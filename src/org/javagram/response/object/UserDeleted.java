package org.javagram.response.object;

import org.telegram.api.TLUserDeleted;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserDeleted extends User {

    public UserDeleted(TLUserDeleted userDeleted) {
        super(userDeleted);
    }
}
