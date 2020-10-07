import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatManager {
    public enum FileFormat{
        XML,
        TEXT
    }

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

    /*String getMessages(LocalDateTime from, LocalDateTime to, FileFormat fileFormat){
        Stream<Message> stream = filterAndGetMessageStream(from, to);
        StringBuilder fileContent = new StringBuilder();

        if(fileFormat == FileFormat.XML){
            fileContent.append("<Messages>\n");
            stream.forEach((Message m) -> {
                fileContent.append(m.toXML());
            });
            fileContent.append("</Messages>");
        }else{
            stream.forEach((Message m) -> {
                fileContent.append(m.toString());
            });
        }

        return fileContent.toString();
    }

    private Stream<Message> filterAndGetMessageStream(LocalDateTime from, LocalDateTime to){
        if (from == null)
            from = LocalDateTime.MIN;

        if (to == null)
            to = LocalDateTime.MAX;

        return messages.stream()
                       .filter(m -> (m.getDate().compareTo(finalFrom) >= 0 && m.getDate().compareTo(finalTo) <= 0));
    }*/
}
