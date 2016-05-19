package org.javagram.response.object.statuses;

import org.telegram.api.TLUserStatusOffline;

import java.util.Date;

/**
 * Created by HerrSergio on 28.04.2016.
 */
public class UserStatusOffline implements UserStatus {

    private Date wasOnline;

    public UserStatusOffline(TLUserStatusOffline tlUserStatusOffline) {
        wasOnline = new Date(tlUserStatusOffline.getWasOnline());
    }

    public Date getWasOnline() {
        return wasOnline;
    }

}
