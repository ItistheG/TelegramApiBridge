package org.javagram.response;

import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.messages.TLMessagesSlice;

import java.util.*;

/**
 * Created by HerrSergio on 19.04.2016.
 */
public class MessagesMessages {
    private int totalCount;
    private boolean slice;
    private ArrayList<MessagesMessage> messages = new ArrayList<>();
    private HashSet<User> users = new HashSet<>();

    public MessagesMessages() {

    }


   /* public int getTotalCount() {
        return totalCount;
    }
*/
    public boolean isSlice() {
        return slice;
    }

    public MessagesMessages(TLAbsMessages tlAbsMessages, Map<Integer, User> users/*, Set<? super User> detectedUsers*/) {

       // if(users == null)
         //   users = new HashMap<>();

        Helper.acceptTLAbsMessages(messages, tlAbsMessages.getUsers(), tlAbsMessages.getMessages(), users, this.users);

        if(tlAbsMessages instanceof TLMessagesSlice) {
            this.totalCount = ((TLMessagesSlice) tlAbsMessages).getCount();
            this.slice = true;
        } else {
            this.totalCount = tlAbsMessages.getMessages().size();
            this.slice = false;
        }

       // this.users = Helper.getUsers(messages);
    }

    public ArrayList<MessagesMessage> getMessages() {
        return messages;
    }

    public HashSet<User> getUsers() {
        return users;
    }
}
