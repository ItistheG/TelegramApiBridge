package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.TLAbsUserStatus;
import org.telegram.api.TLUserStatusEmpty;
import org.telegram.api.TLUserStatusOffline;
import org.telegram.api.TLUserStatusOnline;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public interface UserStatus {

    static UserStatus create(TLAbsUserStatus tlAbsUserStatus) {
        if(tlAbsUserStatus instanceof TLUserStatusOnline) {
            return new UserStatusOnline((TLUserStatusOnline)tlAbsUserStatus);
        } else if(tlAbsUserStatus instanceof TLUserStatusOffline) {
            return new UserStatusOffline((TLUserStatusOffline)tlAbsUserStatus);
        } else if(tlAbsUserStatus instanceof TLUserStatusEmpty) {
            return new UserStatusEmpty((TLUserStatusEmpty)tlAbsUserStatus);
        } else {
            throw new InconsistentDataException();
        }
    }
}
