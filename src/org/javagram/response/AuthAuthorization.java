package org.javagram.response;

import org.javagram.response.object.User;
import org.javagram.response.object.UserSelf;
import org.telegram.api.TLUserSelf;
import org.telegram.api.auth.TLAuthorization;

/**
 * Created by Danya on 04.09.2015.
 */
public class AuthAuthorization
{
    private long expires;
    private User selfUser;

    public AuthAuthorization(TLAuthorization authorization)
    {
        this.expires = authorization.getExpires();
        this.selfUser = User.createUser(authorization.getUser());
    }

    public long getExpires()
    {
        return expires;
    }

    public User getUser()
    {
        return selfUser;
    }
}