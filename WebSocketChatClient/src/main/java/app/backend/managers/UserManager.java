package app.backend.managers;

import app.backend.User;
import org.apache.commons.collections4.list.UnmodifiableList;

import java.util.*;

/**
 * Created by faiter on 5/2/17.
 */
public class UserManager {

    private Map<String, User> userMap = new HashMap<>();

    public List<User> getUserList() {

        return UnmodifiableList.unmodifiableList((List<User>)userMap.values());
    }

    public void addUser(User user){
        Objects.requireNonNull(user);

        userMap.putIfAbsent(user.getUserName(), user);
    }


    public Optional<User> getUserIfExists(String userName){

        return Optional.ofNullable(userMap.get(userName));
    }
    public boolean containsUserName(String userName){

        return userMap.containsKey(userName);
    }
}
