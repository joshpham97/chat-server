package server.database.model;

public class Membership {
    private int userID;
    private int groupID;

    // Needed for DAO implementation
    public Membership() {

    }

    public Membership(int userID, int groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }

    public int getUserID() { return userID; }

    public int getGroupID() { return groupID; }

    public void setUserID(int userID) { this.userID = userID; }

    public void setGroupID(int groupID) { this.groupID = groupID; }

    @Override
    public String toString() {
        return userID + " : " + groupID;
    }
}
