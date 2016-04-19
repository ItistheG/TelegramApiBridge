package org.javagram.response.object;

import org.javagram.response.InconsistentDataException;
import org.telegram.api.*;
import org.telegram.api.messages.TLAbsDialogs;
import org.telegram.api.messages.TLDialogsSlice;
import org.telegram.tl.TLVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HerrSergio on 17.04.2016.
 */
public class MessagesDialog {

    private User peerUser;
    private MessagesMessage topMessage;
    private int unreadCount;

    MessagesDialog(TLDialog tlDialog, User user, MessagesMessage message) {

        if (tlDialog.getPeer() instanceof TLPeerUser) {
            TLPeerUser peer = (TLPeerUser) tlDialog.getPeer();
            if (user.getId() == peer.getUserId()
                    && message.getId() == tlDialog.getTopMessage()) {

                peerUser = user;
                topMessage = message;
                unreadCount = tlDialog.getUnreadCount();

                return;
            }
        }

        throw new InconsistentDataException();
    }

    public User getPeerUser() {
        return peerUser;
    }

    public MessagesMessage getTopMessage() {
        return topMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public static ArrayList<MessagesDialog> create(TLAbsDialogs tlAbsDialogs, Map<Integer, User> users) {

        if(users == null)
            users = new HashMap<>();

        ArrayList<MessagesDialog> messagesDialogs = new ArrayList<>();

        int count = tlAbsDialogs.getDialogs().size();

        if(count != tlAbsDialogs.getMessages().size())
            throw new InconsistentDataException();

        TLVector<TLAbsUser> tlAbsUsers = tlAbsDialogs.getUsers();
        for (TLAbsUser tlAbsUser : tlAbsUsers) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(tlAbsUser.getId(), user);
        }

        for(int i = 0; i < count; i++) {

            TLDialog tlDialog = tlAbsDialogs.getDialogs().get(i);
            TLAbsMessage tlMessage = tlAbsDialogs.getMessages().get(i);

            if(tlDialog.getPeer() instanceof TLPeerUser) {

                TLPeerUser tlPeerUser = (TLPeerUser)tlDialog.getPeer() ;

                MessagesMessage messagesMessage = new MessagesMessage(tlMessage, users);
                MessagesDialog messagesDialog = new MessagesDialog(tlDialog, users.get(tlPeerUser.getUserId()), messagesMessage);

                messagesDialogs.add(messagesDialog);

            } else if(tlDialog.getPeer() instanceof TLPeerChat) {


            } else {
                throw new InconsistentDataException();
            }
        }

        return messagesDialogs;
    }
}
