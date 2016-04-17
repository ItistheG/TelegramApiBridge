
import org.javagram.TelegramApiBridge;
import org.javagram.response.*;
import org.javagram.response.object.ContactStatus;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

            String phoneNumber = "79876497774";

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

            MessagesDialogs dialogs = apiBridge.messagesGetDialogs(0, 0, 0);//0, Integer.MAX_VALUE, 2);

            for(User user : dialogs.getUsers()) {
                System.out.println(user.getClass().getSimpleName() + " : " + user);
                MessagesMessages messagesMessages = apiBridge.messagesGetHistory(user);
                for(Message message : messagesMessages.getMessages())
                    System.out.println(message.getMessage());
            }

            apiBridge.close();

            // dialogs.addAll(apiBridge.messagesGetDialogs(2, Integer.MAX_VALUE, 2));

            /*ArrayList<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add("79099494774");
            System.err.println("Sent invites: " + apiBridge.authSendInvites(phoneNumbers, "Please, add me!"));*/

            System.err.println("Invite text: " + apiBridge.helpGetInviteText());

            System.err.println("Statuses: ");
            ArrayList<ContactStatus> statuses = apiBridge.contactsGetStatuses();
            for (ContactStatus status : statuses) {
                System.err.println("\t" + status.getUserId() + " - " +
                        (status.getExpires() > System.currentTimeMillis() / 1000));
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