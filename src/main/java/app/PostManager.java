package app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.dabatase.daoimpl.AttachmentDAO;
import server.dabatase.daoimpl.HashtagDAO;
import server.dabatase.daoimpl.PostDAO;
import server.dabatase.model.Hashtag;
import server.dabatase.model.Post;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public static Integer createPost(String username, String title, String message)
    {
        Integer postId = PostDAO.insert(username, title, message);

        if(postId != null){
            HashtagManager.createHashTag(postId, message);
            return postId;
        }

        return null;
    }

    public static ArrayList<Post> getRecentPosts() {
        /**
        // TEMPORARY HARDCODING: waiting for PostDao and Post
        ArrayList<Post> tempPosts = new ArrayList<>();
        tempPosts.add(new Post(1, "username1", "title1", "message1", 1));
        tempPosts.add(new Post(2, "username2", "title2", "message2", null));
        tempPosts.add(new Post(3, "username3", "title3", "message3", 2));
        ArrayList<Post> posts = tempPosts;

//        ArrayList<Post> posts = (ArrayList<Post>) PostDAO.getRecentNPosts(NUMBER_OF_POSTS);
        return posts;
         */
        ArrayList<Post> posts = PostDAO.getRecentPosts();
        return posts;
    }

    public static Post getPostById(int postId){
        return PostDAO.selectPostById(postId);
    }
    
    public static boolean updatePost(int postId, String uname, String title, String message)
    {
        boolean success = false;

        //Remove all hashtags
        if (HashtagDAO.existsInPostHashtag(postId))
            success = HashtagDAO.deletePostHashTag(postId);
        else
            success = true;

        //Update the post
        if (success)
            success = PostDAO.updatePostDatabase(postId, uname, title, message);

        //Reinserts the hashtag - old and new
        if (success)
            success = HashtagManager.createHashTag(postId, message);

        return success;
    }

    public static boolean deletePost(int postId) {
        boolean deleted = false;

        if (HashtagDAO.existsInPostHashtag(postId))
            deleted = HashtagDAO.deletePostHashTag(postId);
        else
            deleted = true;

        Post post = PostDAO.selectPostById(postId);
        if (deleted)
            deleted = PostDAO.deletePostDatabase(postId);

        if(post.getAttID() != null && deleted)
            deleted = AttachmentDAO.delete(post.getAttID());

        return deleted;
    }
}
