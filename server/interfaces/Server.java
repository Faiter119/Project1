package main.java.server.interfaces;

import main.java.server.functionalInterfaces.Action;
import main.java.server.functionalInterfaces.ItemAction;

import java.util.List;

/**
 * Created by faiter on 4/24/17.
 */
public interface Server {

    //void onHandshake(ItemAction<String> messageAction);
    void onConnection(ItemAction<Connection> connection);
    void onError(Action action);
    void close();

    List<Connection> getClients();
    int getPort();
    void listen(int port);
}
