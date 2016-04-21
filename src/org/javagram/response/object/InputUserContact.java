package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;
import org.telegram.api.TLInputPeerContact;
import org.telegram.api.TLInputUserContact;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public class InputUserContact extends InputUser {

    private int id;

    public InputUserContact(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    @Override
    public TLInputUserContact createTLInputUser() {
        return new TLInputUserContact(getId());
    }

    @Override
    public TLInputPeerContact createTLInputPeer() {
        return new TLInputPeerContact(getId());
    }
}
