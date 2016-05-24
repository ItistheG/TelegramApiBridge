package org.javagram.response.object.updates;

import org.javagram.response.object.MessagesMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateReadMessage implements Update {
    private int pts;
    private ArrayList<Integer> messages;

    public UpdateReadMessage(Collection<Integer> messages, int pts) {
        this.pts = pts;
        this.messages = new ArrayList<>(messages);
    }

    public int getPts() {
        return pts;
    }

    public ArrayList<Integer> getMessages() {
        return messages;
    }
}
