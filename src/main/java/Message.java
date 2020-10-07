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
