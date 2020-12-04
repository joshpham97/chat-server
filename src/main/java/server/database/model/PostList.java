package server.database.model;

import server.database.model.Post;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(ArrayList.class)
public class PostList implements java.io.Serializable {
    private ArrayList<Post> posts;

    public PostList() {
        posts = new ArrayList<>();
    }

    public PostList(ArrayList<Post> posts) { this.posts = posts;}

    public Post addPost(Post p) {
        posts.add(p);

        return p;
    }

    public int getSize() {
        return posts.size();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "SIZE: " + getSize();
    }
}
