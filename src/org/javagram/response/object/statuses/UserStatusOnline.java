package org.javagram.response.object.statuses;

import org.javagram.response.object.Message;
import org.telegram.api.TLUserStatusOnline;

import java.util.Date;
import static org.javagram.response.Helper.*;

/**
 * Created by HerrSergio on 28.04.2016.
 */
public class UserStatusOnline implements UserStatus {

    private Date expires;

    public UserStatusOnline(TLUserStatusOnline tlUserStatusOnline) {
        this.expires = intToDate(tlUserStatusOnline.getExpires());
    }

    public Date getExpires() {
        return expires;
    }
}
