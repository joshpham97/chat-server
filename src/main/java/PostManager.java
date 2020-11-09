import server.chat.Post;
import server.chat.daoimpl.PostDAO;
import server.chat.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PostManager {
    private ArrayList<Post> messages;
    public PostManager() {
        messages = new ArrayList<Post>();
    }

    public Post postMessageDatabase(String username,String title, String message)
    {
        PostDAO postDao = new PostDAO();
        Post post = postDao.createPost(username, title, message);

        int postID = post.getPostID();
        String postMessage = post.getMessage();
        System.out.println("Post id: " + postID + " Message: " + postMessage);

        Set<String> hashtags = getHashtags(message);
        Iterator<String> it = hashtags.iterator();

        if(!hashtags.isEmpty()) {
            System.out.println("Message has hashtags.");
            while(it.hasNext()) {
                String hashtagWord = it.next();
                System.out.println(hashtagWord);
                postDao.insertHashtag(postID, hashtagWord);
            }
        }
        else
        {
            System.out.println("No hashtags.");
        }
        return post;
    }
    public static Set<String> getHashtags(String message) {
        String[] words = message.split("\\s+");
        Set<String> hashtags = new HashSet<String>();
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags.add(word);
            }
        }
        return hashtags;
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
                .filter(m -> (m.getDatePosted().compareTo(finalFrom) >= 0 && m.getDatePosted().compareTo(finalTo) < 0))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void clearChat(LocalDateTime from, LocalDateTime to) {
        LocalDateTime finalFrom = (from == null) ? LocalDateTime.MIN : from;
        LocalDateTime finalTo = (to == null) ? LocalDateTime.MAX : to;

        for(int i = 0; i < messages.size(); i++) {
            LocalDateTime messageDate = messages.get(i).getDatePosted();

            if(messageDate.compareTo(finalFrom) >= 0 && messageDate.compareTo(finalTo) <= 0) {
                messages.remove(i);
                i--;
            }
        }
    }

}
