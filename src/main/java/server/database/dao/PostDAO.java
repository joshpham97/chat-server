package server.database.dao;

import app.HashtagManager;
import server.database.model.Post;
import server.database.db.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PostDAO {
    public static ArrayList<Post> searchNPostsWithOffset(String username, LocalDateTime from, LocalDateTime to,
                                                         String postIdsStr, Integer n, Integer o) {
        String sql = searchPostsQueryBuilder(username, from, to, postIdsStr, n, o, "*");
        return getPostsHelper(sql);
    }

    public static int countPosts(String username, LocalDateTime from, LocalDateTime to, String postIDsStr) {
        String sql = searchPostsQueryBuilder(username, from, to, postIDsStr, null, null, "COUNT(post_id) AS count");

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
                                                  String postIDsStr, Integer n, Integer o, String fields) {
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
        if(postIDsStr != null && !postIDsStr.isEmpty()) {
            sql += " " + conj[conjNumb] + " post_id IN (" + postIDsStr + ")";
            conjNumb = 1;
        }

        sql += " ORDER BY date_modified DESC, date_posted DESC, post_id DESC";

        if(n != null && o != null)
            sql += " LIMIT " + n + " OFFSET " + o;

        return sql;
    }

    public static Integer insert(String username, String title, String message, String group) {
        Post post = new Post();
        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO Post_info (username, title, date_posted, date_modified, message, permission_group)" + " values (?,?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, username);
            stmt.setString(2, title);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(5, message);
            stmt.setString(6, group);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            Integer generatedKey = null;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            return generatedKey;
        } catch (Exception e) {
            System.err.println("There was an error creating a new post in database.");
            System.err.println(e.getMessage());
        }

        return null;
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
            while(rs.next())
                posts.add(resultSetToPost(rs));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return posts;
    }

    private static Post mapResultSetToPost(ResultSet rs) throws SQLException {
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
        Connection conn;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "DELETE from post_info where post_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            int row = ps.executeUpdate();

            if (row > 0)
                success = true;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return success;
    }

    public static boolean updatePostDatabase(int postId, String title, String message, String group) {
        boolean success = false;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE post_info SET title = ?, date_modified = ?, message = ?, permission_group = ? WHERE post_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, message);
            ps.setString(4, group);
            ps.setInt(5, postId);
            int row = ps.executeUpdate();

            if (row > 0)
                success = true;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static Post selectPostById(int postId){
        Post post = null;

        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM post_info WHERE post_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, postId);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                post = mapResultSetToPost(rs);
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
}
