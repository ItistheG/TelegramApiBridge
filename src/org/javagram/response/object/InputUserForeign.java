package org.javagram.response.object;

import org.telegram.api.TLAbsInputPeer;
import org.telegram.api.TLAbsInputUser;
import org.telegram.api.TLInputPeerForeign;
import org.telegram.api.TLInputUserForeign;

/**
 * Created by HerrSergio on 21.04.2016.
 */
public class InputUserForeign extends InputUser {

    private int id;
    private long accessHash;


    public InputUserForeign(int id, long accessHash) {
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
