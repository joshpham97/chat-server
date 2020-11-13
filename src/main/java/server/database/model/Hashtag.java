package server.dabatase.model;

import java.io.Serializable;

public class Hashtag implements Serializable {
    private int id;
    private String hashtag;

    public Hashtag(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
