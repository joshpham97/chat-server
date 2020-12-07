import app.UserManager;
import app.UserManagerFactory;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private static HttpSession mock(Class<HttpSession> httpSessionClass) {
        return new HttpSession() {
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
    public void loginSuccess() throws InterruptedException {
        // Arrange
        UserManager userManager = UserManagerFactory.getUserManager();
        HttpSession session = mock(HttpSession.class);
        String username = "john";
        String password = "password1";

        // Action
        boolean success = userManager.login(username, password, session);
        Thread.sleep(1000);

        // Assertion
        assertTrue(success);
        assertEquals(username, session.getAttribute("username"));
    }

    @Test
    public void loginFail() throws InterruptedException {
        // Arrange
        UserManager userManager = UserManagerFactory.getUserManager();
        HttpSession session = mock(HttpSession.class);
        String username = "john";
        String password = "incorrect_password";

        // Action
        boolean success = userManager.login(username, password, session);
        Thread.sleep(1000);

        // Assertion
        assertFalse(success);
        assertNull(session.getAttribute("username"));
    }

    @Test
    public void encryptPassword() {
        // Arrange
        UserManager userManager = UserManagerFactory.getUserManager();
        String password = "password1";
        String validHashed = "7c6a180b36896a0a8c02787eeafb0e4c";

        // Action
        String hashedAttempt = userManager.encryptPassword(password);

        // Assertion
        assertEquals(validHashed, hashedAttempt);
    }
}
