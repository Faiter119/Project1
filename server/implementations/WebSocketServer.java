package main.java.server.implementations;

import main.java.server.functionalInterfaces.Action;
import main.java.server.functionalInterfaces.ItemAction;
import main.java.server.interfaces.Connection;
import main.java.server.interfaces.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiter on 4/24/17.
 */
public class WebSocketServer implements Server {


    List<Action> actionList = new ArrayList<>();
    List<ItemAction> itemActionList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        final int PORTNR = 1250;
        ServerSocket server = new ServerSocket(PORTNR);


    }

    @Override
    public void onConnection(ItemAction<Connection> connection) {

    }

    @Override
    public void onError(Action action) {

    }

    @Override
    public void close() {

    }

    @Override
    public List<Connection> getClients() {

        return null;
    }

    @Override
    public int getPort() {

        return 0;
    }

    @Override
    public void listen(int port) {

        actionList.forEach(superAction -> {
            superAction.doTheThing();
        });


    }
}
