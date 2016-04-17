package org.javagram.response;

import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.TLUserSelf;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.messages.TLMessagesSlice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class MessagesMessages {
    private Integer count;
    private List<User> users = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public MessagesMessages() {
        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);
    }

    public MessagesMessages(TLAbsMessages tlAbsMessages) {

        for(TLAbsMessage tlAbsMessage : tlAbsMessages.getMessages()) {
            messages.add(new Message(tlAbsMessage));
        }

        for(TLAbsUser tlAbsUser : tlAbsMessages.getUsers()) {
            if(!(tlAbsUser instanceof TLUserSelf))
                users.add(User.createUser(tlAbsUser));
        }

        if(tlAbsMessages instanceof TLMessagesSlice)
            count = ((TLMessagesSlice) tlAbsMessages).getCount();

        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);
    }

    public MessagesMessages(MessagesMessages slice1, MessagesMessages slice2) {
        if(slice1.count == null || slice2.count == null || slice1.count != slice2.count.intValue())
            throw new IllegalArgumentException();

        this.count = slice2.count;

        this.users.addAll(slice1.users);
        this.messages.addAll(slice1.messages);

        this.users.addAll(slice2.users);
        this.messages.addAll(slice2.messages);

        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);

        if(this.count > this.users.size())
            throw new IllegalArgumentException();

        if(this.count == this.users.size())
            this.count = null;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Integer getCount() {
        return count;
    }

    public boolean isSlice() {
        return count != null;
    }
}
