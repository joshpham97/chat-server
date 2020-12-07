import app.PostManager;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.BeforeClass;
import server.database.model.Post;
import java.sql.SQLException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

public class PostManagerTest {

    private static PostManager postManager;
    private static HttpSession session;

    @BeforeClass
    public static void init() {
        session =  new HttpSession() {
            private Map<String, Object> sessionList = new HashMap<>();

            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int interval) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public HttpSessionContext getSessionContext() {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return sessionList.get(name);
            }

            @Override
            public Object getValue(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String[] getValueNames() {
                return new String[0];
            }

            @Override
            public void setAttribute(String name, Object value) {
                sessionList.put(name, value);
            }

            @Override
            public void putValue(String name, Object value) {

            }

            @Override
            public void removeAttribute(String name) {
                sessionList.remove(name);
            }

            @Override
            public void removeValue(String name) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        };
    }

    @Test
    void createPost() throws InterruptedException , SQLException, IOException{
        //Arrange
        String username = "john";
        String title = "post test";
        String message = "test for post";
        String group = "public";

        //Action
        Integer postId = PostManager.createPost(username, title, message, group);
        Post post = PostManager.getPostById(postId);

        //Assertion
        assertEquals(post.getPostID(), PostManager.getPostById(postId));
        assertEquals(post.getUsername(), username);
        assertEquals(post.getTitle(), title);
        assertEquals(post.getMessage(), message);
        assertEquals(post.getPermissionGroup(), group);

    }


    @Test
    void getPostById() {
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
    void updatePost() {
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
    void deletePost() {
        //Arrange
        String username = "john";
        String title = "test post title";
        String message = "post message test";
        String titleUpdate = "post update test";
        String messageUpdate = "update est for post";
        String group = "public";

        Integer postId = PostManager.createPost(username, title, message, group);

        //Action
        boolean success = PostManager.deletePost(postId,session);
        Post updatePost = PostManager.getPostById(postId);

        //Assertion
        assertTrue(success);

    }
}