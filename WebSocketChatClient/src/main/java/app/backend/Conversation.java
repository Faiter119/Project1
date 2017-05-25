package app.backend;

import org.apache.commons.collections4.list.UnmodifiableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiter on 5/2/17.
 */
public class Conversation {

    private List<User> userList = new ArrayList<>();
    private List<ChatMessage> messageList = new ArrayList<>();

    public Conversation(List<User> userList) {
        this.userList = userList;
    }


    public List<User> getUserList() {
        return UnmodifiableList.unmodifiableList(userList);
    }

    public List<ChatMessage> getMessageList() {
        return UnmodifiableList.unmodifiableList(messageList);
    }

    public void addChatMessage(ChatMessage message){messageList.add(message);}
    public void addUser(User user){userList.add(user);}
}
