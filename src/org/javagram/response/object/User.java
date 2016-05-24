package org.javagram.response.object;

import org.javagram.TelegramApiBridge;
import org.javagram.core.StaticContainer;
import org.telegram.api.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Danya on 20.09.2015.
 */
public abstract class User implements InputUser, InputPeer
{
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private TLAbsUserProfilePhoto photo;

    User(TLUserContact userContact)
    {
        id = userContact.getId();
        firstName = userContact.getFirstName();
        lastName = userContact.getLastName();
        phone = userContact.getPhone();
        photo = userContact.getPhoto();
    }

    User(TLUserSelf userSelf)
    {
        id = userSelf.getId();
        firstName = userSelf.getFirstName();
        lastName = userSelf.getLastName();
        phone = userSelf.getPhone();
        photo = userSelf.getPhoto();
    }

    User(TLUserRequest userRequest)
    {
        id = userRequest.getId();
        firstName = userRequest.getFirstName();
        lastName = userRequest.getLastName();
        phone = userRequest.getPhone();
        photo = userRequest.getPhoto();
    }

    User(TLUserForeign userForeign)
    {
        id = userForeign.getId();
        firstName = userForeign.getFirstName();
        lastName = userForeign.getLastName();
        phone = "";
        photo = userForeign.getPhoto();
    }

    User(TLUserDeleted userDeleted)
    {
        id = userDeleted.getId();
        firstName = userDeleted.getFirstName();
        lastName = userDeleted.getLastName();
        phone = "";
        photo = null;
    }

    User(TLUserEmpty userEmpty)
    {
        id = userEmpty.getId();
        firstName = "";
        lastName = "";
        phone = "";
        photo = null;
    }

    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPhone()
    {
        return phone;
    }

    public BufferedImage getPhoto(TelegramApiBridge telegramApiBridge, boolean small) {
        try {
            return telegramApiBridge.getPhoto(photo, small);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public byte[] getPhoto(boolean small) {
        try {
            return TelegramApiBridge.getPhotoAsBytes(StaticContainer.getTelegramApi(), photo, small);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString()
    {
        String contact = getFirstName() + " " + getLastName();
        return contact.trim();
    }

    public static User createUser(TLAbsUser absUser)
    {
        if(absUser instanceof TLUserContact)
        {
            return new UserContact((TLUserContact)absUser);
        }
        else if(absUser instanceof TLUserSelf)
        {
            return new UserSelf((TLUserSelf)absUser);
        }
        else if (absUser instanceof TLUserForeign)
        {
            return new UserForeign((TLUserForeign)absUser);
        }
        else if (absUser instanceof TLUserDeleted)
        {
            return new UserDeleted((TLUserDeleted)absUser);
        }
        else if (absUser instanceof TLUserRequest)
        {
            return new UserRequest((TLUserRequest)absUser);
        }
        else if (absUser instanceof TLUserEmpty)
        {
            return new UserEmpty((TLUserEmpty)absUser);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            return this.getId() == ((User) obj).getId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
