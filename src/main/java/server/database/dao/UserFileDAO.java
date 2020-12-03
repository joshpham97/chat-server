package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.model.User;

import java.io.*;
import java.util.Optional;
import java.util.stream.Stream;

public class UserFileDAO {
    private static final String USERS_FILE = "users.json";

    public static User getUser(int userID) {
        try {
            // Get user by id
            Optional<User> result = readUsersFile()
                    .filter(u -> (u.getUserID() == userID))
                    .findFirst();

            if(result.isPresent())
                return result.get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getUserByUsername(String username) {
        try {
            // Get user by username
            Optional<User> result = readUsersFile()
                    .filter(u -> (u.getUsername().equals(username)))
                    .findFirst();

            if(result.isPresent())
                return result.get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read users file and return contents as a Stream of Users
    private static Stream<User> readUsersFile() throws Exception {
        InputStream inputStream = UserFileDAO.class.getClassLoader().getResourceAsStream(USERS_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONArray ja = (JSONArray) obj; // Parse object to JSONArray

        return ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return jsonObjectToUser(jo); // Convert to user
                });
    }

    // Convert JSONObject to User
    private static User jsonObjectToUser(JSONObject jo) {
        User user = new User();

        user.setUserID(Integer.parseInt(jo.get("userID").toString()));
        user.setUsername(jo.get("username").toString());
        user.setFirstName(jo.get("firstName").toString());
        user.setLastName(jo.get("lastName").toString());
        user.setEmail(jo.get("email").toString());
        user.setPassword(jo.get("password").toString());

        return user;
    }

    public static void main(String[] args) {
        System.out.println(UserFileDAO.getUser(1));
        System.out.println(UserFileDAO.getUserByUsername("john"));
    }
}
