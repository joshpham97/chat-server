package server.chat.model;

public class User {
    private int userID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Needed for DAO implementation
    public User() {

    }

    public User(int userID, String username, String firstName, String lastName, String email, String password) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // For testing
    @Override
    public String toString() {
        return userID + " : " + username + " : " + firstName + " : " + lastName + " : " + email;
    }
}
