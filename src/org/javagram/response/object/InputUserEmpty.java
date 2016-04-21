package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;
import org.telegram.api.TLInputPeerEmpty;
import org.telegram.api.TLInputUserEmpty;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public class InputUserEmpty extends InputUser {

    public InputUserEmpty() {

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
