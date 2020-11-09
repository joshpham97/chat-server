package server.chat.daoimpl;

import server.chat.Post;
import server.chat.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Blob;
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
            System.out.println("Entered conn");
            //Statement stmt = conn.createStatement();

            System.out.println("create statement");
            //long millis=System.currentTimeMillis();
            //java.sql.Date date=new java.sql.Date(millis);

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
    public static Set<Post> getRecentPosts() { return null; }
    public static Set<Post> getRecentNPosts(int n) { return null; }
}
