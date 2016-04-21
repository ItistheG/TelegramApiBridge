package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;
import org.telegram.api.TLInputPeerSelf;
import org.telegram.api.TLInputUserSelf;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public class InputUserSelf extends InputUser {

    public InputUserSelf() {

    }

    @Override
    public TLInputUserSelf createTLInputUser() {
        return new TLInputUserSelf();
    }

    @Override
    public TLInputPeerSelf createTLInputPeer() {
        return new TLInputPeerSelf();
    }
}
