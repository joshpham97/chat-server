package server.database.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements java.io.Serializable {
    private int postID;
    private String username;
    private String title;
    private LocalDateTime datePosted;
    private LocalDateTime dateModified;
    private String message;
    private Integer attID; // Integer instead of int to simulate nullable fields
    private String datePostedStr; // Helps with displaying date on frontend
    private String dateModifiedStr;

    // Needed for DAO implementation
    public Post() {

    }

    // NEEDED FOR TESTING WITHOUT DAO
    public Post(int postID, String username, String title, String message, LocalDateTime datePosted, LocalDateTime dateModified, Integer attID) {
        this.postID = postID;
        this.username = username;
        this.title = title;
        this.message = message;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.attID = attID;
        this.datePostedStr = formatDate(datePosted);
        this.dateModifiedStr = formatDate(dateModified);
    }

    public Post(int postID, String username, String title, String message, Integer attID) {
        this.postID = postID;
        this.username = username;
        this.title = title;
        datePosted = LocalDateTime.now();
        dateModified = LocalDateTime.now();
        this.message = message;
        this.attID = attID;
        this.datePostedStr = formatDate(datePosted);
        this.dateModifiedStr = formatDate(dateModified);
    }

    // KEEPING FOR PREVIOUS ASSIGNMENT SERVLET
    public Post(String username, String message) {
        this.username = username;
        this.message = message;
        datePosted = LocalDateTime.now();
    }

    // KEEPING FOR PREVIOUS ASSIGNMENT SERVLET
    public Post(String message) {
        this("Anonymous", message);
    }

    public int getPostID() { return postID; }

    public String getUsername() {
        return username;
    }

    public String getTitle() { return title; }

    public LocalDateTime getDatePosted() { return datePosted; }

    public LocalDateTime getDateModified() { return dateModified; }

    public String getDatePostedStr() { return datePostedStr; }

    public String getDateModifiedStr() { return dateModifiedStr; }

    public String getMessage() {
        return message;
    }

    public Integer getAttID() { return attID; }

    public void setPostID(int postID) { this.postID = postID; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) { this.title = title; }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
        this.datePostedStr = formatDate(datePosted);
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
        this.dateModifiedStr = formatDate(dateModified);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAttID(Integer attID) { this.attID = attID; }

    private String formatDate(LocalDateTime date) {
        return date.getMonth().toString().toLowerCase() + " " + date.getDayOfMonth() + ", " + date.getYear();
    }
}