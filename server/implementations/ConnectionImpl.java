package main.java.server.implementations;

import main.java.server.functionalInterfaces.Action;
import main.java.server.functionalInterfaces.ItemAction;
import main.java.server.interfaces.Connection;

/**
 * Created by faiter on 4/24/17.
 */
public class ConnectionImpl implements Connection {

    boolean connected = true;



    @Override
    public void send(String string) {

    }

    @Override
    public void onMessage(ItemAction<String> messageAction) {

    }

    @Override
    public void onClose(Action action) {

        connected = false;

    }

    @Override
    public void onError(Action action) {

    }
}
