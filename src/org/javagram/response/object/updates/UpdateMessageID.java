package org.javagram.response.object.updates;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.MessagesMessage;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateMessageID implements Update {
    private int messageId;
    private long randomId;

    public UpdateMessageID(int messageId, long randomId) {
        this.messageId = messageId;
        this.randomId = randomId;
    }

    public long getRandomId() {
        return randomId;
    }

    public int getMessageId() {
        return messageId;
    }
}
