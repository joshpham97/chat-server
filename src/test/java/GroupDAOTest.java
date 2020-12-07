import org.junit.Test;
import org.junit.*;
import org.junit.rules.ExpectedException;
import server.database.dao.GroupDAO;

import java.io.*;

public class GroupDAOTest {
    private static String testGroups = "./src/test/data/testGroups.json";
    private static OutputStream outputStream;

    @BeforeClass
    @AfterClass
    public static void clearFile() throws IOException {
        outputStream = new FileOutputStream(testGroups);
        outputStream.write("".getBytes());
        outputStream.close();
    }

    @Before
    public void getFile() throws IOException {
       outputStream = new FileOutputStream(testGroups);
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
                "    \"groupID\": \"1\",\n" +
                "    \"groupName\": \"top\",\n" +
                "    \"parent\": null\n" +
                "  }, {\n" +
                "    \"groupID\": \"2\",\n" +
                "    \"groupName\": \"middle\",\n" +
                "    \"parent\": \"1\"\n" +
                "  }, {\n" +
                "    \"groupID\": \"3\",\n" +
                "    \"groupName\": \"bottom\",\n" +
                "    \"parent\": \"2\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Action
        new GroupDAO(testGroups);
    }

    @Test
    public void undefinedParent() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"groupID\": \"1\",\n" +
                "    \"groupName\": \"top\",\n" +
                "    \"parent\": null\n" +
                "  }, {\n" +
                "    \"groupID\": \"2\",\n" +
                "    \"groupName\": \"bottom\",\n" +
                "    \"parent\": \"3\"\n" +
                "  }\n" +
                "]";
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("undefined parent");

        // Action
        new GroupDAO(testGroups);
    }

    @Test
    public void circularRelationships() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"groupID\": \"1\",\n" +
                "    \"groupName\": \"top\",\n" +
                "    \"parent\": \"3\"\n" +
                "  }, {\n" +
                "    \"groupID\": \"2\",\n" +
                "    \"groupName\": \"middle\",\n" +
                "    \"parent\": \"1\"\n" +
                "  }, {\n" +
                "    \"groupID\": \"3\",\n" +
                "    \"groupName\": \"bottom\",\n" +
                "    \"parent\": \"2\"\n" +
                "  }\n" +
                "]";
        outputStream = new FileOutputStream(testGroups);
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("invalid parent-child definition");

        // Action
        new GroupDAO(testGroups);
    }

    @Test
    public void invalidField() throws InterruptedException, IOException {
        // Arrange
        String content =
                "[\n" +
                "  {\n" +
                "    \"groupID\": \"ONE\",\n" +
                "    \"groupName\": \"top\",\n" +
                "    \"parent\": null\n" +
                "  }\n" +
                "]";
        outputStream = new FileOutputStream(testGroups);
        outputStream.write(content.getBytes());
        Thread.sleep(1000);

        // Assertion
        expectedEx.expect(Error.class);
        expectedEx.expectMessage("invalid field");

        // Action
        new GroupDAO(testGroups);
    }
}
