import org.junit.Test;
import org.junit.*;
import org.junit.rules.ExpectedException;
import server.database.dao.MembershipDAO;

import java.io.*;

public class MembershipDAOTest {
    private static String testMemberships = "./src/test/data/testMemberships.json";
    private static OutputStream outputStream;

    @BeforeClass
    @AfterClass
    public static void clearFile() throws IOException {
        outputStream = new FileOutputStream(testMemberships);
        outputStream.write("".getBytes());
        outputStream.close();
    }

    @Before
    public void getFile() throws IOException {
        outputStream = new FileOutputStream(testMemberships);
    }

    @After
    public void closeFile() throws IOException {
        outputStream.close();
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void valid() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"userID\": \"1\",\n" +
                "    \"groupID\": \"1\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Action
        new MembershipDAO(testMemberships);
    }

    @Test
    public void undefinedUser() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"userID\": \"99999\",\n" +
                "    \"groupID\": \"1\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("undefined user");

        // Action
        new MembershipDAO(testMemberships);
    }

    @Test
    public void undefinedGroup() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"userID\": \"1\",\n" +
                "    \"groupID\": \"99999\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("undefined group");

        // Action
        new MembershipDAO(testMemberships);
    }

    @Test
    public void invalidField() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"userID\": \"ONE\",\n" +
                "    \"groupID\": \"1\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("invalid field");

        // Action
        new MembershipDAO(testMemberships);
    }
}
