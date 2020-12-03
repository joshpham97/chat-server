package server.database.model;

public class Group {
    private int groupID;
    private String groupName;
    private Integer parent; // Integer instead of int to simulate a nullable field

    // Needed for DAO implementation
    public Group() {

    }

    public Group(int groupID, String groupName, int parent) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.parent = parent;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getParent() { return parent; }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return groupID + " : " + groupName + " : " + parent;
    }
}
