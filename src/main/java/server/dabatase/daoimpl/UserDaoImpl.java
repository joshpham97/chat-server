package server.dabatase.daoimpl;

import server.dabatase.dao.UserDAO;
import server.dabatase.db.DBConnection;
import server.dabatase.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO {
    @Override
    public User getUser(int userID) {
        User user = null;

        try {
            Connection conn = DBConnection.getConnection();

            // Get query result
            String sql = "SELECT * FROM user WHERE id = " + userID;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // If query result is not empty
            if(rs.next())
                user = resultSetToUser(rs);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM user WHERE username = \"" + username + "\"";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                user = resultSetToUser(rs);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }

        return user;
    }

    // Takes query result and returns a User
    private User resultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setUserID(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }

    // Quick testing: to be removed later
//    public static void main(String[] args) {
//        UserDaoImpl test = new UserDaoImpl();
//        System.out.println(test.getUser(1));
//        System.out.println(test.getUserByUsername("username2"));
//    }
}