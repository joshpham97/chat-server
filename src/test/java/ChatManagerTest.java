import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ChatManagerTest {
    ChatManager cm = new ChatManager();
    Date yesterday = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
    Date today = new Date();
    Date tomorrow = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

    @Test
    void listMessages() {
        assertEquals(0, cm.ListMessages().size());

        // To be replaced with PostMessage
//        cm.messages.add(new Message("content 1", yesterday));
//        cm.messages.add(new Message("content 2", yesterday));
//        cm.messages.add(new Message( "content 3", yesterday));
//        cm.messages.add(new Message("content 4", today));
//        cm.messages.add(new Message("content 5", today));
//        cm.messages.add(new Message( "content 6", today));
//        cm.messages.add(new Message("content 7", tomorrow));
//        cm.messages.add(new Message("content 8", tomorrow));
//        cm.messages.add(new Message( "content 9", tomorrow));
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

//        // Clear all
//        cm.messages.add(new Message("content 1", today));
//        cm.messages.add(new Message("content 2", today));
//        cm.messages.add(new Message("content 3", today));
//        assertEquals(3, cm.messages.size());
//        cm.ClearChat();
//        assertEquals(0, cm.messages.size());
//
//        // Clear within date range
//        cm.messages.add(new Message("content 4", yesterday));
//        cm.messages.add(new Message("content 5", yesterday));
//        cm.messages.add(new Message("content 6", yesterday));
//        cm.messages.add(new Message("content 7", today));
//        cm.messages.add(new Message("content 8", today));
//        cm.messages.add(new Message("content 9", today));
//        cm.messages.add(new Message("content 10", tomorrow));
//        cm.messages.add(new Message("content 11", tomorrow));
//        cm.messages.add(new Message("content 12", tomorrow));
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