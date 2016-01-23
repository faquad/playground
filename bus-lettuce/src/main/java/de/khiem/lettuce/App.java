package de.khiem.lettuce;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        final LettuceClient c = new LettuceClient("redis://127.0.0.1", "webapp.bus.events");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("will send...");
                c.send("from letucce here");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
