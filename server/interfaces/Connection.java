package main.java.server.interfaces;

import main.java.server.functionalInterfaces.Action;
import main.java.server.functionalInterfaces.ItemAction;

/**
 * Created by faiter on 4/24/17.
 */
public interface Connection {

    void send(String string);
    void onMessage(ItemAction<String> messageAction);
    void onClose(Action action);
    void onError(Action action);

}
