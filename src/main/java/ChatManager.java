import java.util.ArrayList;
import java.util.Date;

public class ChatManager {
    private ArrayList<Message> messages;

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

    public void ClearChat() {
        messages = new ArrayList<Message>();
    }

    public void ClearChat(Date from, Date to) {
        ArrayList<Message> messagesInRange = new ArrayList<Message>();

        for(Message m: messages) {
            Date messageDate = m.getDate();

            if(messageDate.compareTo(from) < 0 || messageDate.compareTo(to) > 0)
                messagesInRange.add(m);
        }

        messages = messagesInRange;
    }
}
