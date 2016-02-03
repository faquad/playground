/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.redis;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 *
 * @author khiemnguyen
 */
public class TestCache {
    
    public static void main(String[] args) {
        new TestCache().t1();
    }
    
    
    void t1(){
        Cache<String,User> cache = CacheBuilder.newBuilder().build();
        User u = new User("u1");
        
        
        
        cache.put(u.getId(), u);
        User v = cache.getIfPresent("u1");
        Consumer cons=new Consumer(v);
        
        System.out.println("user with id:" + cons.u.getId());
        cache.invalidate("u1");
        System.out.println("after invalidated:" + cache.getIfPresent("u1"));
        System.out.println("user with id:" + cons.u.getId());
        
    }
    
    
    
    class User{
        String id;
        User(String id){
            this.id=id;
        }

        public String getId() {
            return id;
        }
    }
    class Consumer {
        User u;
        public Consumer(User u) {
            this.u=u;
        }
        
    }
}
