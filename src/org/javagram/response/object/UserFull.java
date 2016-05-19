package org.javagram.response.object;

import org.telegram.api.TLUserFull;

/**
 * Created by HerrSergio on 05.05.2016.
 */
public class UserFull  {

    private User user;
    private ContactsMyLink myLink;
    private ContactsForeignLink foreignLink;
    private boolean blocked;
    private String realFirstName;
    private String realLastName;

    public UserFull(TLUserFull tlUserFull) {
        user = User.createUser(tlUserFull.getUser());
        myLink = ContactsMyLink.getFor(tlUserFull.getLink().getMyLink());
        foreignLink = ContactsForeignLink.getFor(tlUserFull.getLink().getForeignLink());
        blocked = tlUserFull.getBlocked();
        realLastName = tlUserFull.getRealLastName();
        realFirstName = tlUserFull.getRealFirstName();
    }

    public User getUser() {
        return user;
    }

    public ContactsMyLink getMyLink() {
        return myLink;
    }

    public ContactsForeignLink getForeignLink() {
        return foreignLink;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public String getRealFirstName() {
        return realFirstName;
    }

    public String getRealLastName() {
        return realLastName;
    }
}
