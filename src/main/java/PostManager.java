import server.chat.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PostManager {
    //private static PostDaoImpl postDao = new PostDaoImpl();
    private ArrayList<Post> messages;

    public PostManager() {
        messages = new ArrayList<Post>();
    }

    public static ArrayList<Post> getRecentPosts() {
        // TEMPORARY HARDCODING: waiting for PostDao and Post
        ArrayList<Post> tempPosts = new ArrayList<>();
        tempPosts.add(new Post("username1", "message1"));
        tempPosts.add(new Post("username2", "message2"));
        tempPosts.add(new Post("username3", "message3"));


        //ArrayList<Post> posts = postDao.getRecentPosts();
        ArrayList<Post> posts = tempPosts;

        return posts;
    }

    public Post postMessage(String username, String message)
    {
        Post msg;

        if(username.isEmpty()) //If username is empty, they post as Anonymous.
        {
            msg = new Post(message);
        }
        else
        {
            msg = new Post(username, message);
        }

        messages.add(msg);
        return msg;
    }

    public ArrayList<Post> listMessages(LocalDateTime from, LocalDateTime to) {
        final LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        final LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        return messages.stream()
                .filter(m -> (m.getDate().compareTo(finalFrom) >= 0 && m.getDate().compareTo(finalTo) < 0))
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
