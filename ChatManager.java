import java.util.ArrayList;
import java.util.Date;

public class ChatManager {

    public ArrayList<Message> messages;
    public ChatManager() {
        messages = new ArrayList<Message>();
    }
    public void postMessage(String username, String message, Date date)
    {
        if(username == "")
        {
            messages.add(new Message(message, date));
        }
        else
        {
            messages.add(new Message(username, message, date));
        }
        //System.out.println(messages.toString());
    }
}
