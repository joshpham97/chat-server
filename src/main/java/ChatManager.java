import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatManager {
    private ArrayList<Message> messages;

    public ChatManager() {
        messages = new ArrayList<Message>();
    }
  
    public void postMessage(String username, String message)
    {
        if(username.isEmpty()) //If username is empty, they post as Anonymous.
        {
            messages.add(new Message(message));
        }
        else
        {
            messages.add(new Message(username, message));
        }
        //System.out.println(messages.toString());
    }

    public ArrayList<Message> ListMessages() {
        return messages;
    }

    public ArrayList<Message> ListMessages(LocalDateTime from, LocalDateTime to) {
        return filterAndGetMessageStream(from, to).collect(Collectors.toCollection(ArrayList::new));
    }

    public void ClearChat(LocalDateTime from, LocalDateTime to) {
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

    private Stream<Message> filterAndGetMessageStream(LocalDateTime from, LocalDateTime to) {
        //Variables in lambda function must be final
        final LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        final LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        return messages.stream()
                       .filter(m -> (m.getDate().compareTo(finalFrom) >= 0 && m.getDate().compareTo(finalTo) <= 0));
    }
}
