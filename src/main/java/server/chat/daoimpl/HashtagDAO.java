package server.chat.daoimpl;

import server.chat.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Handles querying the post_hashtag and hashtag tables
 */
public class HashtagDAO extends DBConnection {
    public static Integer getHashtagID(String hashtag) {
        String sql = "SELECT hashtag_id FROM hashtag " +
                "WHERE hashtag = '" + hashtag + "'";

        return getIdHelper(sql, "hashtag_id");
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

        return getIdsHelper(sql, "post_id");
    }

    public static Integer getIdHelper(String sql, String idField) {
        Integer id = null;

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                id = rs.getInt(idField);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return id;
    }

    public static ArrayList<Integer> getIdsHelper(String sql, String idField) {
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // For each element of query result
            while(rs.next())
                ids.add(rs.getInt(idField));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return ids;
    }

    // Quick testing: to be removed later
//    public static void main(String[] args) {
//        ArrayList<Integer> ids;
//        ids = getPostIDsByHashtags(Arrays.asList("one", "five"));
//        System.out.println(getHashtagID("fakehashtag"));
//
//        for (Integer i: ids)
//            System.out.println(i);
//    }
}
