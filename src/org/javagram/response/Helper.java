package org.javagram.response;

import org.javagram.response.object.Message;
import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.javagram.response.object.updates.*;
import org.telegram.api.*;

import java.util.*;

/**
 * Created by HerrSergio on 27.04.2016.
 */
public class Helper {

    private Helper() {

    }

    public static void acceptTLAbsMessages(List<? super MessagesMessage> messagesMessages,
                                    List<TLAbsUser> tlAbsUserList,
                                    List<TLAbsMessage> tlAbsMessageList,
                                    Map<Integer, User> users,
                                    Set<? super User> detectedUsers
    ) {
        if(users == null)
            users = new HashMap<>();

        if(detectedUsers == null)
            detectedUsers = new HashSet<>();

        for(TLAbsUser tlAbsUser : tlAbsUserList) {
            User user = User.createUser(tlAbsUser);
            users.putIfAbsent(user.getId(), user);
        }

        for(TLAbsMessage tlAbsMessage : tlAbsMessageList) {
            try {
                MessagesMessage messagesMessage = new MessagesMessage(tlAbsMessage, users, detectedUsers);
                messagesMessages.add(messagesMessage);
            } catch (ExpectedInconsistentDataException e) {
                //Убираем сообщения из чатов
            }
        }
    }

    static HashSet<User> getUsers(List<MessagesMessage> messagesMessages) {
        HashSet<User> users = new HashSet<>();
        for(MessagesMessage messagesMessage : messagesMessages) {
            users.add(messagesMessage.getFrom());
            users.add(messagesMessage.getToPeerUser());
            if(messagesMessage.isForwarded())
                users.add(messagesMessage.getFwdFrom());
        }
        return users;
    }


    static void acceptTLOtherUpdates(List<? super Update> updates, List<TLAbsUpdate> tlAbsUpdates, Map<Integer, ? extends MessagesMessage> messagesMessages,
                                Map<Integer, ? extends User> users, Set<? super User> users2) {

        for(TLAbsUpdate tlAbsUpdate : tlAbsUpdates) {
            if(tlAbsUpdate instanceof TLUpdateNewMessage) {
                TLUpdateNewMessage tlUpdateNewMessage = (TLUpdateNewMessage)tlAbsUpdate;
                if(!messagesMessages.containsKey(tlUpdateNewMessage.getMessage().getId()))
                    continue;
                MessagesMessage message = messagesMessages.get(tlUpdateNewMessage.getMessage().getId());
                updates.add(new UpdateNewMessage(message, tlUpdateNewMessage.getPts()));
            } else if(tlAbsUpdate instanceof TLUpdateMessageID) {
                TLUpdateMessageID tlUpdateMessageID = (TLUpdateMessageID)tlAbsUpdate;
                if(messagesMessages.containsKey(tlUpdateMessageID.getId()))
                    updates.add(new UpdateMessageIDExt(messagesMessages.get(tlUpdateMessageID.getId()), tlUpdateMessageID.getRandomId()));
            } else {
                acceptNonMessageUpdate(tlAbsUpdate, updates, users, users2, 0);
            }
        }
    }

    public static int acceptTLUpdates(List<? super Update> updates, List<TLAbsUpdate> tlAbsUpdates, List<Message> messages2,
                                             Map<Integer, ? extends User> users, Set<? super User> users2) {

        int pts = 0;

        for(TLAbsUpdate tlAbsUpdate : tlAbsUpdates) {
            if(tlAbsUpdate instanceof TLUpdateNewMessage) {
                TLUpdateNewMessage tlUpdateNewMessage = (TLUpdateNewMessage)tlAbsUpdate;
                //if(idOf(messages2, tlUpdateNewMessage.getMessage().getId()) == null)
                Message message = new Message(tlUpdateNewMessage.getMessage());
                messages2.add(message);
                //idOf(messages2, tlUpdateNewMessage.getMessage().getId());
                updates.add(new UpdateNewMessage(message, tlUpdateNewMessage.getPts()));//???
                pts = Math.max(pts, tlUpdateNewMessage.getPts());
            } else if(tlAbsUpdate instanceof TLUpdateMessageID) {
                TLUpdateMessageID tlUpdateMessageID = (TLUpdateMessageID)tlAbsUpdate;
                updates.add(new UpdateMessageID(tlUpdateMessageID.getId(), tlUpdateMessageID.getRandomId()));
            } else {
                pts = acceptNonMessageUpdate(tlAbsUpdate, updates, users, users2, pts);
            }
        }

        return pts;
    }

    private static int acceptNonMessageUpdate(TLAbsUpdate tlAbsUpdate, List<? super Update> updates, Map<Integer, ? extends User> users, Set<? super User> users2, int pts) {
        if(tlAbsUpdate instanceof TLUpdateReadMessages) {
            TLUpdateReadMessages tlUpdateReadMessages = (TLUpdateReadMessages)tlAbsUpdate;
            ArrayList<Integer> messages = new ArrayList<>();
            for(Integer id : tlUpdateReadMessages.getMessages()) {
                messages.add(id);
            }
            if(messages.size() > 0) {
                updates.add(new UpdateReadMessage(messages, ((TLUpdateReadMessages) tlAbsUpdate).getPts()));
            }
            pts = Math.max(pts, tlUpdateReadMessages.getPts());
        } else if(tlAbsUpdate instanceof TLUpdateDeleteMessages) {
            TLUpdateDeleteMessages tlUpdateDeleteMessages = (TLUpdateDeleteMessages)tlAbsUpdate;
            ArrayList<Integer> messages = new ArrayList<>();
            for(Integer id : tlUpdateDeleteMessages.getMessages()) {
                messages.add(id);
            }
            if(messages.size() > 0) {
                updates.add(new UpdateDeleteMessages(messages, tlUpdateDeleteMessages.getPts()));
            }
            pts = Math.max(pts, tlUpdateDeleteMessages.getPts());
        } else if(tlAbsUpdate instanceof TLUpdateRestoreMessages) {
            TLUpdateRestoreMessages tlUpdateRestoreMessages = (TLUpdateRestoreMessages)tlAbsUpdate;
            ArrayList<Integer> messages = new ArrayList<>();
            for(Integer id : tlUpdateRestoreMessages.getMessages()) {
                messages.add(id);
            }
            if(messages.size() > 0) {
                updates.add(new UpdateRestoreMessages(messages, tlUpdateRestoreMessages.getPts()));
            }
            pts = Math.max(pts, tlUpdateRestoreMessages.getPts());
        } else if(tlAbsUpdate instanceof TLUpdateUserName) {
            TLUpdateUserName tlUpdateUserName = (TLUpdateUserName)tlAbsUpdate;
            int userId = tlUpdateUserName.getUserId();
            updates.add(new UpdateUserName(userId, tlUpdateUserName.getFirstName(), tlUpdateUserName.getLastName()));
        } else if(tlAbsUpdate instanceof TLUpdateUserPhoto) {
            TLUpdateUserPhoto tlUpdateUserPhoto = (TLUpdateUserPhoto)tlAbsUpdate;
            int userId = tlUpdateUserPhoto.getUserId();
            updates.add(new UpdateUserPhoto(userId,  intToDate(tlUpdateUserPhoto.getDate()), tlUpdateUserPhoto.getPhoto(), tlUpdateUserPhoto.getPrevious()));
        } else if(tlAbsUpdate instanceof TLUpdateUserStatus) {
            TLUpdateUserStatus tlUpdateUserStatus = (TLUpdateUserStatus)tlAbsUpdate;
            int userId = tlUpdateUserStatus.getUserId();
            updates.add(new UpdateUserStatus(userId, getExpires(tlUpdateUserStatus.getStatus())));
        } else if(tlAbsUpdate instanceof TLUpdateUserTyping) {
            TLUpdateUserTyping tlUpdateUserTyping = (TLUpdateUserTyping)tlAbsUpdate;
            int userId = tlUpdateUserTyping.getUserId();
            updates.add(new UpdateUserTyping(userId));
        } else if(tlAbsUpdate instanceof TLUpdateContactLink) {
            updates.add(new UpdateContact());
        } else if(tlAbsUpdate instanceof TLUpdateContactRegistered) {
            updates.add(new UpdateContact());
        }
        return pts;
    }

    static Message idOf(Collection<Message> messages, int messageId) {
        for(Message message : messages) {
            if(message.getId() == messageId)
                return message;
        }
        return null;
    }

    static Date getExpires(TLAbsUserStatus tlAbsUserStatus) {
        if(tlAbsUserStatus instanceof TLUserStatusOnline) {
            return intToDate(((TLUserStatusOnline) tlAbsUserStatus).getExpires());
        } else if(tlAbsUserStatus instanceof TLUserStatusOffline) {
            return intToDate(((TLUserStatusOffline) tlAbsUserStatus).getWasOnline());
        } else if(tlAbsUserStatus instanceof TLUserStatusEmpty) {
            return null;
        } else {
            throw new InconsistentDataException();
        }
    }

    /*static Map<Integer, Message> createMessagesMap(Collection<? extends Message> collection) {
        Map<Integer, Message> messageMap = new LinkedHashMap<>();
        for(Message message : collection)
            messageMap.put(message.getId(), message);
        return messageMap;
    }*/

    static Map<Integer, MessagesMessage> createMessagesMap(Collection<? extends MessagesMessage> collection) {
        Map<Integer, MessagesMessage> messagesMessageMap = new LinkedHashMap<>();
        for(MessagesMessage messagesMessage : collection)
            messagesMessageMap.put(messagesMessage.getId(), messagesMessage);
        return  messagesMessageMap;
    }

    static Map<Integer, User> createUsersMap(Collection<? extends User> collection) {
        Map<Integer, User> users = new LinkedHashMap<>();
        for(User user : collection)
            users.put(user.getId(), user);
        return users;
    }

    private static final long DATE_MULTIPLIER = 1000;

    public static int dateToInt(Date date) {
        if(date == null)
            return 0;
        else
            return (int)(date.getTime() / DATE_MULTIPLIER);
    }

    public static Date intToDate(int date) {
        return new Date(date * DATE_MULTIPLIER);
    }

}
