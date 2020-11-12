package server.database.dao;
import server.database.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
    Handles querying the post_hashtag and hashtag tables
 */
public class HashtagDAO extends DBConnection {
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

    public static void insertHashtag(int postID, String hashtagWord) {
        try {
            int hashtagID = 0;
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Hashtag WHERE hashtag = ?");
            ps.setString(1, hashtagWord);
            ResultSet r = ps.executeQuery();

            if (r.next()) {
                hashtagID = r.getInt(1);
            } else {
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
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public static void insertPostHashTag(Connection conn, int postID, int hashtagID) {
        try {
            String query3 = "INSERT INTO Post_Hashtag (post_id, hashtag_id)" + " values (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query3);
            pstmt.setInt(1, postID);
            pstmt.setInt(2, hashtagID);
            pstmt.execute();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
