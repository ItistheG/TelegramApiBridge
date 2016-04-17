package org.javagram;

import org.javagram.core.MemoryApiState;
import org.javagram.core.StaticContainer;
import org.javagram.handlers.IncomingMessageHandler;
import org.javagram.response.*;
import org.javagram.response.object.*;
import org.telegram.api.*;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.auth.TLAuthorization;
import org.telegram.api.auth.TLCheckedPhone;
import org.telegram.api.auth.TLSentCode;
import org.telegram.api.contacts.TLContacts;
import org.telegram.api.contacts.TLLink;
import org.telegram.api.engine.ApiCallback;
import org.telegram.api.engine.AppInfo;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.help.TLInviteText;
import org.telegram.api.messages.*;
import org.telegram.api.requests.*;
import org.telegram.api.updates.TLState;
import org.telegram.tl.TLBoolTrue;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLStringVector;
import org.telegram.tl.TLVector;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Danya on 04.09.2015.
 */
public class TelegramApiBridge implements Closeable
{
    private String langCode = "ru";

    private MemoryApiState apiState;
    private AppInfo appInfo;
    private String appHash;

    private TelegramApi api;
    private Random random;

    private String phoneNumber;
    private String phoneCodeHash;

    private IncomingMessageHandler incomingMessageHandler;

    /**
     *
     * @param hostAddr
     * @param appId
     * @param appHash
     * @throws IOException
     */
    public TelegramApiBridge(String hostAddr, Integer appId, String appHash) throws IOException
    {
        apiState = new MemoryApiState(hostAddr);
        appInfo = new AppInfo(appId, "console", "???", "???", langCode);
        this.appHash = appHash;
        random = new Random();
        createApi();

        TLConfig config = api.doRpcCallNonAuth(new TLRequestHelpGetConfig());
        apiState.updateSettings(config);
    }

    //=====================================================================

    /**
     *
     * @param phoneNumber
     * @return AuthCheckedPhone
     * @throws IOException
     */
    public AuthCheckedPhone authCheckPhone(String phoneNumber) throws IOException
    {
        TLRequestAuthCheckPhone checkPhone = new TLRequestAuthCheckPhone(phoneNumber);
        TLCheckedPhone checkedPhone = api.doRpcCallNonAuth(checkPhone);
        return new AuthCheckedPhone(
            checkedPhone.getPhoneRegistered(), checkedPhone.getPhoneInvited()
        );
    }

    /**
     *
     * @param phoneNumber
     * @return AuthSentCode
     * @throws IOException
     */
    public AuthSentCode authSendCode(String phoneNumber) throws IOException
    {
        TLRequestAuthSendCode sendCode = new TLRequestAuthSendCode(phoneNumber, 0, appInfo.getApiId(), appHash, langCode);
        TLSentCode sentCode = api.doRpcCallNonAuth(sendCode);
        this.phoneNumber = phoneNumber;
        phoneCodeHash = sentCode.getPhoneCodeHash();
        return new AuthSentCode(sentCode.getPhoneRegistered(), phoneCodeHash);
    }

    /**
     *
     * @param smsCode
     * @return AuthAuthorization
     * @throws IOException
     */
    public AuthAuthorization authSignIn(String smsCode) throws IOException
    {
        if(phoneNumber == null) {
            throw new IOException("PHONE_NUMBER_INVALID");
        }
        if(phoneCodeHash == null) {
            throw new IOException("PHONE_CODE_EMPTY");
        }
        return authSignIn(smsCode, phoneNumber, phoneCodeHash);
    }

    /**
     *
     * @param smsCode
     * @param firstName
     * @param lastName
     * @return
     * @throws IOException
     */
    public AuthAuthorization authSignUp(String smsCode, String firstName, String lastName) throws IOException
    {
        if(phoneNumber == null) {
            throw new IOException("PHONE_NUMBER_INVALID");
        }
        if(phoneCodeHash == null) {
            throw new IOException("PHONE_CODE_EMPTY");
        }
        return authSignUp(smsCode, phoneNumber, phoneCodeHash, firstName, lastName);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public boolean authLogOut() throws IOException
    {
        TLRequestAuthLogOut logOut = new TLRequestAuthLogOut();
        return api.doRpcCall(logOut) instanceof TLBoolTrue;
    }

    /**
     *
     * @param phoneNumbers
     * @param message
     * @return
     * @throws IOException
     */
    public boolean authSendInvites(ArrayList<String> phoneNumbers, String message) throws IOException
    {
        TLStringVector tlStringVector = new TLStringVector();
        tlStringVector.addAll(phoneNumbers);
        TLRequestAuthSendInvites sendInvites = new TLRequestAuthSendInvites(tlStringVector, message);
        return api.doRpcCall(sendInvites) instanceof TLBoolTrue;
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @return
     * @throws IOException
     */
    public User accountUpdateProfile(String firstName, String lastName) throws IOException
    {
        TLRequestAccountUpdateProfile request = new TLRequestAccountUpdateProfile(firstName, lastName);
        TLAbsUser absUser = api.doRpcCall(request);
        return new UserSelf((TLUserSelf) absUser);
    }

    /**
     *
     * @param offline
     * @return
     * @throws IOException
     */
    public boolean accountUpdateStatus(boolean offline) throws IOException
    {
        TLRequestAccountUpdateStatus request = new TLRequestAccountUpdateStatus(offline);
        return api.doRpcCall(request) instanceof TLBoolTrue;
    }

    /**
     *
     * @param userId
     * @return
     * @throws IOException
     */
    public boolean contactsDeleteContact(int userId) throws IOException
    {
        TLInputUserContact user = new TLInputUserContact(userId);
        TLRequestContactsDeleteContact request = new TLRequestContactsDeleteContact(user);
        return api.doRpcCall(request) instanceof TLLink;
    }

    /**
     *
     * @return ArrayList<UserContact>
     * @throws IOException
     */
    public ArrayList<UserContact> contactsGetContacts() throws IOException
    {
        return contactsGetContacts("");
    }

    /**
     *
     * @param hash
     * @return ArrayList<UserContact>
     * @throws IOException
     */
    protected ArrayList<UserContact> contactsGetContacts(String hash) throws IOException
    {
        ArrayList<UserContact> userContacts = new ArrayList<>();
        TLRequestContactsGetContacts getContacts = new TLRequestContactsGetContacts("");
        TLContacts contacts = (TLContacts) api.doRpcCall(getContacts);
        TLVector<TLAbsUser> users = contacts.getUsers();
        for(TLAbsUser absUser : users)
        {
            if(absUser instanceof TLUserContact) {
                TLUserContact userContact = (TLUserContact) absUser;
                userContacts.add(new UserContact(userContact));
            }
           /* else if(absUser instanceof TLUserSelf) {
                TLUserSelf userSelf = (TLUserSelf) absUser;
                userContacts.add(new UserContact(userSelf));
            }*/
        }
        return userContacts;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public ArrayList<ContactStatus> contactsGetStatuses() throws IOException
    {
        ArrayList<ContactStatus> statuses = new ArrayList<>();
        TLRequestContactsGetStatuses request = new TLRequestContactsGetStatuses();
        TLVector<TLContactStatus> tlStatuses = api.doRpcCall(request);
        for(TLContactStatus status : tlStatuses) {
            statuses.add(new ContactStatus(status));
        }
        return statuses;
    }

    /**
     *
     * @param message
     * @param randomId
     * @throws IOException
     */
    public MessagesSentMessage messagesSendMessage(int userId, String message, long randomId) throws IOException
    {
        TLInputPeerContact peerContact = new TLInputPeerContact(userId);
        TLRequestMessagesSendMessage request = new TLRequestMessagesSendMessage(peerContact, message, randomId);
        TLSentMessage sentMessage = (TLSentMessage) api.doRpcCall(request);
        return new MessagesSentMessage(sentMessage);
    }

    /**
     *
     * @param userId
     * @param isTyping
     * @return
     * @throws IOException
     */
    public boolean messagesSetTyping(int userId, boolean isTyping) throws IOException
    {
        TLInputPeerContact peerContact = new TLInputPeerContact(userId);//TODO:Смущает. А не контакты?
        TLRequestMessagesSetTyping request = new TLRequestMessagesSetTyping(peerContact, isTyping);
        return api.doRpcCall(request) instanceof TLBoolTrue;
    }

    /**
     *
     * @param messageIds
     * @return
     * @throws IOException
     */
    public ArrayList<Message> messagesGetMessages(ArrayList<Integer> messageIds) throws IOException
    {
        TLIntVector intVector = new TLIntVector();
        intVector.addAll(messageIds);
        TLRequestMessagesGetMessages request = new TLRequestMessagesGetMessages(intVector);
        TLVector<TLAbsMessage> tlMessages = api.doRpcCall(request).getMessages();
        ArrayList<Message> messages = new ArrayList<>();
        for(TLAbsMessage message : tlMessages) {
            messages.add(new Message(message));
        }
        return messages;
    }

    public ArrayList<Message> messagesGetMessages(Integer ... messageIds) throws IOException
    {
        return messagesGetMessages(new ArrayList<>(Arrays.asList(messageIds)));
    }

    public MessagesDialogs messagesGetDialogs(int offset, int maxId, int limit) throws IOException
    {
        TLRequestMessagesGetDialogs request = new TLRequestMessagesGetDialogs(offset, maxId, limit);
        TLAbsDialogs tlAbsDialogs = api.doRpcCall(request);
        /*if(tlAbsDialogs instanceof TLDialogsSlice)
            return new MessagesDialogsSlice((TLDialogsSlice)tlAbsDialogs);
        else
            */
        return new MessagesDialogs(tlAbsDialogs);

    }

    /*private static class MessagesDialogsSlice extends MessagesDialogs {

        private int count;

        public MessagesDialogsSlice(TLDialogsSlice tlAbsDialogs) {
            super(tlAbsDialogs);
            count = tlAbsDialogs.getCount();
        }

        public int getCount() {
            return count;
        }
    }*/

    /*public MessagesDialogs messagesGetDialogs(int offset, int limit) throws IOException {

        MessagesDialogs messagesDialogs = messagesGetDialogs(offset, 0, limit);

        do {
            int count = messagesDialogs.getDialogs().size() + messagesDialogs.getChatsCount();
            offset += count;
            limit -= count;
            if(limit <= 0)
                break;
            MessagesDialogs messagesDialogs2 = messagesGetDialogs(offset, 0, limit);
            count = messagesDialogs2.getDialogs().size() + messagesDialogs2.getChatsCount();
            if(count == 0)
                break;
            messagesDialogs = new MessagesDialogs(messagesDialogs, messagesDialogs2);
        } while(true);

        return messagesDialogs;
    }*/

    @Override
    public void close() throws IOException {
        try {
            api.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public MessagesMessages messagesGetHistory(User peerUser, int offset, int maxId, int limit) throws IOException {
        TLAbsInputPeer tlAbsInputPeer = User.createTLInputPeer(peerUser);
        if(tlAbsInputPeer == null)
            return new MessagesMessages();
        TLRequestMessagesGetHistory tlRequestMessagesGetHistory = new TLRequestMessagesGetHistory(tlAbsInputPeer,
                offset, maxId, limit);
        TLAbsMessages tlAbsMessages = api.doRpcCall(tlRequestMessagesGetHistory);
        return new MessagesMessages(tlAbsMessages);
    }

    public MessagesMessages messagesGetHistory(User peerUser) throws IOException {

        int maxId = 0;

        int count = 0;

        MessagesMessages messagesMessages = messagesGetHistory(peerUser, 0, maxId, count);
        while(messagesMessages.isSlice()) {
            count = messagesMessages.getCount() - messagesMessages.getMessages().size();
            MessagesMessages messagesMessages2 = messagesGetHistory(peerUser, messagesMessages.getMessages().size(), maxId, count);
            messagesMessages = new MessagesMessages(messagesMessages, messagesMessages2);
        }

        return messagesMessages;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String helpGetInviteText() throws IOException
    {
        TLRequestHelpGetInviteText request = new TLRequestHelpGetInviteText(langCode);
        TLInviteText inviteText = api.doRpcCall(request);
        return inviteText.getMessage();
    }

    //=====================================================================

    public void setIncomingMessageHandler(IncomingMessageHandler handler)
    {
        this.incomingMessageHandler = handler;
    }

    //=====================================================================

    public AuthAuthorization authSignIn(String smsCode, String phoneNumber, String phoneCodeHash) throws IOException
    {
        TLRequestAuthSignIn signIn = new TLRequestAuthSignIn(phoneNumber, phoneCodeHash, smsCode);
        TLAuthorization authorization = api.doRpcCallNonAuth(signIn);

        apiState.setAuthenticated(apiState.getPrimaryDc(), true);
        TLState state = api.doRpcCall(new TLRequestUpdatesGetState());

        return new AuthAuthorization(authorization);
    }

    public AuthAuthorization authSignUp(String smsCode, String phoneNumber, String phoneCodeHash, String firstName,
        String lastName) throws IOException
    {
        TLRequestAuthSignUp signUp = new TLRequestAuthSignUp(phoneNumber, phoneCodeHash, smsCode, firstName, lastName);
        TLAuthorization authorization = api.doRpcCallNonAuth(signUp);

        apiState.setAuthenticated(apiState.getPrimaryDc(), true);
        TLState state = api.doRpcCall(new TLRequestUpdatesGetState());

        return new AuthAuthorization(authorization);
    }

    //=====================================================================

    private void createApi()
    {
        api = new TelegramApi(apiState, appInfo, new ApiCallback()
        {
            @Override
            public void onAuthCancelled(TelegramApi api) {

            }

            @Override
            public void onUpdatesInvalidated(TelegramApi api) {

            }

            @Override
            public void onUpdate(TLAbsUpdates updates) {
                if (updates instanceof TLUpdateShortMessage)
                {
                    TLUpdateShortMessage shortMessage = (TLUpdateShortMessage) updates;
                    if(incomingMessageHandler != null) {
                        incomingMessageHandler.handle(shortMessage.getFromId(), shortMessage.getMessage());
                    }
                }
                else if (updates instanceof TLUpdateShortChatMessage)
                {
                    System.out.println("Invoke onIncomingMessageChat method");
                    //onIncomingMessageChat(((TLUpdateShortChatMessage) updates).getChatId(), ((TLUpdateShortChatMessage) updates).getMessage());
                }
            }
        });
        StaticContainer.setTelegramApi(api);
    }
}