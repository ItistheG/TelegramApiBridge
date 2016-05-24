
import org.javagram.TelegramApiBridge;
import org.javagram.response.*;
import org.javagram.response.object.*;
import org.javagram.response.MessagesMessages;
import org.javagram.response.object.inputs.InputUserOrPeerSelf;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Danya on 31.07.2015.
 */

public class Main
{
    private static String photosPath = "res/photos";

    public static void main(String args[]) throws Exception
    {
        String testAddr = "149.154.167.40:443";
        String prodAddr = "149.154.167.50:443";
        Integer appId = 33422;
        String appHash = "ff6c12b105d7a608616ee69fe9381e30";

        //=====================================================================


        try(TelegramApiBridge apiBridge = new TelegramApiBridge(prodAddr, appId, appHash);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in))
        ) {

            String phoneNumber =
               //     "79173314167";
             "79876497774";

            //Sending validation code
            try {
                apiBridge.authSendCode(phoneNumber);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            System.err.println("Please, enter SMS code: ");
            String smsCode = bReader.readLine().trim();

            //Checking phone number
            AuthCheckedPhone checkedPhone = apiBridge.authCheckPhone(phoneNumber);
            if (checkedPhone.isRegistered()) {
                //Authorization
                AuthAuthorization auth = apiBridge.authSignIn(smsCode);
                User user = auth.getUser();
                System.err.println("You've signed in; name: " + user.toString());

                savePhoto(user.getPhoto(true), photosPath + "/self-small.png");
                savePhoto(user.getPhoto(false), photosPath + "/self-big.png");
            } else {
                //Registration
                System.err.println("Please, type the first name:");
                String firstName = bReader.readLine().trim();
                System.err.println("Please, type the last name:");
                String lastName = bReader.readLine().trim();
                AuthAuthorization auth = apiBridge.authSignUp(smsCode, firstName, lastName);
                System.err.println("You've signed up; name: " + auth.getUser().toString());
            }

            apiBridge.setIncomingMessageHandler(new IncMessageHandler());

            UpdatesState updatesState = apiBridge.updatesGetState();


            Set<User> users = new HashSet<>(apiBridge.contactsGetContacts());
            List<MessagesDialog> messagesDialogList = apiBridge.messagesGetDialogs(0, Integer.MAX_VALUE);

            UpdatesAbsDifference updatesDifference = apiBridge.updatesGetDifference(updatesState);

            for(MessagesDialog messagesDialog : messagesDialogList) {
                User user =  messagesDialog.getPeerUser();
                System.out.println(user.getClass().getSimpleName() + " : " + user);

                users.add(user);

               /* if(messagesDialog.getUnreadCount() > 0) {
                    apiBridge.messagesReadHistory(user, messagesDialog.getTopMessage().getId());
                }

                List<MessagesMessage> messagesMessages = apiBridge.messagesGetHistory(user, 0, 0, Integer.MAX_VALUE).getMessages();
                for(MessagesMessage message : messagesMessages)
                    System.out.println(message.getMessage());*/

                /*if(messagesMessages.size() > 0) {
                    MessagesMessage messagesMessage = messagesMessages.get(0);
                    apiBridge.messagesReceivedMessages(messagesMessage.getId());
                }*/
            }


            ArrayList<InputUser> inputUsers = new ArrayList<>();
            users.forEach(user -> inputUsers.add(user));
            inputUsers.add(new InputUserOrPeerSelf());
            for(User user : apiBridge.usersGetUsers(inputUsers)) {
                System.out.println(user);
                UserFull userFull = apiBridge.usersGetFullUser(user);
                System.out.println("Real name : " + userFull.getRealFirstName() + " " + userFull.getRealLastName());
                System.out.println("Contact name : " + userFull.getUser().getId() + " vs " + user.getId());
                System.out.println("MyLink : " + userFull.getMyLink());
                System.out.println("ForegnLink : " + userFull.getForeignLink());
                System.out.println("Blcoked : " + userFull.isBlocked());
            }


            MessagesMessages messagesMessages = apiBridge.messagesSearch("слово", 0, 0, 200);

            for (MessagesMessage messagesMessage : messagesMessages.getMessages()) {
                System.out.println(messagesMessage.getMessage());
            }



            apiBridge.close();
            System.exit(0);

            // dialogs.addAll(apiBridge.messagesGetDialogs(2, Integer.MAX_VALUE, 2));

            /*ArrayList<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add("79099494774");
            System.err.println("Sent invites: " + apiBridge.authSendInvites(phoneNumbers, "Please, add me!"));*/

            System.err.println("Invite text: " + apiBridge.helpGetInviteText());

            System.err.println("Statuses: ");
            ArrayList<ContactStatus> statuses = apiBridge.contactsGetStatuses();
            for (ContactStatus status : statuses) {
                System.err.println("\t" + status.getUserId() + " - " +
                        (status.getExpires().getTime() > System.currentTimeMillis()));
            }

            ArrayList<UserContact> contacts = apiBridge.contactsGetContacts();
            for (UserContact contact : contacts) {
                System.err.println(contact + " - " + contact.getPhone() + " - " + contact.isOnline() + " - " + contact.getId());
//            savePhoto(contact.getPhoto(true), photosPath + "/" + contact.getPhone() + "-small.png");
//            savePhoto(contact.getPhoto(false), photosPath + "/" + contact.getPhone() + "-big.png");
            }

            //System.err.println("Delete contact: " + apiBridge.contactsDeleteContact(173382350));

            int userId = 116023976;

            MessagesSentMessage sentMessage = apiBridge.messagesSendMessage(userId, "Tesxt message", (long) (1000000000L * Math.random()));
            System.err.println("Sent message: id=" + sentMessage.getId() + ", date=" +
                    sentMessage.getDate() + ", pts=" + sentMessage.getPts() + ", seq=" + sentMessage.getSeq());

            apiBridge.setIncomingMessageHandler(new IncMessageHandler());

            System.err.println("Typing: " + apiBridge.messagesSetTyping(userId, true));

//        System.err.println("Update status to offline: " + apiBridge.accountUpdateStatus(true));
//        System.err.println("Update profile: " + apiBridge.accountUpdateProfile("Даниил", "Пилипенко").toString());
//        System.out.println("Logout: " + apiBridge.authLogOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void savePhoto(byte bytes[], String name) throws Exception
    {
        if(bytes == null) {
            return;
        }
        FileOutputStream fos = new FileOutputStream(name);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}