package de.khiem.offsite.eventLog;

import de.khiem.offsite.eventLog.impl.BinaryQueueImpl;
import de.khiem.offsite.eventLog.impl.LocalEventLogService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import rx.Observable;

public class Main {
    
    public static void main(String[] args) {
        Main single = new Main();
        //single.testLogInsert(true);
        try {
            single.testQueue();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void testQueue() throws Exception{
        CountDownLatch latch = new CountDownLatch(1);
        final EventQueue queue =new BinaryQueueImpl(500, "/tmp/", "bigQueu", new Class[]{Event.class});
        queue.doWork((Event e) -> {
            System.out.println("handled eventId:" + e.itemId);
        });
        
        System.out.println("started queue");
        Observable.timer(2000, 2000, TimeUnit.MILLISECONDS).subscribe(s -> {
            System.out.println("start generating");
            long l=0l;
            for (int i=0; i< 5; ++i){
                try {
                    Event e=newEvent(l);
                    queue.put(e);
                    System.out.println("\tput event " + l);
                    l++;
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        latch.await();
    }
    
    
    void testLogInsert(boolean bulk){
        EventLogService s =new LocalEventLogService();
        String[] types = new String[]{"B1", "B2", "B3"};
        long itemId = 1l;
        for (String t: types){
            if (!bulk){
                for (int i=0; i<100; i++){
                    Event e=newEvent(itemId);
                    s.insert(t, e).subscribe(l -> {System.out.println("inserted:" + l);});
                    itemId++;
                }
            }else {
                List<Event> events=new ArrayList<>();
                for (int i=0; i<100; i++){
                    events.add(newEvent(itemId));
                    itemId++;
                }
                s.insert(t, events).subscribe(l -> {
                    System.out.println("onnext:" +l);
                });
            }
        }
        
        s.list(types[0], 9, 10).subscribe(n -> {System.out.println("queriedOnNext:" + n.itemId);});
        
    }
    
    Event newEvent(long itemId ){
        return new Event(itemId);
    }
}
