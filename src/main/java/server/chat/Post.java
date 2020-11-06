package server.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements java.io.Serializable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int postID;
    private String username;
    private String title;
    private String message;
    private LocalDateTime date;
    private LocalDateTime lastModified;

    // NEEDED FOR TESTING WITHOUT DAO
    public Post(int postID, String username, String title, String message, LocalDateTime date, LocalDateTime lastModified) {
        this.postID = postID;
        this.username = username;
        this.title = title;
        this.message = message;
        this.date = date;
        this.lastModified = lastModified;
    }

    public Post(int postID, String username, String title, String message) {
        this.postID = postID;
        this.username = username;
        this.title = title;
        this.message = message;
        date = LocalDateTime.now();
        lastModified = LocalDateTime.now();
    }

    public Post(String username, String message) {
        this.username = username;
        this.message = message;
        date = LocalDateTime.now();
    }

    public Post(String message) {
        this("Anonymous", message);
    }

    public int getPostID() { return postID; }

    public String getUsername() {
        return username;
    }

    public String getTitle() { return title; }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getLastModified() { return lastModified; }

    public void setPostID(int postID) { this.postID = postID; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) { this.title = title; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLastModified(LocalDateTime lastModified) { this.lastModified = lastModified;}

    @Override
    public String toString() {
        String strDate = date.format(FORMATTER);
        return strDate + " :: " + this.username + " :: " + this.message;
    }

    public String toXML(){
        StringBuilder messageXML = new StringBuilder();
        return  " <Message>\n" +
                "  <Date>" + this.getDate() + "</Date>\n" +
                "  <Username>" + this.getUsername() + "</Username>\n" +
                "  <Content>" + this.getMessage() + "</Content>\n" +
                " </Message>\n";
    }
}
