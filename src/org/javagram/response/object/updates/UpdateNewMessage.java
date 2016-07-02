package org.javagram.response.object.updates;

import org.javagram.response.object.Message;
import org.javagram.response.object.MessagesMessage;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateNewMessage implements Update {
    private Message message;
    private int pts;

    public UpdateNewMessage(Message message, int pts) {
        this.pts = pts;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public int getPts() {
        return pts;
    }
}
