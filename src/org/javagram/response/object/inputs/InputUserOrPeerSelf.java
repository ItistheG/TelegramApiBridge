package org.javagram.response.object.inputs;

import org.javagram.response.object.InputPeer;
import org.javagram.response.object.InputUser;
import org.telegram.api.TLInputPeerSelf;
import org.telegram.api.TLInputUserSelf;

/**
 * Created by HerrSergio on 22.04.2016.
 */
public class InputUserOrPeerSelf implements InputUserOrPeer {

    public InputUserOrPeerSelf() {

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
