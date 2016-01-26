/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.eventlog.notifications;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import java.util.Optional;


/**
 *
 * @author khiemnguyen
 */
public class PublisherImpl implements Publisher{

    
    private final RedisClient client;
    StatefulRedisConnection<String, String> conn;
    
    private static final String HASH_KEY (String resourceId){
        return  String.format("B:%s", resourceId);
    }
    
    private static final String SESSION_KEY(String sid){
        return  String.format("S:%s", sid);
    }
    
    private static final Auth toAuth(String subject, String perm){
        return new Auth();
    }
    private static final String toAuthVal(Auth auth){
        return "100";
    }

    public PublisherImpl(String redisHost, int redisPort) {
        client = RedisClient.create("redis://" + redisHost + ":" + redisPort);
        conn = client.connect();
        System.out.println("redisClientCreated");        
    }
    
    @Override
    public Optional<Auth> getAuthority(String subject, String resource) {
        String kv = conn.sync().hget(HASH_KEY(resource), subject);
        if (kv==null || kv.isEmpty())
            return Optional.empty();
        return Optional.of(toAuth(subject, kv));
    }

   
    @Override
    public void setAuthority(Auth auth) {
        Long l = conn.sync().hlen(auth.getResourceId());
        System.out.println("exists:" + (l!=null && l>0l));
        conn.sync().hset(HASH_KEY(auth.getResourceId()), auth.getSubjectId(), toAuthVal(auth));
    }

    @Override
    public void removeResouce(String resourceId) {
        conn.sync().del(HASH_KEY(resourceId));
    }

    @Override
    public Optional<Session> getSession(String sessionId) {
        String v = conn.sync().get(SESSION_KEY(sessionId));
        return v==null ? Optional.empty(): Optional.of(new Session(sessionId, v));
    }

    @Override
    public void removeSession(String sessionId) {
        conn.sync().del(SESSION_KEY(sessionId));
    }

    @Override
    public void setSession(Session session) {
        conn.sync().psetex(SESSION_KEY(session.getSessionId()), TIMEOUT,session.getSubjectId());
    }

    @Override
    public void close() {
        conn.close();
        client.shutdown();
    }

    
    
    private static final long TIMEOUT = 1000;
    
}
