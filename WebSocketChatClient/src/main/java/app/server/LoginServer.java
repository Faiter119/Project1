package app.server;

import app.backend.LoginMessage;
import com.google.gson.Gson;
import server.api.Server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by faiter on 5/4/17.
 */
public class LoginServer implements Runnable{

    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void run() {

        Server server;
        try {
            server = Server.getWebSocketServer(8081);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }

        server.onConnection(connection -> {

           connection.onMessage(message -> {

               logger.info("Message received: "+message);

               LoginMessage loginMessage = gson.fromJson(message, LoginMessage.class);

               connection.send("OK");




           });

        });
        logger.info("LoginServer listening");
        server.listen();
    }
}
