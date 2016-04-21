package org.javagram.response.object;

import org.telegram.api.TLUserForeign;
import org.telegram.api.TLUserRequest;

/**
 * Created by HerrSergio on 21.04.2016.
 */
@Deprecated
public abstract class UserNonContact extends User {

    private long accessHash;

    protected UserNonContact(TLUserForeign userForeign) {
        super(userForeign);
        accessHash = userForeign.getAccessHash();
    }

    protected UserNonContact(TLUserRequest userRequest) {
        super(userRequest);
        accessHash = userRequest.getAccessHash();
    }

   /* public long getAccessHash() {
        return accessHash;
    }*/
}
