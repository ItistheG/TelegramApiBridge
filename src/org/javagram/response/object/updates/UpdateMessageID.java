package org.javagram.response.object.updates;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.MessagesMessage;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateMessageID implements Update {
    private int id;
    private MessagesMessage message;
    private long randomId;

    public UpdateMessageID(int id, MessagesMessage message, long randomId) {
        if(id != message.getId())
            throw new InconsistentDataException();
        this.message = message;
        this.id = id;
        this.randomId = randomId;
    }

    public int getId() {
        return id;
    }

    public long getRandomId() {
        return randomId;
    }

    public MessagesMessage getMessage() {
        return message;
    }
}
