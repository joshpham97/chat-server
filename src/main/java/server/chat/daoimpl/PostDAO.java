package server.chat.daoimpl;

import server.chat.Post;
import server.chat.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDAO extends DBConnection {
    public static Blob getAttachmentByPostId(int postId) {
        return null;
    }

    public static ArrayList<Post> getRecentPosts() {
        // Get query result
        String sql = "SELECT * FROM post_info " +
                     "ORDER BY date_modified DESC, date_posted DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> getRecentNPosts(int n) {
        String sql = "SELECT * FROM post_info " +
                     "ORDER BY date_modified DESC, date_posted DESC " +
                     "LIMIT " + n;

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> getRecentNPostsWithOffset(int n, int o) {
        String sql = "SELECT * FROM post_info " +
                "ORDER BY date_modified DESC, date_posted DESC " +
                "LIMIT " + n + " OFFSET " + o;

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByUsername(String username) {
        String sql = "SELECT * FROM post_info " +
                "WHERE username LIKE \'%" + username + "%\' " +
                "ORDER BY date_modified DESC, date_posted DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByDatePosted(LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM post_info " +
                "WHERE date_posted >= \'" + from.toLocalDate() + "\'" +
                "AND date_posted <= \'" + to.toLocalDate() + "\'";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByDateModified(LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM post_info " +
                "WHERE date_modified >= \'" + from.toLocalDate() + "\'" +
                "AND date_modified <= \'" + to.toLocalDate() + "\'";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostByHashtags(List<String> hashtags) {
        // Get the hashtag ids
        ArrayList<Integer> hashtagIDs = HashtagDAO.getHashtagIDs(hashtags);

        // Get the post ids
        ArrayList<Integer> postIDs = HashtagDAO.getPostIDs(hashtagIDs);

        // Get the posts
        String postIDsStr = "";
        for (Integer i: postIDs)
            postIDsStr += i + ", ";
        postIDsStr = postIDsStr.substring(0, postIDsStr.length() - 2);

        String sql = "SELECT * FROM post_info " +
                "WHERE post_id IN (" + postIDsStr + ")";

        return getPostsHelper(sql);
    }

    private static ArrayList<Post> getPostsHelper(String sql) {
        ArrayList<Post> posts = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                posts.add(resultSetToPost(rs));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return posts;
    }

    private static Post resultSetToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        Integer attID = rs.getInt("att_id");
        if(attID == 0) { // Means that db field was null
            attID = null;
        }

        post.setPostID(rs.getInt("post_id"));
        post.setUsername(rs.getString("username"));
        post.setTitle(rs.getString("title"));
        post.setDatePosted(rs.getDate("date_posted").toLocalDate().atStartOfDay());
        post.setDatePosted(rs.getDate("date_modified").toLocalDate().atStartOfDay());
        post.setMessage((rs.getString("message")));
        post.setAttID(attID);

        return post;
    }

    // Quick testing: to be removed later
    public static void main(String[] args) {
        ArrayList<Post> posts;
//        posts = getRecentPosts();
//        posts = getRecentNPosts(3);
//        posts = getRecentNPostsWithOffset(3, 1);
//        posts = searchPostsByUsername("user");
//        posts = searchPostsByDatePosted(LocalDateTime.now().minusDays(6), LocalDateTime.now().minusDays(4));
//        posts = searchPostsByDateModified(LocalDateTime.now().minusDays(6), LocalDateTime.now().minusDays(4));

        posts = searchPostByHashtags(Arrays.asList("three", "five"));

        for (Post p: posts)
            System.out.println(p.getPostID());
    }
}
