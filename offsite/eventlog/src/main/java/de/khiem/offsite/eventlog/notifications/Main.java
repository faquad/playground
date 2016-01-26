/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventlog.notifications;

import de.khiem.offsite.eventlog.notifications.Publisher.Auth;
import java.util.Optional;

/**
 *
 * @author khiemnguyen
 */
public class Main {
    public static void main(String[] args) throws Exception{
            new Main().test1();
    }
   
    
    void test1() throws Exception{
        Publisher p = new PublisherImpl("127.0.0.1", 6379);
        Auth a1= new Auth();
        a1.setPerm(new Publisher.Permission());
        a1.setSubjectId("user1");
        a1.setResourceId("box1");
        p.setAuthority(a1);
        System.out.println("got a1:" + p.getAuthority(a1.getSubjectId(), a1.getResourceId()));
        
        Auth a2= new Auth();
        a2.setPerm(new Publisher.Permission());
        a2.setSubjectId("user2");
        a2.setResourceId("box1");
        System.out.println("got a2:" + p.getAuthority(a2.getSubjectId(), a2.getResourceId()));
        
        
        p.setAuthority(a2);
        System.out.println("got a2:" + p.getAuthority(a2.getSubjectId(), a2.getResourceId()));
        
        p.removeResouce(a1.getResourceId());
        System.out.println("got a2:" + p.getAuthority(a2.getSubjectId(), a2.getResourceId()));
        
       
        
        String sid1 = "sessoion1";
        String uid = "user1";
        p.setSession(new Publisher.Session(sid1, uid));
        System.out.println("got session:" + p.getSession(sid1));
        
        Thread.sleep(2000);
        System.out.println("got expired session:" + p.getSession(sid1));
        
        
         p.close();
    }
}
