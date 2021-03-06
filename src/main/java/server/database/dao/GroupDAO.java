package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.model.Group;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupDAO {
    private static final String DEFAULT_FILE = "groups.json";
    private String groups_file;

    public GroupDAO(String filePath) {
        groups_file = filePath;
        checkData();
    }

    public GroupDAO() {
        this(DEFAULT_FILE);
    }

    public Group getGroup(int groupID) {
        try {
            Optional<Group> result = readGroupsFile()
                    .filter(g -> (((Group) g).getGroupID() == groupID))
                    .findFirst();

            if (result.isPresent())
                return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Group getGroupByName(String groupName) {
        try {
            Optional<Group> result = readGroupsFile()
                    .filter(g -> (g.getGroupName().equals(groupName)))
                    .findFirst();

            if (result.isPresent())
                return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Group> getChildGroups(List<String> groupNames) {
        try {
            ArrayList<Integer> parents = new ArrayList<>();
            for (String name : groupNames) {
                parents.add(getGroupByName(name).getGroupID());
            }

            return readGroupsFile()
                    .map(g -> {
                        if (g.getParent() != null && parents.contains(g.getParent())) {
                            parents.add(g.getGroupID());
                            return g;
                        }

                        return null;
                    })
                    .filter(g -> (g != null))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read groups file and return contents as a Stream of Groups
    private Stream<Group> readGroupsFile() throws Exception {
        InputStream inputStream;

        if(groups_file.equals(DEFAULT_FILE))
            inputStream = GroupDAO.class.getClassLoader().getResourceAsStream(groups_file); // Get resource
        else
            inputStream = new FileInputStream(groups_file);

        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONArray ja = (JSONArray) obj; // Parse object to JSONArray

        return ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return jsonObjectToGroup(jo); // Convert to group
                });
    }

    // Convert JSONObject to Group
    private Group jsonObjectToGroup(JSONObject jo) {
        Group group = new Group();

        try {
            group.setGroupID(Integer.parseInt(jo.get("groupID").toString()));
            group.setGroupName(jo.get("groupName").toString());


            Object objParent = jo.get("parent");
            if (objParent != null)
                group.setParent(Integer.parseInt(objParent.toString()));
            else
                group.setParent(null);
        } catch (Exception e) {
            throw new Error("Erroneous Group data: invalid field");
        }

        return group;
    }

    // Checks for erroneous data
    private void checkData() {
        try {
            Stream<Group> groups = readGroupsFile();

            groups.forEach(g -> {
                Integer parent = g.getParent();

                if (parent != null) {
                    Group parentGroup = getGroup(g.getParent());

                    if (parentGroup == null)
                        throw new Error("Erroneous Group data: undefined parent");
                    else if (parent >= g.getGroupID()) // Prevents circular parent-child definitions
                        throw new Error("Erroneous Group data: invalid parent-child definition");
                    else if (g.getGroupName().isEmpty())
                        throw new Error("Erroneous Group data: empty group name");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        System.out.println(GroupDAO.getGroup(1));
//        System.out.println(GroupDAO.getGroupByName("encs"));
//        System.out.println(GroupDAO.getChildGroups("concordia"));
//    }
}
