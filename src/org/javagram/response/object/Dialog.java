package org.javagram.response.object;

import org.telegram.api.TLDialog;
import org.telegram.api.TLPeerUser;

/**
 * Created by HerrSergio on 13.04.2016.
 */
public class Dialog {

    private Integer anotherUserId;
    private int topMessage;
    private int unreadCount;

    public Dialog(TLDialog tlDialog) {
        //TODO: To implement
        if(tlDialog.getPeer() instanceof TLPeerUser) {
            TLPeerUser peer = (TLPeerUser)tlDialog.getPeer();
            anotherUserId = peer.getUserId();
        } else {
            anotherUserId = null;
        }

        topMessage = tlDialog.getTopMessage();
        unreadCount = tlDialog.getUnreadCount();
    }

    public int getTopMessage() {
        return topMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public Integer getAnotherUserId() {
        return anotherUserId;
    }
}
