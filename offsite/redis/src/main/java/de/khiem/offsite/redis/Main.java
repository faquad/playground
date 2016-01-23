/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khiemnguyen
 */
public class Main {
    
    public static void main(String[] args) {
         final int evt=1;
         final int clients =1;
         final String topic = "webapp.bus.events";
         
         final CountDownLatch latch= new CountDownLatch(clients*evt);
         List<RedisDBus> buses = new ArrayList<>();
         for (int i=0;i <clients;++i){
             final String clientId = "client_" + i; 
             RedisDBus b=new RedisDBus("localhost", 6379);
             
             b.addListener(topic, new EventHandler<String>() {
                 @Override
                 public void onEvent(String evt) {
                     System.out.println(clientId + " received msg on " + topic + ":" + evt);
                     latch.countDown();
                 }
             });
             buses.add(b);
         }
         
         for (RedisDBus b: buses){
             for (int i =0; i< evt ; ++i)
             b.publish(topic, "event_" +i);
         }
         
        try {
            latch.await();
            System.out.println("finished");
            
             RedisDBus bnew=new RedisDBus("localhost", 6379);
             
             bnew.addListener(topic, new EventHandler<String>() {
                 @Override
                 public void onEvent(String evt) {
                     System.out.println("newClient" + " received msg on " + topic + ":" + evt);
                 }
             });
            bnew.publish(topic, "blabalalbla");
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
}
