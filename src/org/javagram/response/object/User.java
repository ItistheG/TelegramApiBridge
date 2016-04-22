package org.javagram.response.object;

import org.javagram.TelegramApiBridge;
import org.javagram.core.StaticContainer;
import org.telegram.api.*;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.upload.TLFile;
import org.telegram.tl.TLBytes;

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

    public byte[] getPhoto(boolean small) throws IOException
    {
        TelegramApi api = StaticContainer.getTelegramApi();

        if(!(photo instanceof TLUserProfilePhoto)) {
            return null;
        }
        TLUserProfilePhoto profilePhoto = (TLUserProfilePhoto) photo;
        TLAbsFileLocation location = small ? profilePhoto.getPhotoSmall() : profilePhoto.getPhotoBig();
        if(!(location instanceof TLFileLocation)) {
            return null;
        }

        TLFileLocation fileLocation = (TLFileLocation) location;
        int dcId = api.getState().getPrimaryDc(); //fileLocation.getDcId();

        TLInputFileLocation inputLocation = new TLInputFileLocation(
            fileLocation.getVolumeId(),
            fileLocation.getLocalId(),
            fileLocation.getSecret()
        );

        TLFile res;
        try {
            res = api.doGetFile(dcId, inputLocation, 0, 1024 * 1024 * 1024);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        return res.getBytes().cleanData();
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
