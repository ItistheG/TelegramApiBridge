package org.javagram.response.object.inputs;

import org.javagram.response.object.InputPeer;
import org.javagram.response.object.InputUser;
import org.telegram.api.TLInputPeerContact;
import org.telegram.api.TLInputUserContact;

/**
 * Created by HerrSergio on 22.04.2016.
 */
public class InputUserOrPeerContact implements InputUser, InputPeer {

    private int id;

    public InputUserOrPeerContact(int id) {
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
