package server.chat.daoimpl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.chat.dao.UserFileDAO;
import server.chat.model.User;

import java.io.FileReader;
import java.util.Optional;

public class UserFileDaoImpl implements UserFileDAO {
    private final String USERS_FILE = "src\\main\\users.json";

    @Override
    public User getUser(int userID) {
        Object obj;
        JSONArray ja;
        User user = new User();

        try {
            // Read users file
            obj = new JSONParser().parse(new FileReader(USERS_FILE));
            ja = (JSONArray) obj;

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

    public User jsonObjectToUser(JSONObject jo) {
        User user = new User();

        user.setUserID(Integer.parseInt(jo.get("userID").toString()));
        user.setUsername(jo.get("username").toString());
        user.setFirstName(jo.get("firstName").toString());
        user.setLastName(jo.get("lastName").toString());
        user.setEmail(jo.get("email").toString());
        user.setPassword(jo.get("password").toString());

        return user;
    }

    // Quick testing: to be removed later
    public static void main(String[] args) {
        UserFileDaoImpl test = new UserFileDaoImpl();
        System.out.println(test.getUser(1).getUsername());
    }
}
