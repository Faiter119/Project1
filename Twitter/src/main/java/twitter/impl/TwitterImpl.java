package twitter.impl;

import com.google.gson.Gson;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import twitter.interfaces.Twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Created by faiter on 8/28/17.
 */

public class TwitterImpl implements Twitter{

    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);;
    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>(1000);;
    private Client client;

    private Consumer<String> onMessage;

    @Override
    public void stop(){client.stop();}


    private ClientBuilder builder;
    @Override
    public void setClientOptions(ClientBuilder builder){this.builder = builder;}

    @Override
    public void listen(){

        builder.eventMessageQueue(eventQueue);
        builder.processor(new StringDelimitedProcessor(msgQueue));

        client = builder.build();

        client.connect();

        while (!client.isDone()) {
            String msg = null;

            try {msg = msgQueue.take();} catch (InterruptedException e) {e.printStackTrace();}

            if (onMessage != null) onMessage.accept(msg);

        }



    }

    public static void main(String[] args) throws InterruptedException {

        Gson JSON = new Gson();

        TwitterImpl twitterImpl = new TwitterImpl();

        List<String> messages = new ArrayList<>();

        twitterImpl.onMessage = message -> {
            System.out.println(message);
            messages.add(message);
        };

        twitterImpl.listen();

        Thread.sleep(10000);
        twitterImpl.stop();

        System.out.println(messages);


        System.out.println("***Finding matches***");
        messages.forEach(message -> {

            HashMap<String, String> hashMap = (HashMap<String, String>) JSON.fromJson(message, HashMap.class);

            String text = hashMap.get("text");

            if(Pattern.matches(".*Trump.*", text)){

                System.out.println(text);
                System.out.println();

            }
        });

    }

    @Override
    public void onMessage(Consumer<String> consumer) {

        this.onMessage = consumer;

    }

}
