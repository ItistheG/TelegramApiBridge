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

    static void acceptTLAbsMessages(List<MessagesMessage> messagesMessages,
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

    static void acceptTLUpdates(List<? super Update> updates, List<TLAbsUpdate> tlAbsUpdates, Map<Integer, ? extends MessagesMessage> messagesMessages,
                                             Map<Integer, ? extends User> users, Set<? super User> users2) {

      //  ArrayList<Update> updates = new ArrayList<>();

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
                    updates.add(new UpdateMessageID(messagesMessages.get(tlUpdateMessageID.getId()), tlUpdateMessageID.getRandomId()));
            } else if(tlAbsUpdate instanceof TLUpdateReadMessages) {
                TLUpdateReadMessages tlUpdateReadMessages = (TLUpdateReadMessages)tlAbsUpdate;
               /* ArrayList<MessagesMessage> messages = new ArrayList<>();
                for(Integer id : tlUpdateReadMessages.getMessages()) {
                    if(!messagesMessages.containsKey(id))
                        continue;
                    MessagesMessage message = messagesMessages.get(id);
                    messages.add(message);
                }*/
                ArrayList<Integer> messages = new ArrayList<>();
                for(Integer id : tlUpdateReadMessages.getMessages()) {
                    messages.add(id);
                }
                if(messages.size() > 0) {
                    updates.add(new UpdateReadMessage(messages, ((TLUpdateReadMessages) tlAbsUpdate).getPts()));
                }
            } else if(tlAbsUpdate instanceof TLUpdateDeleteMessages) {
                TLUpdateDeleteMessages tlUpdateDeleteMessages = (TLUpdateDeleteMessages)tlAbsUpdate;
                /*ArrayList<MessagesMessage> messages = new ArrayList<>();
                for(Integer id : tlUpdateDeleteMessages.getMessages()) {
                    if(!messagesMessages.containsKey(id))
                        continue;
                    MessagesMessage message = messagesMessages.get(id);
                    messages.add(message);
                }*/
                ArrayList<Integer> messages = new ArrayList<>();
                for(Integer id : tlUpdateDeleteMessages.getMessages()) {
                    messages.add(id);
                }
                if(messages.size() > 0) {
                    updates.add(new UpdateDeleteMessages(messages, tlUpdateDeleteMessages.getPts()));
                }
            } else if(tlAbsUpdate instanceof TLUpdateRestoreMessages) {
                TLUpdateRestoreMessages tlUpdateRestoreMessages = (TLUpdateRestoreMessages)tlAbsUpdate;
                /*ArrayList<MessagesMessage> messages = new ArrayList<>();
                for(Integer id : tlUpdateRestoreMessages.getMessages()) {
                    if(!messagesMessages.containsKey(id))
                        continue;
                    MessagesMessage message = messagesMessages.get(id);
                    messages.add(message);
                }*/
                ArrayList<Integer> messages = new ArrayList<>();
                for(Integer id : tlUpdateRestoreMessages.getMessages()) {
                    messages.add(id);
                }
                if(messages.size() > 0) {
                    updates.add(new UpdateRestoreMessages(messages, tlUpdateRestoreMessages.getPts()));
                }
            } else if(tlAbsUpdate instanceof TLUpdateUserName) {
                TLUpdateUserName tlUpdateUserName = (TLUpdateUserName)tlAbsUpdate;
                User user = users.get(tlUpdateUserName.getUserId());
                users2.add(user);
                updates.add(new UpdateUserName(user, tlUpdateUserName.getFirstName(), tlUpdateUserName.getLastName()));
            } else if(tlAbsUpdate instanceof TLUpdateUserPhoto) {
                TLUpdateUserPhoto tlUpdateUserPhoto = (TLUpdateUserPhoto)tlAbsUpdate;
                User user = users.get(tlUpdateUserPhoto.getUserId());
                users2.add(user);
                updates.add(new UpdateUserPhoto(user,  intToDate(tlUpdateUserPhoto.getDate()), tlUpdateUserPhoto.getPhoto(), tlUpdateUserPhoto.getPrevious()));
            } else if(tlAbsUpdate instanceof TLUpdateUserStatus) {
                TLUpdateUserStatus tlUpdateUserStatus = (TLUpdateUserStatus)tlAbsUpdate;
                User user = users.get(tlUpdateUserStatus.getUserId());
                users2.add(user);
                updates.add(new UpdateUserStatus(user, getExpires(tlUpdateUserStatus.getStatus())));
            } else if(tlAbsUpdate instanceof TLUpdateUserTyping) {
                TLUpdateUserTyping tlUpdateUserTyping = (TLUpdateUserTyping)tlAbsUpdate;
                User user = users.get(tlUpdateUserTyping.getUserId());
                users2.add(user);
                updates.add(new UpdateUserTyping(user));
            } else {

            }
        }

        //return updates;
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
