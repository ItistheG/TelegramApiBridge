package org.javagram.response.object;

import org.telegram.api.TLAbsChat;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class Chat {

    private int id;

    public Chat(TLAbsChat tlAbsChat) {
        id = tlAbsChat.getId();
    }

    public int getId() {
        return id;
    }
}
