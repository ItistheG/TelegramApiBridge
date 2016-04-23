package org.javagram.response;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.messages.TLMessagesSlice;
import org.telegram.api.updates.TLAbsDifference;
import org.telegram.api.updates.TLDifference;
import org.telegram.api.updates.TLDifferenceEmpty;
import org.telegram.api.updates.TLDifferenceSlice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HerrSergio on 19.04.2016.
 */
public class MessagesMessages extends ArrayList<MessagesMessage> {
    private int totalCount;
    private boolean slice;

    public MessagesMessages() {

    }


    public int getTotalCount() {
        return totalCount;
    }

    public boolean isSlice() {
        return slice;
    }

    public MessagesMessages(TLAbsMessages tlAbsMessages, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        acceptTLAbsMessages(tlAbsMessages.getUsers(), tlAbsMessages.getMessages(), users);

        if(tlAbsMessages instanceof TLMessagesSlice) {
            this.totalCount = ((TLMessagesSlice) tlAbsMessages).getCount();
            this.slice = true;
        } else {
            this.totalCount = tlAbsMessages.getMessages().size();
            this.slice = false;
        }
    }



    private void acceptTLAbsMessages(List<TLAbsUser> tlAbsUserList, List<TLAbsMessage> tlAbsMessageList, Map<Integer, User> users) {

        for(TLAbsUser tlAbsUser : tlAbsUserList) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(user.getId(), user);
        }

        for(TLAbsMessage tlAbsMessage : tlAbsMessageList) {
            MessagesMessage messagesMessage = new MessagesMessage(tlAbsMessage, users);
            this.add(messagesMessage);
        }
    }

}
