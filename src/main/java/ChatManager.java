import server.chat.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatManager {
    private ArrayList<Message> messages;
    private Message msg;
    public ChatManager() {
        messages = new ArrayList<Message>();
    }

    public Message postMessage(String username, String message)
    {
        Message msg;

        if(username.isEmpty()) //If username is empty, they post as Anonymous.
        {
            msg = new Message(message);
        }
        else
        {
            msg = new Message(username, message);
        }

        messages.add(msg);
        return msg;
    }

    //Test code, please dont remove
    public Message postMessage(String username, String message, LocalDateTime date)
    {
        Message msg = new Message(username, message, date);
        messages.add(msg);
        return msg;
    }

    public ArrayList<Message> listMessages(LocalDateTime from, LocalDateTime to) {
        final LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        final LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        return messages.stream()
                .filter(m -> (m.getDate().compareTo(finalFrom) >= 0 && m.getDate().compareTo(finalTo) <= 0))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void clearChat(LocalDateTime from, LocalDateTime to) {
        LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        for(int i = 0; i < messages.size(); i++) {
            LocalDateTime messageDate = messages.get(i).getDate();

            if(messageDate.compareTo(finalFrom) >= 0 && messageDate.compareTo(finalTo) <= 0) {
                messages.remove(i);
                i--;
            }
        }
    }
}
