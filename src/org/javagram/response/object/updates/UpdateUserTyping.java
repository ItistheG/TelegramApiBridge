package org.javagram.response.object.updates;

import org.javagram.response.object.User;
import org.telegram.api.TLUpdateUserTyping;

import java.util.Date;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class UpdateUserTyping implements Update {

    private User user;
    private Date expires;

    public UpdateUserTyping(User user) {
        this.user = user;
        this.expires = new Date(System.currentTimeMillis() + 6000);
    }

    public User getUser() {
        return user;
    }

    public Date getExpires() {
        return expires;
    }
}
