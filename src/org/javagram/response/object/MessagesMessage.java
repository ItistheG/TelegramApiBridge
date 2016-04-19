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
public class MessagesMessage extends Message {

    private User from;
    private User toPeerUser;
    private User fwdFrom;

    MessagesMessage(TLAbsMessage tlAbsMessage, Map<Integer, User> users) {
        super(tlAbsMessage);

        if(!users.containsKey(super.getFromId()) || !users.containsKey(super.getToPeerUserId())
                 || super.isForwarded() && !users.containsKey(super.getFromId())
                )
            throw new InconsistentDataException();

        this.from = users.get(super.getFromId());
        this.toPeerUser = users.get(super.getToPeerUserId());
        if(super.isForwarded())
            this.fwdFrom = users.get(super.getFwdFromId());
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
