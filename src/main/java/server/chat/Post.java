package server.chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements java.io.Serializable {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String username;
    private String message;
    private LocalDateTime date;
  
    public Post(String username, String message) {
        this.username = username;
        this.message = message;
        date = LocalDateTime.now();
    }

    public Post(String message) {
        this("Anonymous", message);
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setContent(String message) {
        this.message = message;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

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
