package server.dabatase.dao;

import server.dabatase.model.User;

public interface UserDAO {
//    Set<User> getAllUsers();
    User getUser(int userID);
    User getUserByUsername(String username);
//    boolean insertUser(User user);
//    boolean updateUser(User user);
//    boolean deleteUser(int userID);
}
