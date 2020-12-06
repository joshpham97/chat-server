import org.junit.Test;
import org.junit.*;
import server.database.dao.GroupDAO;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class GroupDAOTest {
    @Test(expected =  Error.class)
    public void checkData() throws InterruptedException, SQLException, IOException {
        // Arrange
        // TODO: make new groups file with errors

        // Action
        GroupDAO groupDao = new GroupDAO();
    }
}
