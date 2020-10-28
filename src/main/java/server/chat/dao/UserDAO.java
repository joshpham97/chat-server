package server.chat.dao;

import server.chat.model.User;

import java.util.Set;

public interface UserDAO {
//    Set<User> getAllUsers();
    User getUser(int userID);
    User getUserByUsername(String username);
//    boolean insertUser(User user);
//    boolean updateUser(User user);
//    boolean deleteUser(int userID);
}
