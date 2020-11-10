import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.chat.Post;
import server.chat.daoimpl.PostDAO;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostManager {
    // Read config file
    private static final String CONFIG_FILE = "config.json";
    private static JSONObject jo;
    static {
        try {
            InputStream inputStream = PostManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE); // Get resource
            Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
            jo = (JSONObject) obj;
            jo = (JSONObject) jo.get("postConfig");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int NUMBER_OF_POSTS = Integer.parseInt((String) jo.get("numberOfPosts"));
    private ArrayList<Post> messages;

    public PostManager() {
        messages = new ArrayList<Post>();
    }

    public static ArrayList<Post> getRecentPosts() {
        ArrayList<Post> posts = PostDAO.getRecentPosts();
        return posts;
    }

    public static ArrayList<Post> searchPostsWithPagination(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags, int pageNumb) {
        int limit = NUMBER_OF_POSTS;
        int offset = NUMBER_OF_POSTS * (pageNumb - 1);

        ArrayList<Post> posts = PostDAO.searchNPostsWithOffset(username, from, to, hashtags, limit, offset);
        return posts;
    }

    public static int getNumberOfPages(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        int postCount = PostDAO.countPosts(username, from, to, hashtags);
        int pages = (int) Math.ceil(((double) postCount)/NUMBER_OF_POSTS);
        return pages;
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
