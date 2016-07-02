package org.javagram.response.object.updates;

import org.javagram.TelegramApiBridge;
import org.javagram.response.object.User;
import org.telegram.api.TLAbsUserProfilePhoto;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * Created by HerrSergio on 22.05.2016.
 */
public class UpdateUserPhoto implements Update {
    private int userId;
    private Date date;
    private TLAbsUserProfilePhoto photo;
    private boolean previous;

    public UpdateUserPhoto(int userId, Date date, TLAbsUserProfilePhoto photo, boolean previous) {
        this.userId = userId;
        this.date = date;
        this.photo = photo;
        this.previous = previous;
    }

    public int getUser() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public BufferedImage getPhoto(TelegramApiBridge telegramApiBridge, boolean small) {
        try {
            return telegramApiBridge.getPhoto(photo, small);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPrevious() {
        return previous;
    }
}
