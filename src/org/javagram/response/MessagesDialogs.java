package org.javagram.response;

import org.javagram.response.InconsistentDataException;
import org.javagram.response.object.*;
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
@Deprecated
public class MessagesDialogs {

    private List<Dialog> dialogs = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Chat> chats = new ArrayList<>();
    private Integer count;

    public MessagesDialogs(TLAbsDialogs tlAbsDialogs) {

        int count = tlAbsDialogs.getDialogs().size();

        if(count != tlAbsDialogs.getMessages().size())
            throw new InconsistentDataException();

        for(int i = 0; i < count; i++) {

            TLDialog tlDialog = tlAbsDialogs.getDialogs().get(i);
            TLAbsMessage tlMessage = tlAbsDialogs.getMessages().get(i);

            Dialog dialog = new Dialog(tlDialog);
            Message message = new Message(tlMessage);

            if(tlDialog.getPeer() instanceof TLPeerUser) {

                if(message.getFromId() != dialog.getPeerUserId()
                        && message.getToPeerUserId().intValue() != dialog.getPeerUserId().intValue())
                    throw new InconsistentDataException();

                dialogs.add(dialog);
                messages.add(message);

            } else if(tlDialog.getPeer() instanceof TLPeerChat) {



            } else {
                throw new InconsistentDataException();
            }
        }

        //boolean dialogNeeded[] = new boolean[count];

        for(TLAbsUser tlAbsUser : tlAbsDialogs.getUsers()) {
            if(!(tlAbsUser instanceof TLUserSelf)) {

                for (int i = 0; i < messages.size(); i++) {
                    Message message = messages.get(i);

                    if (message.getFromId() == tlAbsUser.getId()
                            || message.getToPeerUserId() == tlAbsUser.getId()) {
                        users.add(User.createUser(tlAbsUser));
                        //dialogNeeded[i] = true;
                        break;
                    }
                }
            }
        }

        //TODO: Надо удалить сообщения от неконтактов?
        /*for(int i = dialogNeeded.length - 1; i >=0; i--) {
            if(!dialogNeeded[i]) {
                dialogs.remove(i);
                messages.remove(i);
            }
        }*/

        if(tlAbsDialogs instanceof TLDialogsSlice)
            this.count = ((TLDialogsSlice) tlAbsDialogs).getCount();

        dialogs = Collections.unmodifiableList(dialogs);
        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);
        chats = Collections.unmodifiableList(chats);
    }

    /*public boolean isSlice() {
        return slice;
    }*/

    /*public MessagesDialogs(MessagesDialogs ... slices) {

        if(slices.length > 0) {

            count = slices[0].count;

            for(MessagesDialogs slice : slices) {
                if(!slice.isSlice() || slice.count.intValue() != count)
                    throw new IllegalArgumentException();

                this.users.addAll(slice.users);
                this.dialogs.addAll(slice.dialogs);
                this.messages.addAll(slice.messages);
                this.chatsCount += slice.chatsCount;
            }

            if(count > this.users.size())
                throw new IllegalArgumentException();

            if(count == this.messages.size())
                count = null;
        }

        dialogs = Collections.unmodifiableList(dialogs);
        messages = Collections.unmodifiableList(messages);
        users = Collections.unmodifiableList(users);
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

    public Integer getCount() {
        return count;
    }

    public boolean isSlice() {
        return count != null;
    }

    public List<Chat> getChats() {
        return chats;
    }
}
