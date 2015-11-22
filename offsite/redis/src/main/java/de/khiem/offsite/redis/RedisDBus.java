
package de.khiem.offsite.redis;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.HashSet;
import java.util.Set;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.MessageListener;

/**
 *
 * @author khiemnguyen
 */
public class RedisDBus implements DBus<String> {

    private final Redisson client;
    private final Set<String> topics;

    public RedisDBus(String host, int port) {
        Config conf = new Config();
        conf.useSingleServer()
                .setAddress(host + ":" + port);

        client = Redisson.create(conf);
        topics = new HashSet<>();
    }

    @Override
    public void publish(String topicPath, String evt) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(topicPath), "topicPath required");
        try{
        client.getTopic(topicPath).publish(evt);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(String topicPath, EventHandler<String> listerner) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(topicPath), "topicPath required");
        if (!topics.contains(topicPath)) {
            client.getTopic(topicPath).addListener(new MessageListener<Object>() {
                @Override
                public void onMessage(String channel, Object msg) {
                    System.out.println("got msg on channel:" + channel);
                    try {
                    listerner.onEvent((String) msg);
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
