package org.javagram.response.object.updates;

import org.javagram.response.object.User;
import org.telegram.api.TLUpdateUserTyping;

import java.util.Date;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserTyping implements Update {

    private int userId;
    private Date expires;

    public UpdateUserTyping(int userId) {
        this.userId = userId;
        this.expires = new Date(System.currentTimeMillis() + 6000);
    }

    public int getUser() {
        return userId;
    }

    public Date getExpires() {
        return expires;
    }
}
