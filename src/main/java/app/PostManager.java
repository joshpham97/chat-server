package app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.dao.PostDAO;
import server.database.model.PostList;
import server.database.dao.AttachmentDAO;
import server.database.model.Post;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static PostList searchPosts(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        // Get the post ids
        ArrayList<Integer> postIDs = HashtagManager.getPostIDsByHashtags(hashtags);

        ArrayList<Post> posts = PostDAO.searchNPostsWithOffset(username, from, to, formatListToString(postIDs), null, null);
        return new PostList(posts);
    }

    public static PostList searchPostsWithPagination(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags, int pageNumb) {
        int limit = NUMBER_OF_POSTS;
        int offset = NUMBER_OF_POSTS * (pageNumb - 1);

        // Get the post ids
        ArrayList<Integer> postIDs = HashtagManager.getPostIDsByHashtags(hashtags);

        ArrayList<Post> posts = PostDAO.searchNPostsWithOffset(username, from, to, formatListToString(postIDs), limit, offset);
        return new PostList(posts);
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

    public static int getNumberOfPages(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        // Get the post ids
        ArrayList<Integer> postIDs = HashtagManager.getPostIDsByHashtags(hashtags);

        int postCount = PostDAO.countPosts(username, from, to, formatListToString(postIDs));
        int pages = (int) Math.ceil(((double) postCount) / NUMBER_OF_POSTS);
        return pages;
    }

    public static Post getPostById(int postId){
        return PostDAO.selectPostById(postId);
    }

    public static boolean updatePost(int postId, String uname, String title, String message)
    {
        boolean success = false;

        //Remove all hashtags
        if (HashtagManager.existsInPostHashtag(postId))
            success = HashtagManager.deletePostHashTag(postId);
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

    public static boolean deletePost(int postId, HttpSession session) {
        boolean deleted = false;
        /** Code modified by stefan */
        String uname = (String)session.getAttribute("username");
        ArrayList<String> n = (ArrayList<String>)session.getAttribute("membership");
        String groupName = n.get(0);
        Post specificPost = PostManager.getPostById(postId);

        if(groupName.equals("admins") || specificPost.getUsername().equals(uname))
        {
            if (HashtagManager.existsInPostHashtag(postId))
                deleted = HashtagManager.deletePostHashTag(postId);
            else
                deleted = true;

            Post post = PostDAO.selectPostById(postId);
            if (deleted)
                deleted = PostDAO.deletePostDatabase(postId);

            if(post.getAttID() != null && deleted)
                deleted = AttachmentManager.deleteAttachment(post.getAttID());

        }
        else
        {
            deleted = false;
        }


        return deleted;
    }

    public static boolean updateAttachmentId(int postId, Integer attachmentId){
        return PostDAO.updateAttachmentId(postId, attachmentId);
    }

    public static boolean updateModifiedDate(int postId){
        return PostDAO.updateModifiedDate(postId);
    }

    private static String formatListToString(ArrayList<Integer> postIDs) {
        if(postIDs == null || postIDs.size() == 0)
            return null;

        String postIDsStr = "";
        // Build a string of comma separated post ids
        for (Integer i : postIDs)
            postIDsStr += i + ", ";
        postIDsStr = postIDsStr.substring(0, postIDsStr.length() - 2);

        return postIDsStr;
    }
}
