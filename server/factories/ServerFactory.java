package main.java.server.factories;

import main.java.server.implementations.WebSocketServer;
import main.java.server.interfaces.Server;

/**
 * Created by faiter on 4/24/17.
 */
public class ServerFactory {

    public static Server getWebSocketServer(){
        return new WebSocketServer();
    }

}
