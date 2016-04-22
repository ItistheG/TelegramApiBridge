package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;

/**
 * Created by HerrSergio on 22.04.2016.
 */
public interface InputPeer {
    TLAbsInputPeer createTLInputPeer();
}
