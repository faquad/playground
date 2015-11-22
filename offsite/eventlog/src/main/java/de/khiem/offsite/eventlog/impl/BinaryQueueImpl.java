/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventlog.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.base.Preconditions;
import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;
import de.khiem.offsite.eventLog.Event;
import de.khiem.offsite.eventLog.EventQueue;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

/**
 *
 * @author khiemnguyen
 */
public class BinaryQueueImpl implements EventQueue {

    private final String directory;
    private final String name;
    private final IBigQueue queue;
    private final long waitOnEmtpyInMs;

    private long backOff;

    static Logger logger = LoggerFactory.getLogger(BinaryQueueImpl.class.getName());

    private final KryoPool pool;
    long round=0;
    
    public BinaryQueueImpl(long waitOnEmptyImMs, String path, String name, final Class<? extends Event>[] clazzes) throws IOException {
        this.directory = path;
        this.name = name;
        this.waitOnEmtpyInMs = waitOnEmptyImMs;
        queue = new BigQueueImpl(directory, name);
        backOff = waitOnEmptyImMs;
        final AtomicInteger ids = new AtomicInteger(11);
        KryoFactory factory = () -> {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(true);
            for (Class<? extends Event> c : clazzes) {
                kryo.register(c, ids.incrementAndGet());
            }
            return kryo;
        };
        pool = new KryoPool.Builder(factory).softReferences().build();
    }

    @Override
    public void put(Event event) throws IOException {
        Preconditions.checkNotNull(event);
        queue.enqueue(toBytes(event));
    }

    @Override
    public void put(List<Event> events) throws IOException {
        Preconditions.checkNotNull(events);
        for (Event e : events) {
            if (e != null) {
                queue.enqueue(toBytes(e));
            }
        }
    }

    @Override
    public void doWork(final Handler h) {
        System.out.println("doing work round " + round);
        boolean hasErr = false;
        while (true) {
            try {
                byte[] e = queue.dequeue();
                if (e == null) {
                    break;
                }
                h.process(fromBytes(e));
            } catch (IOException e) {
                logger.warn("failed to deQueue", e);
                hasErr = true;
                break;
            }
        }
        if (hasErr) {
            backOff += waitOnEmtpyInMs;
        } else {
            backOff = waitOnEmtpyInMs;
        }
        System.out.println("done-doing work " + round);
        round ++;
        reSchedule(h);
    }

    private void reSchedule(final Handler h) {
        System.out.println("reScheduling:" + backOff);
        logger.info("rescheduling in {} ms", backOff);
        Observable.timer(backOff, TimeUnit.MILLISECONDS)
//                .flatMap(new Func1<Long, Observable<Long>>(){
//
//            @Override
//            public Observable<Long> call(Long t) {
//                System.out.println("call from flatmap -> doWork -> just " + t);
//                doWork(h);
//                return Observable.just(t);
//            }
//        })
                .subscribe(
                s -> {
                    System.out.println("onENextCalled,round -> " + round);
                    doWork(h);
                }
        );
//                
//                subscribeOn(Schedulers.computation())
//                .subscribe((n)->{System.out.println("timerOnNext");}, 
//                e -> {
//                    logger.warn("failed to rescheduled.inc backoff",e);
//                    backOff += waitOnEmtpyInMs;
//                    reSchedule(h);
//                });
        
//        Observable.timer(backOff, TimeUnit.MILLISECONDS)
//                .map(l -> {retunr })
//                .subscribe(s -> {
//                    doWork(h);
//                }, e -> {
//                    logger.warn("failed to rescheduled.inc backoff",e);
//                    backOff += waitOnEmtpyInMs;
//                    reSchedule(h);
//                });
    }

    @Override
    public byte[] toBytes(Event event) {
        Kryo kryo = pool.borrow();
        try (Output out = new Output(new java.io.ByteArrayOutputStream())) {
            kryo.writeClassAndObject(out, event);
            return out.toBytes();
        } finally {
            pool.release(kryo);
        }
    }

    @Override
    public Event fromBytes(byte[] bytes) {
        Kryo kryo = pool.borrow();
        try (Input in = new Input(bytes)) {
            return (Event) kryo.readClassAndObject(in);
        } finally {
            pool.release(kryo);
        }
    }
}
