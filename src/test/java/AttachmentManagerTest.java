import app.AttachmentManager;
import app.PostManager;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;

import server.database.model.Attachment;
import server.database.model.Post;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

public class AttachmentManagerTest {
    private static Part orginalFile;
    private static Part updatedFile;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("In before class");
        orginalFile = new Part() {
            @Override
            public InputStream getInputStream() throws IOException {
                File f = new File("./src/test/data/attachmentTest.txt");
                return new FileInputStream(f);
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSubmittedFileName() {
                return "attachmentTest.txt";
            }

            @Override
            public long getSize() {
                return 500;
            }

            @Override
            public void write(String s) throws IOException {

            }

            @Override
            public void delete() throws IOException {

            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }
        };
        updatedFile = new Part() {
            @Override
            public InputStream getInputStream() throws IOException {
                File f = new File("./src/test/data/updatedFile.txt");
                return new FileInputStream(f);
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getSubmittedFileName() {
                return "updatedFile.txt";
            }

            @Override
            public long getSize() {
                return 500;
            }

            @Override
            public void write(String s) throws IOException {

            }

            @Override
            public void delete() throws IOException {

            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }
        };
    }

    @Test
    public void getAttachment() throws InterruptedException, SQLException, IOException {
//        //Arrange
//        Integer postId = PostManager.createPost("john", "Attachment Test", "This is a test for attachments");
//        boolean success = AttachmentManager.insertAttachment(orginalFile, postId);
//        Post post = PostManager.getPostById(postId);
//        Integer attId = post.getAttID();
//
//        //Action
//        Attachment attachment = AttachmentManager.getAttachment(attId);
//        String fileContent = new String(attachment.getFileBlob().getBinaryStream().readAllBytes(), StandardCharsets.UTF_8);
//
//        //Assertion
//        assertTrue(success);
//        assertNotNull(attachment);
//        assertEquals("attachmentTest.txt", attachment.getFilename());
//        assertEquals(500, attachment.getFilesize());
//        assertEquals("text/plain", attachment.getMediaType());
//        assertEquals("This is a file for testing.", fileContent);
    }

    @Test
    public void insertAttachment() throws InterruptedException {
        //Arrange
        Integer postId = PostManager.createPost("john", "Attachment Test", "This is a test for attachments");
        Thread.sleep(1000);

        //Action
        boolean success = AttachmentManager.insertAttachment(orginalFile, postId);
        Post post = PostManager.getPostById(postId);
        Integer attId = post.getAttID();
        Attachment attachment = AttachmentManager.getAttachment(attId);

        //Assertion
        assertTrue(success);
        assertNotNull(attachment);
        assertEquals((int) attId, attachment.getAttachmentId());
        assertFalse(post.getDatePosted().equals(post.getDateModified()));
    }

    @Test
    public void updateAttachment() throws InterruptedException {
        //Arrange - Create a new post and attach a file
        Integer postId = PostManager.createPost("john", "Attachment Test", "This is a test for attachments");
        AttachmentManager.insertAttachment(orginalFile, postId);
        Post post = PostManager.getPostById(postId);
        Integer attId = post.getAttID();
        Thread.sleep(1000);

        //Action
        //Get the date before updating
        LocalDateTime beforeModifiedDate = post.getDateModified();
        //Update the post
        boolean success = AttachmentManager.updateAttachment(updatedFile, postId, attId);
        Attachment attachment = AttachmentManager.getAttachment(attId);
        String attachmentName = attachment.getFilename();
        post = PostManager.getPostById(postId);
        LocalDateTime afterModifiedDate = post.getDateModified();

        //Assertion
        assertTrue(success);
        assertNotNull(attachment);
        assertTrue(attachmentName.equals("updatedFile.txt"));
        assertFalse(beforeModifiedDate.equals(afterModifiedDate));
    }

    @Test
    public void deleteAttachment() throws InterruptedException {
        //Arrange - Create a new post and attach a file
        Integer postId = PostManager.createPost("john", "Attachment Test", "This is a test for attachments");
        AttachmentManager.insertAttachment(orginalFile, postId);
        Post post = PostManager.getPostById(postId);
        Integer attId = post.getAttID();
        Thread.sleep(1000);

        //Action
        //Get the date before updating
        LocalDateTime beforeModifiedDate = post.getDateModified();
        //Delete the attachment
        boolean success = AttachmentManager.deleteAttachment(postId, attId);
        //Get the post again
        post = PostManager.getPostById(postId);
        LocalDateTime afterModifiedDate = post.getDateModified();
        attId = post.getAttID();

        //Assertion
        assertTrue(success);
        assertNull(attId);
        assertFalse(beforeModifiedDate.equals(afterModifiedDate));
    }
}
