package app;

import server.database.dao.GroupDAO;
import server.database.dao.MembershipDAO;
import server.database.model.Group;
import server.database.model.Membership;

import java.util.ArrayList;

// Handles calling MembershipDAO and GroupDAO
public class GroupManager {
    private static GroupDAO groupDAO = new GroupDAO();
    private static MembershipDAO membershipDAO = new MembershipDAO();

    public static String getGroupName(int groupID) {
        Group group = groupDAO.getGroup(groupID);

        return group == null ? null : group.getGroupName();
    }

    // Gets the group names for a user
    public static ArrayList<String> getUserGroups(int userID) {
        ArrayList<String> groups = new ArrayList<>();

        ArrayList<Membership> memberships = membershipDAO.getUserMemberships(userID);
        for (Membership m: memberships) {
            Group group = groupDAO.getGroup(m.getGroupID());
            groups.add(group.getGroupName());
        }

        return groups;
    }

//    public static void main(String[] args) {
//        System.out.println(GroupManager.getGroupName(1));
//        System.out.println(GroupManager.getUserGroups(1));
//    }
}
