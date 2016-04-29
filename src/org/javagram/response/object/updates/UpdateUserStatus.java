package org.javagram.response.object.updates;

import org.javagram.response.object.User;
import org.javagram.response.object.UserStatus;
import org.telegram.api.TLAbsUserStatus;
import org.telegram.api.TLUserStatusEmpty;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserStatus implements Update {
    private User user;
    private UserStatus status;

    public UpdateUserStatus(User user, UserStatus status) {
        this.user = user;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public UserStatus getStatus() {
        return status;
    }
}
