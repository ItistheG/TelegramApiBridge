package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;

/**
 * Created by HerrSergio on 21.04.2016.
 */
class InputUserDeleted extends InputUser {

    InputUserDeleted() {

    }

    @Override
    public TLAbsInputUser createTLInputUser() {
        return null;
    }

    @Override
    public TLAbsInputPeer createTLInputPeer() {
        return null;
    }
}
