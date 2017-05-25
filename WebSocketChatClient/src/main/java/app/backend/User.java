package app.backend;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by faiter on 5/2/17.
 */
public class User {

    private String userName;
    private String passwordEncrypted;

    private User(String userName, String passwordEncrypted) {

        this.userName = userName;
        this.passwordEncrypted = passwordEncrypted;
    }

    public static User generateUser(String username, String password){

        String gensalt = BCrypt.gensalt();

        String hashpw = BCrypt.hashpw(password, gensalt);

        return new User(username, hashpw);
    }

    public String getUserName() {

        return userName;
    }

    public boolean authorizePassword(String password){

        return BCrypt.checkpw(password, passwordEncrypted);

    }

    @Override
    public String toString() {

        return "User{" + "userName='" + userName + '\'' + '}';
    }
}
