package app.backend.managers;

import app.backend.Conversation;
import app.backend.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by faiter on 5/3/17.
 */
public class ConversationManager {

    private List<Conversation> conversationList = new ArrayList<>();

    private Map<User, List<Conversation>> userConversationMap = new HashMap<>();

    public void addConversation(Conversation conversation){



        conversationList.add(conversation);
    }
}
