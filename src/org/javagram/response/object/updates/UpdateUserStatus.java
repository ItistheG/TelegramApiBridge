package org.javagram.response.object.updates;

import org.javagram.response.object.User;
import org.javagram.response.object.statuses.UserStatus;

import java.util.Date;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserStatus implements Update {
    private int userId;
    private Date expires;

    public UpdateUserStatus(int userId, Date expires) {
        this.userId = userId;
        this.expires = expires;
    }

    public int getUser() {
        return userId;
    }

    public Date getExpires() {
        return expires;
    }
}
