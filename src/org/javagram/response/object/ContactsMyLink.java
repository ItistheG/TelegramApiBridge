package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.contacts.*;

/**
 * Created by HerrSergio on 05.05.2016.
 */
public enum ContactsMyLink {
    EMPTY, REQUESTED_IS_CONTACT, REQUESTED_ISNT_CONTACT, CONTACT;

    public static ContactsMyLink getFor(TLAbsMyLink tlAbsMyLink) {
        if(tlAbsMyLink instanceof TLMyLinkEmpty)
            return EMPTY;
        else if(tlAbsMyLink instanceof TLMyLinkContact)
            return CONTACT;
        else if(tlAbsMyLink instanceof TLMyLinkRequested) {
            if(((TLMyLinkRequested) tlAbsMyLink).getContact())
                return REQUESTED_IS_CONTACT;
            else
                return REQUESTED_ISNT_CONTACT;
        } else
            throw  new InconsistentDataException();
    }
}
