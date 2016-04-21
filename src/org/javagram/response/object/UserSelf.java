package org.javagram.response.object;

import org.telegram.api.TLUserSelf;

/**
 * Created by HerrSergio on 16.04.2016.
 */
public class UserSelf extends User {

    public UserSelf(TLUserSelf tlUserSelf) {
        super(tlUserSelf);
    }

    @Override
    public InputUserSelf getInputUser() {
        return new InputUserSelf();
    }
}
