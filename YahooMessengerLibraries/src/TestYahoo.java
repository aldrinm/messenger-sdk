import com.yahoo.messenger.data.json.Contact;
import com.yahoo.messenger.exception.MessengerException;
import com.yahoo.messenger.manager.YahooMessengerLoginManager;
import com.yahoo.messenger.manager.YahooMessengerMessageManager;
import com.yahoo.messenger.util.YahooMessengerConstants;

import java.io.IOException;

public class TestYahoo {

    //Note: Update the consumer key and secret in YahooMessengerConstants
    private static String username = ""; //example@yahoo.com
    private static String password = "";
    private static String targetusername = ""; //for example if the user is thatperson@yahoo.com , then targetusername = "thatperson";
    private static String message = "Hello there. from here";


    public static void main(String[] args) throws IOException, MessengerException {
        loginAndSend(username, password, targetusername, message);
    }


    private static void loginAndSend(String username, String password, String targetusername, String message) throws IOException, MessengerException {
        YahooMessengerLoginManager loginManager = YahooMessengerLoginManager.getInstance();
        loginManager.performLoginOAuth(username, password,
                YahooMessengerConstants.authenticationConsumerKey,
                YahooMessengerConstants.authenticationConsumerSecret);


        YahooMessengerMessageManager messageManager = YahooMessengerMessageManager.getInstance();


        Contact contact = new Contact();
        contact.setId(targetusername);
        messageManager.sendMessage(contact, message);

        loginManager.performLogout();
    }
}
