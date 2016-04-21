package org.javagram.response.object;

import org.telegram.api.*;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public abstract class InputUser {

    InputUser() {

    }

    public abstract TLAbsInputUser createTLInputUser();
    public abstract TLAbsInputPeer createTLInputPeer();
}
