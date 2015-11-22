
package de.khiem.offsite.rx;

import java.util.LinkedList;
import rx.subjects.PublishSubject;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
/**
 *
 * @author khiemnguyen
 */
public class Test1 {
    
    public static void main(String[] args) {
        //t2();
        t3();
    }
    
    
    static void t3(){
         final int count = 20; 
        final  CountDownLatch  l = new CountDownLatch(count-1);
        GR gr  = new GR(new Subscriber<GroupedObservable<String, Event>>(){
            
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(GroupedObservable<String, Event> t) {
                System.out.println("Subscriber<GroupedObservable>-on_next, key:" + t.getKey());
                t.reduce(0, new Func2<Integer, Event, Integer>(){
                    @Override
                    public Integer call(Integer t1, Event t2) {
                        
                        int result =  t1 + t2.ind;
                        System.out.println("reduce_call for " + t2 + ",result->" + result);
                        l.countDown();
                        return result;
                        
                        
                    }
                }).subscribe((Integer i) -> { System.out.println("onnext-subscr of Subscriber<GroupedObservable->" + i);});
            }
        });
        
         //for (;;){
                System.out.println("will generate " + count);
                String[] k=new String[]{"A", "B","C"};

                for (int i = 0; i < count; i++) {
                     try {
                          String key = k[i%3];
                          gr.next(new Event(key, i));
                          Thread.sleep((long) (Math.random() * 2000));
                    // sleep for a random amount of time
                    
                } catch (Exception e) {
                    // do nothing
                }
                   
                }
               
                
                try {
            Thread.sleep(5000);
            System.out.println("will generate next round " + count);
              for (int i = 0; i < count; i++) {
                     try {
                          String key = k[i%3];
                          gr.next(new Event(key, i));
                          Thread.sleep((long) (Math.random() * 2000));
                    // sleep for a random amount of time
                    
                } catch (Exception e) {
                    // do nothing
                }
                   
                }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                 
                
        try {
           l.await();
           System.out.println("done");
            //  }
        } catch (InterruptedException ex) {
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void t2() {
        final int count = 20; 
        final  CountDownLatch  l = new CountDownLatch(count-1);
        B b = new B(new Subscriber<List<Integer>>() {
            
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(List<Integer> t) {
                System.out.println("onNext.size:" + t.size());
                for (Integer i: t){
                    l.countDown();
                    System.out.println("\t got:" + i + ",latch:" + l.getCount());
                }
               
            }
        });
        
        //for (;;){
                System.out.println("will generate " + count);
                
                for (int i = 0; i < count; i++) {
                     try {
                          b.accept(i);
                          Thread.sleep((long) (Math.random() * 1000));
                        // sleep for a random amount of time
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    // do nothing
                }
                }
        
                 
                
               
        try {
           l.await();
           System.out.println("done");
            //  }
        } catch (InterruptedException ex) {
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }
            
      
        
    }
    
    static class GR {
        private final Subscriber<? super GroupedObservable<String, Event>> s;
        PublishSubject<Event> pub;
        
        public GR(Subscriber<? super GroupedObservable<String, Event>>  s) {
            this.s = s;
            pub =PublishSubject.create();
            pub.share();

            
            Observable<GroupedObservable<String, Event>> groupBy = pub//.debounce(500, TimeUnit.MILLISECONDS)
                    .groupBy(new Func1<Event, String>(){
                        @Override
                        public String call(Event t) {
                            System.out.println("call-KeySelector for :" + t);
                            return t.name;
                        }
                    }); //.debounce(500, TimeUnit.MILLISECONDS)
            
            
                       
            
            Observable<List<GroupedObservable<String, Event>>> buffered = groupBy.buffer(4000, TimeUnit.MILLISECONDS);
            
                       
            buffered.subscribe(new Subscriber<List<GroupedObservable<String, Event>>>() {

                @Override
                public void onCompleted() {
                    System.out.println("onComplete of buffered");
                }

                @Override
                public void onError(Throwable e) {
                     System.out.println("onErr");
                }

                @Override
                public void onNext(List<GroupedObservable<String, Event>> t) {
                    System.out.println("\tonNext of List<GroupedObservable<String, Event>>");
                    for (GroupedObservable<String, Event> o : t){
                         System.out.println("\t\toKey:" + o.getKey());
                         
                         o.take(4000, TimeUnit.MILLISECONDS).reduce(0, new Func2<Integer,Event,Integer>(){
                             @Override
                             public Integer call(Integer t1, Event t2) {
                                 int res = t1+t2.ind;
                                 System.out.println("\treduce with " + t2 + ',' + t1 + ",return " + res + ",key:" + o.getKey());
                                 return res;                                 
                             }
                             
                        }).subscribe(i-> {System.out.println("Subscr.onNext->" + o.getKey() + ":" + i);});
                    }
                }
            });
        }
        
        
        void next(Event e){
            pub.onNext(e);
        }
        
    }
    
    
    static class B {
        
        Observable<List<Integer>> buffered;
        PublishSubject<Integer> subj ;
        private final Subscriber<? super List<Integer>> s;
        B (Subscriber<? super List<Integer>> s){
            
           subj = PublishSubject.create();
           subj.share();
                              
           Observable<Integer> debounced = subj.debounce(500, TimeUnit.MILLISECONDS);
          
           buffered = subj.buffer(debounced);
         
           buffered.subscribeOn(Schedulers.newThread()).subscribe(s);
         
            // see all bursts in a single sequence
        //        intermittentBursts().toBlocking().forEach(System.out::println);

        // debounce to the last value in each burst
        //        intermittentBursts().debounce(10, TimeUnit.MILLISECONDS).toBlocking().forEach(System.out::println);

        /* The following will emit a buffered list as it is debounced */
        // first we multicast the stream ... using refCount so it handles the subscribe/unsubscribe
       // burstStream = intermittentBursts().publish().refCount();
        // then we get the debounced version
        //Observable<Integer> debounced = burstStream.debounce(10, TimeUnit.MILLISECONDS);
        // then the buffered one that uses the debounced stream to demark window start/stop
        //buffered = burstStream.buffer(debounced);
        
        // then we subscribe to the buffered stream so it does what we want
        //buffered.take(20).toBlocking().forEach(System.out::println);
           this.s=s;
        }
        
        
        Observable<Integer> asObservable(){
            return subj;
        }
        
        void finish(){
            s.unsubscribe();
        }
        
        void accept(int el){
            System.out.println("onNExt(accept) " + el);
            subj.onNext(el);
        }
    }

    
    private static void t1(){
        Q q=new Q();
        q.start();
        
    }
    
    static class Q {
        List<Event> events;
        public Q() {
            events= new LinkedList<>();
                    
        }
        
        void start(){
            PublishSubject.<BoxEvent>create(s -> {
               
            });
            
            final Observable<Event> p = Observable.<Event>create((Subscriber<? super Event> s) -> {
                System.out.println("onSubsc call..");
                
            });
            
           p.buffer(200l, TimeUnit.MILLISECONDS, 5);
        }
        
        void add(Event e){
            events.add(e);
        }
    }
    
    
    static class Event {
        String name;
        int ind;
        public Event(String name, int ind) {
            this.name = name;
            this.ind = ind;
        }

        @Override
        public String toString() {
            return "event ["+name+ ":" + ind+ "]";
        }
    }
    
    class BoxEvent {
    }
}
