import java.util.ArrayList;
import java.util.Date;

public class ChatManager {
    public ArrayList<Message> messages;

    public ChatManager() {
        messages = new ArrayList<Message>();
    }

    public ArrayList<Message> ListMessages() {
        return messages;
    }

    public ArrayList<Message> ListMessages(Date from, Date to) {
        ArrayList<Message> messagesInRange = new ArrayList<Message>();

        for(Message m: messages) {
            Date mDate = m.getDate();

            if(mDate.compareTo(from) >= 0 && mDate.compareTo(to) <= 0)
                messagesInRange.add(m);
        }

        return messagesInRange;
    }
}
