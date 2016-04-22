package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;
import org.telegram.api.TLUserDeleted;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserDeleted extends User {

    public UserDeleted(TLUserDeleted userDeleted) {
        super(userDeleted);
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
