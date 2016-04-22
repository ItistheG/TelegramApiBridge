package org.javagram.response.object;

import org.telegram.api.*;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserForeign extends User {

    private long accessHash;

    public UserForeign(TLUserForeign userForeign) {
        super(userForeign);
        accessHash = userForeign.getAccessHash();
    }

    UserForeign(TLUserRequest userRequest) {
        super(userRequest);
        accessHash = userRequest.getAccessHash();
    }

    public long getAccessHash() {
        return accessHash;
    }

    @Override
    public TLInputUserForeign createTLInputUser() {
        return new TLInputUserForeign(getId(), getAccessHash());
    }

    @Override
    public TLInputPeerForeign createTLInputPeer() {
        return new TLInputPeerForeign(getId(), getAccessHash());
    }
}
