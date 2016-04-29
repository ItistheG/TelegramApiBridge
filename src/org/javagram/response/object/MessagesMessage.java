package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLAbsUser;
import org.telegram.api.messages.TLAbsMessages;

import java.util.*;

/**
 * Created by HerrSergio on 17.04.2016.
 */
public class MessagesMessage extends Message {

    private User from;
    private User toPeerUser;
    private User fwdFrom;

    public MessagesMessage(TLAbsMessage tlAbsMessage, Map<Integer, ? extends User> users, Set<? super User> usedUsers) {
        super(tlAbsMessage);

        /*if(!users.containsKey(super.getFromId()) || !users.containsKey(super.getToPeerUserId())
                 || super.isForwarded() && !users.containsKey(super.getFromId())
                )
            throw new InconsistentDataException();*/

        this.from = users.get(super.getFromId());
        usedUsers.add(this.from);
        this.toPeerUser = users.get(super.getToPeerUserId());
        usedUsers.add(this.toPeerUser);
        if(super.isForwarded()) {
            this.fwdFrom = users.get(super.getFwdFromId());
            usedUsers.add(this.fwdFrom);
        }
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
}
