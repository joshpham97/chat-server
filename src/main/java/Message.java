import java.util.Date;

public class Message implements java.io.Serializable {
    private String username;
    private String message;
    private Date date;

    public Message(String username, String message, Date date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }

    //Constructor that we're most likely to use
    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        date = new Date();
    }

    public Message(String message, Date date) {
        this("Anonymous", message, date);
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setContent(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.username + ": " + this.message;
    }
}
