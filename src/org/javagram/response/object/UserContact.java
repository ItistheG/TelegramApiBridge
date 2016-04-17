package org.javagram.response.object;

import org.telegram.api.*;

/**
 * Created by Danya on 04.09.2015.
 */
public class UserContact extends User
{
    private long accessHash;
    private boolean isOnline;

    public UserContact(TLUserContact tlUserContact)
    {
        super(tlUserContact);
        accessHash = tlUserContact.getAccessHash();
        isOnline = tlUserContact.getStatus() instanceof TLUserStatusOnline;
    }

  /*  public UserContact(TLUserSelf tlUserSelf)
    {
        super(tlUserSelf);
    }*/

    public long getAccessHash()
    {
        return accessHash;
    }

    public boolean isOnline()
    {
        return isOnline;
    }
}
