package org.javagram.response.object.inputs;

import org.javagram.response.object.InputPeer;
import org.javagram.response.object.InputUser;
import org.telegram.api.TLInputPeerForeign;
import org.telegram.api.TLInputUserForeign;

/**
 * Created by HerrSergio on 22.04.2016.
 */
public class InputUserOrPeerForeign implements InputUserOrPeer {

    private int id;
    private long accessHash;

    public InputUserOrPeerForeign(int id, long accessHash) {
        this.id = id;
        this.accessHash = accessHash;
    }

    public int getId() {
        return id;
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
