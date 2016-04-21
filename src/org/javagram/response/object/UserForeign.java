package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLInputPeerForeign;
import org.telegram.api.TLUserForeign;
import org.telegram.api.TLUserRequest;

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
    public InputUserForeign getInputUser() {
        return new InputUserForeign(getId(), getAccessHash());
    }
}
