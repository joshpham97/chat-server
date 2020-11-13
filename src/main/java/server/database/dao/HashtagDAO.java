package server.database.dao;

import server.database.model.Hashtag;
import server.database.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HashtagDAO {
    private static Hashtag mapResultSetToHashtag(ResultSet resultSet){
        Hashtag hashtag = null;

        try{
            if (resultSet.next()) {
                hashtag = new Hashtag();
                hashtag.setId(resultSet.getInt("hashtag_id"));
                hashtag.setHashtag(resultSet.getString("hashtag"));
            }
        }catch (SQLException ex){
            System.err.println("There was an issue parsing the result set.");
            System.err.println(ex.getMessage());
        }

        return hashtag;
    }

    public static Hashtag selectByHashtag(String hashtagWord){
        Connection conn;
        Hashtag hashtag = null;

        try{
            conn = DBConnection.getConnection();
            PreparedStatement selectHashTagQuery = conn.prepareStatement("SELECT * FROM Hashtag WHERE hashtag = ?");
            selectHashTagQuery.setString(1, hashtagWord);
            ResultSet r = selectHashTagQuery.executeQuery();
            hashtag = mapResultSetToHashtag(r);
        }catch (SQLException ex){
            System.err.println("There was an issue getting the hash tag.");
            System.err.println(ex.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return hashtag;
    }

    public static Integer insert(int postID, String hashtagWord) {
        Connection conn;
        Integer hashtagID = null;
        try {
            conn = DBConnection.getConnection();
            PreparedStatement insertHashTagQuery = conn.prepareStatement("INSERT INTO Hashtag (hashtag) value (?)", Statement.RETURN_GENERATED_KEYS);
            insertHashTagQuery.setString(1, hashtagWord);
            insertHashTagQuery.execute();
            ResultSet generatedKeys = insertHashTagQuery.getGeneratedKeys();

            if (generatedKeys.next()) {
                hashtagID = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("There was an issue inserting the hash tag.");
            System.err.println(e.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return hashtagID;
    }

    public static boolean insertPostHashTag(int postID, int hashtagID) {
        Connection conn;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();
            String query = "INSERT INTO Post_Hashtag (post_id, hashtag_id)" + " values (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postID);
            preparedStatement.setInt(2, hashtagID);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                success = true;
            }

        } catch (Exception e) {
            System.err.println("There was an issue inserting the post-hashtag row");
            System.err.println(e.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return success;
    }

    public static boolean existsInPostHashtag(int postId) {
        Connection conn = null;
        boolean exists = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * from post_hashtag where post_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                exists = true;
            }
        } catch (Exception e) {
            System.err.println("There was an error checking if hash tags exist in post");
            System.err.println(e.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return exists;
    }

    public static boolean deletePostHashTag(int postId){
        Connection conn = null;
        boolean success = false;

        try {
            conn = DBConnection.getConnection();

            String query = "DELETE from post_hashtag where post_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, postId);
            int row = pstmt.executeUpdate();

            if (row > 0) {
                success = true;
            }
        } catch (Exception e) {
            System.err.println("There was an error deleting entry from post-hashtag table");
            System.err.println(e.getMessage());
        }finally {
            DBConnection.closeConnection();
        }

        return success;
    }

    public static Integer getHashtagID(String hashtag) {
        String sql = "SELECT hashtag_id FROM hashtag " +
                "WHERE hashtag = '" + hashtag + "'";

        Integer id = null;
        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                id = rs.getInt("hashtag_id");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return id;
    }

    public static ArrayList<Integer> getPostIDsByHashtags(List<String> hashtags) {
        // Build a string of comma separated hashtags
        String hashtagStr = "";
        for (String h: hashtags)
            hashtagStr += "'" + h + "', ";
        hashtagStr = hashtagStr.substring(0, hashtagStr.length() - 2);

        // Get the post ids
        String sql = "SELECT DISTINCT post_id FROM post_hashtag " +
                "WHERE hashtag_id IN (SELECT hashtag_id " +
                "FROM hashtag " +
                "WHERE hashtag IN (" + hashtagStr + ")" +
                ")";

        ArrayList<Integer> ids = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                ids.add(rs.getInt("post_id"));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return ids;
    }

}
