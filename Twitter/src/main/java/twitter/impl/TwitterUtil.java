package twitter.impl;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;

/**
 * Created by faiter on 8/28/17.
 */
public class TwitterUtil {

    public static class Builder{

        private String hostConstant;

        private Builder(){}

        static Builder builder(){
            return new Builder();
        }

        public Builder hostConstant(String constant){
            this.hostConstant = constant;
            return this;
        }

    }

    public static Client buildClient(BlockingQueue<String> msgQueue, BlockingQueue<Event> eventQueue, StatusesFilterEndpoint statusFilter){

        Authentication auth = new OAuth1("EP4iCSqP3CFle0c6GQGUR83T0", "LtqWzbNz3CgqRwYuAT5LzZQLgGLly5xSqQouqbADCtwNafUhS6", "281201278-qNtgZ0rT9q0t6ZqRGmukBqpMNn6Jv9LoMeV4wILP", "VBvsSGIH0Fe7NfAqhIWl9ODWe2PKmMJ7EkLAtXWeMmuFw");

        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01") // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(auth)
                .endpoint(statusFilter)
                .processor(new StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue); // optional: use this if you want to process client events


        return builder.build();
    }

}
