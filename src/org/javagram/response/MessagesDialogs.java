package org.javagram.response;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;
import org.telegram.api.*;
import org.telegram.api.messages.TLAbsDialogs;
import org.telegram.api.messages.TLDialogsSlice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HerrSergio on 14.04.2016.
 */
public class MessagesDialogs {

    private List<Dialog> dialogs = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public MessagesDialogs(TLAbsDialogs tlAbsDialogs) {

        int count = tlAbsDialogs.getDialogs().size();

        if(count != tlAbsDialogs.getMessages().size())
            throw new InconsistentDataException();

        for(int i = 0; i < count; i++) {
            TLDialog tlDialog = tlAbsDialogs.getDialogs().get(i);
            TLAbsMessage tlMessage = tlAbsDialogs.getMessages().get(i);

            if(tlDialog.getPeer() instanceof TLPeerUser) {
                Dialog dialog = new Dialog(tlDialog);

                if(tlMessage instanceof TLMessage || tlMessage instanceof TLMessageForwarded) {
                    Message message = new Message(tlMessage);

                    if(message.getFromId() != dialog.getAnotherUserId()
                            && message.getToId().intValue() != dialog.getAnotherUserId().intValue())
                        throw new InconsistentDataException();

                    dialogs.add(dialog);
                    messages.add(message);

                }
            }
        }

        boolean dialogNeeded[] = new boolean[count];

        for(TLAbsUser tlAbsUser : tlAbsDialogs.getUsers()) {
            if(tlAbsUser instanceof TLUserContact) {

                boolean needed = false;

                for (int i = 0; i < messages.size(); i++) {
                    Message message = messages.get(i);
                    if (message.getFromId() == tlAbsUser.getId()
                            || message.getToId() == tlAbsUser.getId()) {
                        needed = true;
                        dialogNeeded[i] = true;
                        break;
                    }
                }

                if(needed)
                    users.add(new UserContact((TLUserContact)tlAbsUser));
            }
        }

        // Надо удалить сообщения от неконтактов?
        for(int i = dialogNeeded.length - 1; i >=0; i--) {
            if(!dialogNeeded[i]) {
                dialogs.remove(i);
                messages.remove(i);
            }
        }

        // slice = tlAbsDialogs instanceof TLDialogsSlice;


        dialogs = Collections.unmodifiableList(dialogs);
        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);
    }

    /*public boolean isSlice() {
        return slice;
    }*/

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }
}
