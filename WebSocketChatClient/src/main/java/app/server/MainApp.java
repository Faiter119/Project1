package app.server;

/**
 * Created by faiter on 5/4/17.
 */
public class MainApp {

    public static void main(String[] args) {

        ChatServer chatServer = new ChatServer();
        LoginServer loginServer = new LoginServer();

        new Thread(chatServer).start();
        new Thread(loginServer).start();
    }
}
