import app.PostManager;
import org.junit.Test;


import server.database.model.Post;
import server.database.model.PostList;

import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

public class PostManagerTest {

    @Test
    public void createPost() {
        //Arrange
        String username = "john";
        String title = "post test";
        String message = "test for post";
        String group = "public";

        //Action
        Integer postId = PostManager.createPost(username, title, message, group);
        Post post = PostManager.getPostById(postId);

        //Assertion
        assertEquals(post.getPostID(), postId);
        assertEquals(post.getUsername(), username);
        assertEquals(post.getTitle(), title);
        assertEquals(post.getMessage(), message);
        assertEquals(post.getPermissionGroup(), group);

    }


    @Test
    public void getPostById() {
        //Arrange
        String username = "john";
        String title = "post test";
        String message = "test for post";
        String group = "public";

        Integer postId = PostManager.createPost(username, title, message, group);

        //Action
        Post success = PostManager.getPostById(postId);

        //Assertion
        assertEquals(success.getPostID(), postId);
        assertEquals(success.getUsername(), username);
        assertEquals(success.getTitle(), title);
        assertEquals(success.getMessage(), message);
        assertEquals(success.getPermissionGroup(), group);
    }

    @Test
    public void updatePost() {
        //Arrange
        String username = "john";
        String title = "test post title";
        String message = "post message test";
        String titleUpdate = "post update test";
        String messageUpdate = "update est for post";
        String group = "public";

        Integer postId = PostManager.createPost(username, title, message, group);

        //Action
        boolean success = PostManager.updatePost(postId, titleUpdate, messageUpdate, group);
        Post updatePost = PostManager.getPostById(postId);

        //Assertion
        assertTrue(success);
        assertEquals(updatePost.getPostID(), postId);
        assertEquals(updatePost.getTitle(), titleUpdate);
        assertEquals(updatePost.getMessage(), messageUpdate);
        assertEquals(updatePost.getPermissionGroup(), group);
        assertEquals(updatePost.getDateModified(),PostManager.getPostById(postId).getDateModified());

    }

    @Test
    public void deletePost() {
        //Arrange
        String username = "john";
        String title = "test post title";
        String message = "post message test";
        String group = "public";
        boolean isAdmin = true;

        Integer postId = PostManager.createPost(username, title, message, group);

        //Action
        boolean success = PostManager.deletePost(postId,username,isAdmin);
        Post updatePost = PostManager.getPostById(postId);

        //Assertion
        assertTrue(success);
        assertNull(updatePost);

    }

    @Test
    public void searchPost() throws InterruptedException
    {
        //Arrange
        LocalDateTime from = LocalDateTime.now();
        String username = "john";
        String title = "post test";
        String message = "test for #post";
        ArrayList<String> group = new ArrayList<>();
        group.add("public");
        Integer postId = PostManager.createPost(username, title, message, group.get(0));
        PostManager.getPostById(postId);
        List<String> hashtagList = Arrays.asList("post");

        //Action
        PostList pl = PostManager.searchPosts(username, from, null, hashtagList, group);
        ArrayList<Post> postList = pl.getPosts();

        //Assertion
        for (Post p: postList) {
            assertTrue(p.getUsername().contains(username));
            assertTrue(group.contains(p.getPermissionGroup()));
        }
    }
}