import java.time.LocalDateTime;

public class Message implements java.io.Serializable {
    private String username;
    private String message;
    private LocalDateTime date;

    public Message(String username, String message, LocalDateTime date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }
  
    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        date = LocalDateTime.now();
    }

    public Message(String message) {
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
        return this.date + "::" + this.username + "::" + this.message;
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
