package main.java.server;

import main.java.server.factories.ServerFactory;
import main.java.server.interfaces.Connection;
import main.java.server.interfaces.Server;

/**
 * Created by faiter on 4/24/17.
 */
public class ServerTesting {

    public static void main(String[] args) {

        Server webSocketServer = ServerFactory.getWebSocketServer();

        webSocketServer.onConnection((Connection connection) -> {

            System.out.println(connection+": Connected");

            connection.onMessage(message ->{

                // do stuff

                connection.send("Hello there");

            });

            connection.onError(() -> {

                System.out.println("An error occured");

            });

            connection.onClose(() -> {

                System.out.println(connection+": Client disconnected");

            });

        });

        webSocketServer.onError(() -> {

            System.out.println("Error!");

        });

        webSocketServer.listen(8080);
    }
}
