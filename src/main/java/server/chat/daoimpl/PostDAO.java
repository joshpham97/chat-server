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

    public static ArrayList<Post> searchPostsByUsername(String username) {
        String sql = "SELECT * FROM post_info " +
                "WHERE username LIKE \'%" + username + "%\' " +
                "ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByDatePosted(LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM post_info";

        if(from != null && to != null)
            sql += " WHERE date_posted >= \'" + from.toLocalDate() + "\' " +
                    "AND date_posted < \'" + to.toLocalDate() + "\'";
        else if(from != null)
            sql += " WHERE date_posted >= \'" + from.toLocalDate() + "\'";
        else if(to != null)
            sql += " WHERE date_posted < \'" + to.toLocalDate() + "\'";

        sql += " ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByDateModified(LocalDateTime from, LocalDateTime to) {
        String sql = "SELECT * FROM post_info";

        if(from != null && to != null)
            sql += " WHERE date_modified >= \'" + from.toLocalDate() + "\' " +
                    "AND date_modified < \'" + to.toLocalDate() + "\'";
        else if(from != null)
            sql += " WHERE date_modified >= \'" + from.toLocalDate() + "\'";
        else if(to != null)
            sql += " WHERE date_modified < \'" + to.toLocalDate() + "\'";

        sql += " ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPostsByHashtags(List<String> hashtags) {
        // Get the post ids
        ArrayList<Integer> postIDs = HashtagDAO.getPostIDsByHashtags(hashtags);

        if(postIDs.size() == 0)
            return null;

        // Get the posts
        String postIDsStr = "";
        for (Integer i: postIDs)
            postIDsStr += i + ", ";
        postIDsStr = postIDsStr.substring(0, postIDsStr.length() - 2);

        String sql = "SELECT * FROM post_info " +
                "WHERE post_id IN (" + postIDsStr + ") " +
                "ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static ArrayList<Post> searchPosts(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        String sql = "SELECT * FROM post_info";

        String[] conj = new String[]{"WHERE", "AND"}; // Conjunctions for WHERE clause
        int conjNumb = 0;
        if(username != null) {
            sql += " " + conj[conjNumb] + " username LIKE \'%" + username + "%\'";
            conjNumb = 1;
        }
        if(from != null || to != null) {
            if(from != null && to != null)
                sql += " " + conj[conjNumb] + " date_modified >= \'" + from.toLocalDate() + "\' " +
                        "AND date_modified < \'" + to.toLocalDate() + "\'";
            else if(from != null)
                sql += " " + conj[conjNumb] + " date_modified >= \'" + from.toLocalDate() + "\'";
            else if(to != null)
                sql += " " + conj[conjNumb] + " date_modified < \'" + to.toLocalDate() + "\'";

            conjNumb = 1;
        }
        if(hashtags != null && hashtags.size() != 0) {
            // Get the post ids
            ArrayList<Integer> postIDs = HashtagDAO.getPostIDsByHashtags(hashtags);

            if(postIDs.size() != 0) {
                // Build a string of comma separated post ids
                String postIDsStr = "";
                for (Integer i : postIDs)
                    postIDsStr += i + ", ";
                postIDsStr = postIDsStr.substring(0, postIDsStr.length() - 2);

                sql += " " + conj[conjNumb] + " post_id IN (" + postIDsStr + ")";
                conjNumb = 1;
            }
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
//        posts = searchPostsByUsername("user");
//        posts = searchPostsByDatePosted(LocalDateTime.now().minusDays(6), LocalDateTime.now().minusDays(4));
//        posts = searchPostsByDateModified(LocalDateTime.now().minusDays(6), LocalDateTime.now().minusDays(4));
//        posts = searchPostsByHashtags(Arrays.asList("one", "three"));
//        posts = searchPosts("1", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1), Arrays.asList("one", "three"));
//
//
//        for (Post p: posts)
//            System.out.println(p.getPostID());
//    }
}
