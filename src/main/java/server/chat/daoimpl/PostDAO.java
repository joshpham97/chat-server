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
                     "ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> getRecentNPosts(int n) {
        String sql = "SELECT * FROM post_info " +
                     "ORDER BY date_modified DESC, date_posted DESC, post_id DESC " +
                     "LIMIT " + n;

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> getRecentNPostsWithOffset(int n, int o) {
        String sql = "SELECT * FROM post_info " +
                "ORDER BY date_modified DESC, date_posted DESC, post_id DESC " +
                "LIMIT " + n + " OFFSET " + o;

        return getPostsHelper(sql);
    }

    // General search: builds query based on arguments
    public static ArrayList<Post> searchPosts(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        String sql = "SELECT * FROM post_info";

        // Conjunctions for WHERE clause (for query building)
        String[] conj = new String[]{"WHERE", "AND"};
        int conjNumb = 0; // First conjunction is WHERE

        // Username filtering
        if(username != null) {
            sql += " " + conj[conjNumb] + " username LIKE \'%" + username + "%\'";
            conjNumb = 1;
        }

        // Date filtering (lower bound)
        if(from != null) {
            sql += " " + conj[conjNumb] + " date_modified >= \'" + from.toLocalDate() + "\'";
            conjNumb = 1;
        }

        // Date filtering (upper bound)
        if(to != null) {
            sql += " " + conj[conjNumb] + " date_modified < \'" + to.toLocalDate() + "\'";
            conjNumb = 1;
        }

        // Hashtag filtering
        if(hashtags != null && hashtags.size() != 0) {
            // Get the post ids
            ArrayList<Integer> postIDs = HashtagDAO.getPostIDsByHashtags(hashtags);

            if(postIDs.size() == 0) {
                return null;
            }

            // Build a string of comma separated post ids
            String postIDsStr = "";
            for (Integer i : postIDs)
                postIDsStr += i + ", ";
            postIDsStr = postIDsStr.substring(0, postIDsStr.length() - 2);

            sql += " " + conj[conjNumb] + " post_id IN (" + postIDsStr + ")";
            conjNumb = 1;
        }

        sql += " ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

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
//    public static void main(String[] args) {
//        ArrayList<Post> posts;
//        posts = getRecentPosts();
//        posts = getRecentNPosts(3);
//        posts = getRecentNPostsWithOffset(3, 1);
//        posts = searchPosts("1", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1), Arrays.asList("one", "three"));
//
//        for (Post p: posts)
//            System.out.println(p.getPostID());
//    }
}
