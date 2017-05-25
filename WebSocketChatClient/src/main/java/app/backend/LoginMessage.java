package app.backend;

/**
 * Created by faiter on 5/4/17.
 */
public class LoginMessage {

    private String userName;

    public LoginMessage(String userName) {

        this.userName = userName;
    }

    public String getUserName() {

        return userName;
    }

    @Override
    public String toString() {

        return "LoginMessage{" + "userName='" + userName + '\'' + '}';
    }
}
