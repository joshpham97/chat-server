package server.dabatase.daoimpl;

import server.dabatase.model.Post;
import server.dabatase.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Set;

public class PostDAO extends DBConnection {
    public static Blob getAttachmentByPostId(int postId) {
        return null;
    }

    public static Post createPost(String username, String title, String message) {
        Post post = new Post();
        try {
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
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            post.setPostID(generatedKey);
            post.setUsername(username);
            post.setTitle(title);
            post.setMessage(message);
            post.setDatePosted(date.toLocalDate().atStartOfDay());
            post.setDateModified(date.toLocalDate().atStartOfDay());
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return post;
    }


    public static ArrayList<Post> getRecentPosts() {
        //return null;
        // Get query result
        String sql = "SELECT * FROM post_info " +
                "ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        return getPostsHelper(sql);
    }

    public static Set<Post> getRecentNPosts(int n) {
        return null;
    }

    private static ArrayList<Post> getPostsHelper(String sql) {
        ArrayList<Post> posts = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while (rs.next())
                posts.add(resultSetToPost(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return posts;
    }

    private static Post resultSetToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        Integer attID = rs.getInt("att_id");
        if (attID == 0) { // Means that db field was null
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

    public static boolean deletePostDatabase(int postId) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            if (existsInPostHashtag(conn, postId)) {
                String query1 = "DELETE from post_hashtag where post_id=?";
                PreparedStatement pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, postId);
                pstmt.executeUpdate();
            }

            String sql = "DELETE from post_info where post_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();

            success = true;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static boolean existsInPostHashtag(Connection conn, int postId) {
        boolean exists = false;
        try {
            String sql = "SELECT * from post_hashtag where post_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                exists = true;
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return exists;
    }

    public static Post updatePostDatabase(int postId, String uname, String title, String message) {
        Post post = new Post();

        //LocalDateTime localDate = LocalDateTime.now();
        //java.sql.Date date = java.sql.Date.valueOf(localDate.toLocalDate());

        try {
            Connection conn = DBConnection.getConnection();
            if (existsInPostHashtag(conn, postId)) {
                String query1 = "DELETE from post_hashtag where post_id=?";
                PreparedStatement pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, postId);
                pstmt.executeUpdate();
            }
            String sql = "UPDATE post_info SET username = ?, title = ?, date_modified = ?, message = ? WHERE post_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, title);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, message);
            ps.setInt(5, postId);
            ps.executeUpdate();

            /*.setPostID(postId);
            post.setUsername(uname);
            post.setTitle(title);
            post.setMessage(message);
            post.setDateModified(date.toLocalDate().atStartOfDay());*/

            post = resultSetToPost(ps.getResultSet());

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return post;
    }

    public static Post selectPostById(int postId){
        Post post = null;

        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM post_info WHERE post_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, postId);

            ResultSet rs = statement.executeQuery();


            if(rs.next()) {
                post = new Post();
                post.setPostID(rs.getInt("post_id"));
                post.setUsername(rs.getString("username"));
                post.setTitle(rs.getString("title"));
                post.setDatePosted(rs.getTimestamp("date_posted").toLocalDateTime());
                post.setDateModified(rs.getTimestamp("date_modified").toLocalDateTime());
                post.setMessage(rs.getString("message"));

                Integer attachmentId = rs.getInt("att_id");
                if(rs.wasNull())
                    post.setAttID(null);
                else
                    post.setAttID(attachmentId);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return post;
    }

    public static boolean updateAttachmentId(int postId, Integer attachmentId){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE post_info SET att_id = ? WHERE post_id = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            LocalDateTime modifiedDate = LocalDateTime.now();

            if(attachmentId != null)
                statement.setInt(1, attachmentId);
            else
                statement.setNull(1, Types.INTEGER);
            statement.setInt(2, postId);

            int row = statement.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return false;
    }

    public static boolean updateModifiedDate(int postId){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE post_info SET date_modified = ? WHERE post_id = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            LocalDateTime modifiedDate = LocalDateTime.now();

            statement.setTimestamp(1, Timestamp.valueOf(modifiedDate));
            statement.setInt(2, postId);

            int row = statement.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return false;
    }
}
