package org.javagram.response.object.updates;

import org.javagram.response.object.User;
import org.javagram.response.object.statuses.UserStatus;

import java.util.Date;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserStatus implements Update {
    private User user;
    private Date expires;

    public UpdateUserStatus(User user, Date expires) {
        this.user = user;
        this.expires = expires;
    }

    public User getUser() {
        return user;
    }

    public Date getExpires() {
        return expires;
    }
}
