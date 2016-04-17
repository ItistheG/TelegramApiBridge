package org.javagram.response.object;

import org.telegram.api.TLUserForeign;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserForeign extends User {

    private long accessHash;

    public UserForeign(TLUserForeign userForeign) {
        super(userForeign);
        accessHash = userForeign.getAccessHash();
    }

    public long getAccessHash() {
        return accessHash;
    }
}
