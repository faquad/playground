/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.redis;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kimyoung
 */
public class Publisher {
    public static void main(String[] args) {
        final RedisDBus b=new RedisDBus("localhost", 6379);
        b.addListener("webapp.bus.events", new EventHandler<String>() {
            @Override
            public void onEvent(String evt) {
                System.out.println("redis got event:" + evt);
            }
        });
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                b.publish("webapp.bus.events", "from redis");
            }
        }, 1,2, TimeUnit.SECONDS);
    }
}
