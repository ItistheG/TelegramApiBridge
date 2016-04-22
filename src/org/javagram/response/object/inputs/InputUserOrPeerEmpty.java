package org.javagram.response.object.inputs;

import org.javagram.response.object.InputPeer;
import org.javagram.response.object.InputUser;
import org.telegram.api.TLInputPeerEmpty;
import org.telegram.api.TLInputUserEmpty;

/**
 * Created by HerrSergio on 22.04.2016.
 */
public class InputUserOrPeerEmpty implements InputUser, InputPeer {

    public InputUserOrPeerEmpty() {

    }

    @Override
    public TLInputUserEmpty createTLInputUser() {
        return new TLInputUserEmpty();
    }

    @Override
    public TLInputPeerEmpty createTLInputPeer() {
        return new TLInputPeerEmpty();
    }
}
