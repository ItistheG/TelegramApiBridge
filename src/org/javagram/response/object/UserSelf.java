package org.javagram.response.object;

import org.telegram.api.TLInputPeerSelf;
import org.telegram.api.TLInputUserSelf;
import org.telegram.api.TLUserSelf;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserSelf extends User {

    public UserSelf(TLUserSelf tlUserSelf) {
        super(tlUserSelf);
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
