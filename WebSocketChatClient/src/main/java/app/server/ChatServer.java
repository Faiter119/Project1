package app.server;

import app.backend.ChatMessage;
import app.backend.User;
import app.backend.managers.UserManager;
import com.google.gson.Gson;
import server.api.Server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by faiter on 5/2/17.
 */
public class ChatServer implements Runnable {

    private Gson gson = new Gson();
    private Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public void run() {

        UserManager userManager = new UserManager();
        userManager.addUser(User.generateUser("admin","admin")); // for testing

        Server server = null;
        try {
            server = Server.getWebSocketServer(8080);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }

        server.onConnection(connection -> {

            logger.info("Someone connected!");
            connection.send("You connected!");

            connection.onMessage(message -> {

                logger.info("Message received: "+message);

                ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);



            });

            connection.onClose(() -> {

                logger.info("Someone disconnected!");

            });

        });

        logger.info("ChatServer listening!");
        server.listen();


    }
}
