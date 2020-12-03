package server.database.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.database.model.Membership;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MembershipDAO {
    private static final String MEMBERSHIPS_FILE = "memberships.json";

    // Gets all memberships for a user
    public static ArrayList<Membership> getUserMemberships(int userID) {
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
    public static ArrayList<Membership> getGroupMemberships(int groupID) {
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
    private static Stream<Membership> readMembershipsFile() throws Exception {
        InputStream inputStream = MembershipDAO.class.getClassLoader().getResourceAsStream(MEMBERSHIPS_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONArray ja = (JSONArray) obj; // Parse object to JSONArray

        return ja.stream()
                .map(o -> {
                    JSONObject jo = (JSONObject) o;
                    return jsonObjectToMembership(jo); // Convert to membership
                });
    }

    // Convert JSONObject to Membership
    private static Membership jsonObjectToMembership(JSONObject jo) {
        Membership membership = new Membership();

        membership.setUserID(Integer.parseInt(jo.get("userID").toString()));
        membership.setGroupID(Integer.parseInt(jo.get("groupID").toString()));

        return membership;
    }

//    public static void main(String[] args) {
//        System.out.println(MembershipDAO.getUserMemberships(1).toString());
//        System.out.println(MembershipDAO.getGroupMemberships(1).toString());
//    }
}
