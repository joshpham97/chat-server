package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.model.Group;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Stream;

public class GroupDAO {
    private static final String GROUPS_FILE = "groups.json";

    public static Group getGroup(int groupID) {
        try {
            Optional<Group> result = readGroupsFile()
                    .filter(g -> (((Group) g).getGroupID() == groupID))
                    .findFirst();

            if(result.isPresent())
                return result.get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Group getGroupByName(String groupName) {
        try {
            Optional<Group> result = readGroupsFile()
                    .filter(g -> (g.getGroupName().equals(groupName)))
                    .findFirst();

            if(result.isPresent())
                return result.get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read groups file and return contents as a Stream of Groups
    private static Stream<Group> readGroupsFile() throws Exception {
        InputStream inputStream = GroupDAO.class.getClassLoader().getResourceAsStream(GROUPS_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONArray ja = (JSONArray) obj; // Parse object to JSONArray

        return ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return jsonObjectToGroup(jo); // Convert to group
                });
    }

    // Convert JSONObject to Group
    private static Group jsonObjectToGroup(JSONObject jo) {
        Group group = new Group();

        group.setGroupID(Integer.parseInt(jo.get("groupID").toString()));
        group.setGroupName(jo.get("groupName").toString());

        Object objParent = jo.get("parent");
        if(objParent != null)
            group.setParent(Integer.parseInt(objParent.toString()));
        else
            group.setParent(null);

        return group;
    }

//    public static void main(String[] args) {
//        System.out.println(GroupDAO.getGroup(1));
//        System.out.println(GroupDAO.getGroupByName("encs"));
//    }
}
