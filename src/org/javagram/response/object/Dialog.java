package org.javagram.response.object;

import org.telegram.api.TLDialog;
import org.telegram.api.TLPeerChat;
import org.telegram.api.TLPeerUser;

/**
 * Created by HerrSergio on 13.04.2016.
 */
public class Dialog {

    private Integer peerUserId, peerChatId;
    private int topMessage;
    private int unreadCount;

    public Dialog(TLDialog tlDialog) {

        if(tlDialog.getPeer() instanceof TLPeerUser) {
            TLPeerUser peer = (TLPeerUser)tlDialog.getPeer();
            peerUserId = peer.getUserId();
        } else if(tlDialog.getPeer() instanceof TLPeerChat) {
            TLPeerChat peer = (TLPeerChat)tlDialog.getPeer();
            peerChatId = peer.getChatId();
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

    public Integer getPeerUserId() {
        return peerUserId;
    }

    public Integer getPeerChatId() {
        return peerChatId;
    }

    public boolean isUserDialog() {
        return peerUserId != null;
    }

    public boolean isChat() {
        return peerChatId != null;
    }
}
