package server.chat.model;

import server.chat.Post;

import java.util.ArrayList;

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
