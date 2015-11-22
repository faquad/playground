/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author khiemnguyen
 */
public class Main {
    public static void main(String[] args) {
        try {
            new Main().t1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void t1() throws Exception{
        final int count = 1500;
        final CountDownLatch latch =new CountDownLatch(count);
        final Random r = new Random();
        for (int i=0;i<count;i++){
            final String id= i+"";
            
            Fiber<String> f =  new Fiber<String>(){
                @Override
                protected String run() throws SuspendExecution, InterruptedException {
                    ThreadLocal<String> l=newTL(id);
                    System.out.println("running " + id);
                    latch.countDown();
                    System.out.println("from tl:" + l.get() + "::" + id);
                    l.remove();
                    sleep((int)r.nextDouble()*100, TimeUnit.SECONDS);
                    return id;
                }
            };
            f.start();
            System.out.println("ran : " + f.get());
        }
    }
    
    ThreadLocal<String> newTL(String id){
        ThreadLocal tl =new ThreadLocal();
        tl.set(id);
        return tl;
    }
}
