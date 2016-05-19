package org.javagram.response.object.updates;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.MessagesMessage;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateMessageID implements Update {
    private MessagesMessage message;
    private long randomId;

    public UpdateMessageID(MessagesMessage message, long randomId) {
        this.message = message;
        this.randomId = randomId;
    }

    public long getRandomId() {
        return randomId;
    }

    public MessagesMessage getMessage() {
        return message;
    }
}
