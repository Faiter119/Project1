package twitter.testing;

import com.google.gson.Gson;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.httpclient.auth.OAuth1;
import twitter.interfaces.Twitter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by faiter on 8/30/17.
 */
public class TwitterTest {

    static Gson JSON = new Gson();

    public static void main(String[] args) throws InterruptedException, IOException {

        Twitter twitter = Twitter.makeTwitter();

        twitter.setClientOptions(
                new ClientBuilder()
                        .hosts(new HttpHosts(Constants.STREAM_HOST))
                        .authentication(new OAuth1("EP4iCSqP3CFle0c6GQGUR83T0", "LtqWzbNz3CgqRwYuAT5LzZQLgGLly5xSqQouqbADCtwNafUhS6", "281201278-qNtgZ0rT9q0t6ZqRGmukBqpMNn6Jv9LoMeV4wILP", "VBvsSGIH0Fe7NfAqhIWl9ODWe2PKmMJ7EkLAtXWeMmuFw"))
                        .endpoint(new StatusesFilterEndpoint().trackTerms(Arrays.asList("USA"))));

        List<String> messages = new ArrayList<>();

        twitter.onMessage(message -> {
            messages.add(message);
            System.out.println(message);
        });

        new Thread(twitter::listen).start();

        //Waiting
        Thread.sleep(10000);

        twitter.stop();

        /*messages.forEach(s -> {

            HashMap<String, String> hashMap = JSON.fromJson(s, HashMap.class);

            System.out.println(hashMap.get("text"));
            System.out.println();

        });*/


        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("/home/faiter/IntelliJ/Projects/Project1/Twitter/src/main/java/twitter/data.csv"), StandardOpenOption.TRUNCATE_EXISTING);


        // Write headers
        HashMap hashMap = JSON.fromJson(messages.get(0), HashMap.class);

        Set<String> headers = hashMap.keySet();

        headers.stream().sorted().forEach(o -> {
            System.out.print(o+" , ");
            try {bufferedWriter.write(o + ", ");}catch (IOException e) {e.printStackTrace();}
        });
        System.out.println();

        //Write data
        messages.forEach(message -> {

            HashMap messageMap = JSON.fromJson(message, HashMap.class);

            headers.stream().sorted().forEach(header -> {

                Object o1 = messageMap.get(header);

                String val = String.valueOf(o1);

                //Pattern pattern = Pattern.compile(".*,.*");

                String newVal = val.replaceAll(","," ");

                /*if (pattern.matcher(val).matches()){
                    System.out.println("VAL");
                    System.out.println(val);
                    System.out.println("REPLACED WITH");
                    System.out.println(newVal);
                }*/
                System.out.print(header+":"+newVal+", ");
                try {bufferedWriter.write(newVal+", ");}catch (IOException e) {e.printStackTrace();}


            });
            System.out.println();

            try {bufferedWriter.write("\n");} catch (IOException e) {e.printStackTrace();}
        });

       bufferedWriter.close();
    }
}
