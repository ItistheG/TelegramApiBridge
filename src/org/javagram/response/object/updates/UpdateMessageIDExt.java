package org.javagram.response.object.updates;

import org.javagram.response.object.MessagesMessage;

/**
 * Created by HerrSergio on 01.07.2016.
 */
public class UpdateMessageIDExt extends UpdateMessageID {

    private MessagesMessage message;

    public UpdateMessageIDExt(MessagesMessage message, long randomId) {
        super(message.getId(), randomId);
        this.message = message;
    }

    public MessagesMessage getMessage() {
        return message;
    }
}
