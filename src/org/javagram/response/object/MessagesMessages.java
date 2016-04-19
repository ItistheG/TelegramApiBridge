package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.messages.TLMessagesSlice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HerrSergio on 19.04.2016.
 */
public class MessagesMessages extends ArrayList<MessagesMessage> {
    private Integer totalCount;

    public MessagesMessages() {

    }


    public int getTotalCount() {
        return totalCount != null ? totalCount : size();
    }

    public boolean isSlice() {
        return totalCount != null;
    }

    public MessagesMessages(TLAbsMessages tlAbsMessages, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        List<TLAbsUser> tlAbsUserList = tlAbsMessages.getUsers();
        List<TLAbsMessage> tlAbsMessageList = tlAbsMessages.getMessages();

        for(TLAbsUser tlAbsUser : tlAbsUserList) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(user.getId(), user);
        }

        for(TLAbsMessage tlAbsMessage : tlAbsMessageList) {
            MessagesMessage messagesMessage = new MessagesMessage(tlAbsMessage, users);
            this.add(messagesMessage);
        }

        if(tlAbsMessages instanceof TLMessagesSlice)
            this.totalCount = ((TLMessagesSlice) tlAbsMessages).getCount();
    }

}
