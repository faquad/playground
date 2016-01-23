/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.lettuce;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;
import com.lambdaworks.redis.pubsub.RedisPubSubListener;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;

/**
 *
 * @author kimyoung
 */
public class LettuceClient {
    String server;
    String addr;
    StatefulRedisPubSubConnection<String, String>  listener;
    StatefulRedisPubSubConnection<String, String>  publisher;

    public LettuceClient(String server, String addr) {
        this.server =server;
        this.addr  = addr;
        RedisClient client = RedisClient.create(server);
        listener = client.connectPubSub();
        publisher = client.connectPubSub();
        System.out.println("connected to pubsub");
        listener.addListener(new RedisPubSubAdapter<String, String>(){
            @Override
            public void message(String channel, String message) {
                System.out.println("got msg:" + message + " on channel " + channel);
            }

            @Override
            public void subscribed(String channel, long count) {
                System.out.println("subscribed");
            }
            
            
        });
        System.out.println("added listener.");
        listener.async().subscribe(addr);
        //System.out.println("subscribed to " + addr);
    }
    
    public void send(String msg){
        try {
            System.out.println("sending " + msg + " -> " + addr);
        listener.sync().publish(addr, msg);
       //conn.async().publish(addr, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
