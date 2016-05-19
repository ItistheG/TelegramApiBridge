package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.contacts.TLAbsForeignLink;
import org.telegram.api.contacts.TLForeignLinkMutual;
import org.telegram.api.contacts.TLForeignLinkRequested;
import org.telegram.api.contacts.TLForeignLinkUnknown;

/**
 * Created by HerrSergio on 05.05.2016.
 */
public enum ContactsForeignLink {
    UNKNOWN, REQUESTED_HAS_PHONE, REQUESTED_HASNT_PHONE, MUTUAL;

    public static ContactsForeignLink getFor(TLAbsForeignLink tlAbsForeignLink) {
        if(tlAbsForeignLink instanceof TLForeignLinkUnknown)
            return UNKNOWN;
        else if(tlAbsForeignLink instanceof TLForeignLinkRequested) {
            if(((TLForeignLinkRequested) tlAbsForeignLink).getHasPhone())
                return REQUESTED_HAS_PHONE;
            else
                return REQUESTED_HASNT_PHONE;
        } else if(tlAbsForeignLink instanceof TLForeignLinkMutual)
            return MUTUAL;
        else
            throw new InconsistentDataException();
    }
}
