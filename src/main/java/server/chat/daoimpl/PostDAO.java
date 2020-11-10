package server.chat.daoimpl;

import server.chat.Post;
import server.chat.db.DBConnection;
import server.chat.model.Attachment;

import java.sql.*;
import java.util.Set;

public class PostDAO extends DBConnection {
    public static Blob getAttachmentByPostId(int postId) {
        return null;
    }
    public static Set<Post> getRecentPosts() { return null; }
    public static Set<Post> getRecentNPosts(int n) { return null; }

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
                post.setAttID(rs.getInt("att_id"));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return post;
    }

    public static boolean updateAttachmentId(int postId, int attachmentId){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE post_info SET att_id = ? WHERE post_id = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, attachmentId);
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
