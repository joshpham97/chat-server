package server.chat.daoimpl;

import server.chat.Post;
import server.chat.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Set;

public class PostDAO extends DBConnection {
    public static Blob getAttachmentByPostId(int postId) {
        return null;
    }
    public Post createPost(String username, String title, String message)
    {
        Post post = new Post();
        try{
            Connection conn = DBConnection.getConnection();

            LocalDateTime localDate = LocalDateTime.now();
            //Date date = Date.valueOf(localDate);
            //date = java.sql.Date.valueOf(String.valueOf(date));
            java.sql.Date date = java.sql.Date.valueOf(localDate.toLocalDate());
            //String title = "TITLE";
            String query = "INSERT INTO Post_info (username, title, date_posted, date_modified, message)" + " values (?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, title);
            stmt.setDate(3, date);
            stmt.setDate(4, date);
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
            post.setDatePosted(date.toLocalDate().atStartOfDay());
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
    public static ArrayList<Post> getRecentPosts() {
        //return null;
        // Get query result
        String sql = "SELECT * FROM post_info " +
                "ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }
    public static Set<Post> getRecentNPosts(int n) { return null; }
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
    public boolean deletePostDatabase(int postId)
    {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            if(existsInPostHashtag(conn, postId))
            {
                String query1 = "DELETE from post_hashtag where post_id=?";
                PreparedStatement pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, postId);
                pstmt.executeUpdate();
            }

            String sql = "DELETE from post_info where post_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();

            System.out.println("Record has been deleted!");
            success = true;

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return success;
    }
    public boolean existsInPostHashtag(Connection conn, int postId)
    {
        boolean exists = false;
        try {
            String sql = "SELECT * from post_hashtag where post_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                exists = true;
            }
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return exists;
    }

}
