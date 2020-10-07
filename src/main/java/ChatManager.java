import org.graalvm.compiler.lir.sparc.SPARCMove;

import java.io.File;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
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

    public ArrayList<Message> ListMessages(LocalDateTime from, LocalDateTime to) {
        return filterAndGetMessageStream(from, to).collect(Collectors.toCollection(ArrayList::new));
    }

    public void ClearChat() {
        messages.clear();
    }

    public void ClearChat(LocalDateTime from, LocalDateTime to) {
        for(int i = 0; i < messages.size(); i++) {
            LocalDateTime messageDate = messages.get(i).getDate();

            if(messageDate.compareTo(from) >= 0 && messageDate.compareTo(to) <= 0) {
                messages.remove(i);
                i--;
            }
        }
    }

    String getMessages(LocalDateTime from, LocalDateTime to, FileFormat fileFormat){
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

    private Stream<Message> filterAndGetMessageStream(LocalDateTime from, LocalDateTime to) {
        //Variables in lambda function must be final
        final LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        final LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        return messages.stream()
                       .filter(m -> (m.getDate().compareTo(finalFrom) >= 0 && m.getDate().compareTo(finalTo) <= 0));
    }
}
