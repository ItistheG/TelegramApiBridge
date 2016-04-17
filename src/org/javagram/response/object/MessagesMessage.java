package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.messages.TLAbsMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HerrSergio on 17.04.2016.
 */
public class MessagesMessage {

    private Message message;
    private User from;
    private User toPeerUser;
    private User fwdFrom;

    protected MessagesMessage(Message message, Map<Integer, User> users) {
        this.message = message;

        if(!users.containsKey(message.getFromId()) || !users.containsKey(message.getToPeerUserId())
                 || message.isForwarded() && !users.containsKey(message.getFromId())
                )
            throw new InconsistentDataException();

        this.from = users.get(message.getFromId());
        this.toPeerUser = users.get(message.getToPeerUserId());
        if(message.isForwarded())
            this.fwdFrom = users.get(message.getFwdFromId());
    }

    public Message getMessage() {
        return message;
    }

    public User getToPeerUser() {
        return toPeerUser;
    }

    public User getFrom() {
        return from;
    }

    public User getFwdFrom() {
        return fwdFrom;
    }

    public boolean isForwarded() {
        return message.isForwarded();
    }

    public static ArrayList<MessagesMessage> create(TLAbsMessages tlAbsMessages, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        List<TLAbsUser> tlAbsUserList = tlAbsMessages.getUsers();
        List<TLAbsMessage> tlAbsMessageList = tlAbsMessages.getMessages();

        ArrayList<MessagesMessage> messagesMessages = new ArrayList<>();

        for(TLAbsUser tlAbsUser : tlAbsUserList) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(user.getId(), user);
        }

        for(TLAbsMessage tlAbsMessage : tlAbsMessageList) {
            Message message = new Message(tlAbsMessage);
            MessagesMessage messagesMessage = new MessagesMessage(message, users);
            messagesMessages.add(messagesMessage);
        }

        return messagesMessages;
    }
}
