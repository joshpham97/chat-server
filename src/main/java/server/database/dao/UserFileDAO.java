package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.db.DBConnection;
import server.database.model.User;

import java.io.*;
import java.util.Optional;

public class UserFileDAO {
    private final String USERS_FILE = "users.json";

    public User getUser(int userID) {
        JSONArray ja;
        User user = null;

        try {
            // Read users file
            ja = readUsersFile();

            // Get user by id
            Optional<User> result = ja.stream()
                    .map(o -> {
                        JSONObject jo = (JSONObject) o;
                        return jsonObjectToUser(jo); // Convert to user
                    })
                    .filter(u -> (((User) u).getUserID() == userID)).findFirst();

            if(result.isPresent())
                user = result.get();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByUsername(String username) {
        JSONArray ja;
        User user = null;

        try {
            // Read users file
            ja = readUsersFile();

            // Get user by username
            Optional<User> result = ja.stream()
                    .map(o -> {
                        JSONObject jo = (JSONObject) o;
                        return jsonObjectToUser(jo); // Convert to user
                    })
                    .filter(u -> (((User) u).getUsername().equals(username))).findFirst();

            if(result.isPresent())
                user = result.get();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // Read users file and return contents as JSONArray
    private JSONArray readUsersFile() throws Exception {
        JSONArray ja = null;

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USERS_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        ja = (JSONArray) obj; // Parse object to JSONArray

        return ja;
    }

    // Convert JSONObject to User
    private User jsonObjectToUser(JSONObject jo) {
        User user = new User();

        user.setUserID(Integer.parseInt(jo.get("userID").toString()));
        user.setUsername(jo.get("username").toString());
        user.setFirstName(jo.get("firstName").toString());
        user.setLastName(jo.get("lastName").toString());
        user.setEmail(jo.get("email").toString());
        user.setPassword(jo.get("password").toString());

        return user;
    }
}
