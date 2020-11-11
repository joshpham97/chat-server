package server.chat.daoimpl;

import server.chat.Post;
import server.chat.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Blob;
import java.util.Set;

public class PostDAO extends DBConnection {
    public static Blob getAttachmentByPostId(int postId) {
        return null;
    }

    public static ArrayList<Post> searchNPostsWithOffset(String username, LocalDateTime from, LocalDateTime to,
                                                         List<String> hashtags, Integer n, Integer o) {
        String sql = searchPostsQueryBuilder(username, from, to, hashtags, n, o, "*");
        return getPostsHelper(sql);
    }

    public static int countPosts(String username, LocalDateTime from, LocalDateTime to, List<String> hashtags) {
        String sql = searchPostsQueryBuilder(username, from, to, hashtags, null, null, "COUNT(post_id) AS count");

        int count = -1; // Signals an error
        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                count = rs.getInt("count");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return count;
    }

    // Builds query based on arguments
    private static String searchPostsQueryBuilder(String username, LocalDateTime from, LocalDateTime to,
                                                 List<String> hashtags, Integer n, Integer o, String fields) {
        String sql = "SELECT " + fields + " FROM post_info";

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

        if(n != null && o != null)
                sql += " LIMIT " + n + " OFFSET " + o;

        return sql;
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
        post.setDatePosted(rs.getTimestamp("date_posted").toLocalDateTime());
        post.setDatePosted(rs.getTimestamp("date_modified").toLocalDateTime());
        post.setMessage((rs.getString("message")));
        post.setAttID(attID);

        return post;
    }

    public Post createPost(String username, String title, String message)
    {
        Post post = new Post();
        try{
            Connection conn = DBConnection.getConnection();

            LocalDateTime localDate = LocalDateTime.now();
            //Date date = Date.valueOf(localDate);
            //date = java.sql.Date.valueOf(String.valueOf(date));
            Timestamp timestamp = Timestamp.valueOf(localDate);
            //String title = "TITLE";
            String query = "INSERT INTO Post_info (username, title, date_posted, date_modified, message)" + " values (?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, title);
            stmt.setTimestamp(3, timestamp);
            stmt.setTimestamp(4, timestamp);
            stmt.setString(5, message);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey =0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            post.setPostID(generatedKey);
            post.setUsername(username);
            post.setTitle(title);
            post.setMessage(message);
            post.setDatePosted(timestamp.toLocalDateTime());
            post.setDateModified(timestamp.toLocalDateTime());
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return post;
    }

    public void insertHashtag(int postID, String hashtagWord)
    {
        try {
            int hashtagID = 0;
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Hashtag WHERE hashtag = ?");
            ps.setString(1, hashtagWord);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                hashtagID = r.getInt(1);
            }
            else
            {
                String query2 = "INSERT INTO Hashtag (hashtag)" + " value (?)";
                PreparedStatement ps1 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, hashtagWord);
                ps1.execute();
                ResultSet result = ps1.getGeneratedKeys();

                if (result.next()) {
                    hashtagID = result.getInt(1);
                }
            }
            insertPostHashTag(conn, postID, hashtagID);
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void insertPostHashTag(Connection conn, int postID, int hashtagID)
    {
        try {
            String query3 = "INSERT INTO Post_Hashtag (post_id, hashtag_id)" + " values (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query3);
            pstmt.setInt(1, postID);
            pstmt.setInt(2, hashtagID);
            pstmt.execute();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // Quick testing: to be removed later
//    public static void main(String[] args) {
//        ArrayList<Post> posts;
//        posts = searchNPostsWithOffset(null, null, null, null, null, null);
//        System.out.println(countPosts(null, null, null, null));
//
//        for (Post p: posts)
//            System.out.println(p.getPostID());
//    }
}
