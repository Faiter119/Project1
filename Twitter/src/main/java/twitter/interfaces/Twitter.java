package twitter.interfaces;

import com.twitter.hbc.ClientBuilder;
import twitter.impl.TwitterImpl;

import java.util.function.Consumer;

/**
 * Created by faiter on 8/30/17.
 */
public interface Twitter {

    static Twitter makeTwitter(){
        return new TwitterImpl();
    }

    // Blocking method
    void listen();
    void onMessage(Consumer<String> consumer);
    void setClientOptions(ClientBuilder builder);
    void stop();



}
