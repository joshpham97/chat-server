import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChatManagerTest {
    ChatManager cm = new ChatManager();
    LocalDateTime today = LocalDateTime.now();
    LocalDateTime yesterday = today.minusDays(1);
    LocalDateTime tomorrow = today.plusDays(1);

    @Test
    void listMessages() {
//        assertEquals(0, cm.ListMessages().size());
//
//        // To be replaced with PostMessage
//        cm.messages.add(new Message("username 1", "content 1", yesterday));
//        cm.messages.add(new Message("username 2", "content 2", yesterday));
//        cm.messages.add(new Message("username 3", "content 3", yesterday));
//        cm.messages.add(new Message("username 4", "content 4", today));
//        cm.messages.add(new Message("username 5", "content 5", today));
//        cm.messages.add(new Message("username 6", "content 6", today));
//        cm.messages.add(new Message("username 7", "content 7", tomorrow));
//        cm.messages.add(new Message("username 8", "content 8", tomorrow));
//        cm.messages.add(new Message( "username 9", "content 9", tomorrow));
//
//        assertEquals(9, cm.ListMessages().size());
//        assertEquals(9, cm.ListMessages(yesterday, tomorrow).size());
//
//        assertEquals(6, cm.ListMessages(yesterday, today).size());
//        assertEquals(6, cm.ListMessages(today, tomorrow).size());
//
//        assertEquals(3, cm.ListMessages(yesterday, yesterday).size());
//        assertEquals(3, cm.ListMessages(today, today).size());
//        assertEquals(3, cm.ListMessages(tomorrow, tomorrow).size());
//
//        assertEquals(0, cm.ListMessages(tomorrow, yesterday).size());
    }

    @Test
    void clearChat() {
//        assertEquals(0, cm.messages.size());
//
//        // Clear all
//        cm.messages.add(new Message("username 1", "content 1", today));
//        cm.messages.add(new Message("username 2", "content 2", today));
//        cm.messages.add(new Message("username 3", "content 3", today));
//        assertEquals(3, cm.messages.size());
//        cm.ClearChat();
//        assertEquals(0, cm.messages.size());
//
//        // Clear within date range
//        cm.messages.add(new Message("username 4", "content 4", yesterday));
//        cm.messages.add(new Message("username 5", "content 5", yesterday));
//        cm.messages.add(new Message("username 6", "content 6", yesterday));
//        cm.messages.add(new Message("username 7", "content 7", today));
//        cm.messages.add(new Message("username 8", "content 8", today));
//        cm.messages.add(new Message("username 9", "content 9", today));
//        cm.messages.add(new Message("username 10", "content 10", tomorrow));
//        cm.messages.add(new Message("username 11","content 11", tomorrow));
//        cm.messages.add(new Message("username 12", "content 12", tomorrow));
//        assertEquals(9, cm.messages.size());
//        cm.ClearChat(today, today);
//        assertEquals(6, cm.messages.size());
//
//        cm.ClearChat(yesterday, today);
//        assertEquals(3, cm.messages.size());
//
//        cm.ClearChat(yesterday, today);
//        assertEquals(3, cm.messages.size());
//
//        cm.ClearChat(today, tomorrow);
//        assertEquals(0, cm.messages.size());
    }
}