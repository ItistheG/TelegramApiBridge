package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLInputPeerEmpty;
import org.telegram.api.TLUserEmpty;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserEmpty extends User {
    public UserEmpty(TLUserEmpty userEmpty) {
        super(userEmpty);
    }

    @Override
    public InputUserEmpty getInputUser() {
        return new InputUserEmpty();
    }
}
