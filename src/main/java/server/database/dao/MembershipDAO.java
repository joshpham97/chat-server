package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.model.Membership;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MembershipDAO {
    private static final String DEFAULT_FILE = "memberships.json";
    private String memberships_file;

    public MembershipDAO(String filePath) {
        memberships_file = filePath;
        checkData();
    }

    public MembershipDAO() {
        this(DEFAULT_FILE);
    }

    // Gets all memberships for a user
    public ArrayList<Membership> getUserMemberships(int userID) {
        try {
            return readMembershipsFile()
                    .filter(m -> (m.getUserID() == userID))
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Gets all memberships for a group
    public ArrayList<Membership> getGroupMemberships(int groupID) {
        try {
            return readMembershipsFile()
                    .filter(m -> (m.getGroupID() == groupID))
                    .collect(Collectors.toCollection(ArrayList::new));

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read groups file and return contents as a Stream of Groups
    private Stream<Membership> readMembershipsFile() throws Exception {
        InputStream inputStream;

        if(memberships_file.equals(DEFAULT_FILE))
            inputStream = GroupDAO.class.getClassLoader().getResourceAsStream(memberships_file); // Get resource
        else
            inputStream = new FileInputStream(memberships_file);

        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONArray ja = (JSONArray) obj; // Parse object to JSONArray

        return ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return jsonObjectToMembership(jo); // Convert to membership
                });
    }

    // Convert JSONObject to Membership
    private Membership jsonObjectToMembership(JSONObject jo) {
        Membership membership = new Membership();

        try {
            membership.setUserID(Integer.parseInt(jo.get("userID").toString()));
            membership.setGroupID(Integer.parseInt(jo.get("groupID").toString()));
        } catch(Exception e) {
            throw new Error("Erroneous Membership data: invalid field");
        }

        return membership;
    }

    // Checks for erroneous data
    private void checkData() {
        try {
            Stream<Membership> membs = readMembershipsFile();
            GroupDAO groupDAO = new GroupDAO();

            membs.forEach(m -> {
                if(UserDAO.getUser(m.getUserID()) == null)
                    throw new Error("Erroneous Membership data: undefined user");
                else if(groupDAO.getGroup(m.getGroupID()) == null)
                    throw new Error("Erroneous Membership data: undefined group");
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        MembershipDAO membershipDAO = new MembershipDAO();
//
//        System.out.println(membershipDAO.getUserMemberships(1).toString());
//        System.out.println(membershipDAO.getGroupMemberships(1).toString());
//    }
}