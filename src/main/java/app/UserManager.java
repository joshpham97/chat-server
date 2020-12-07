package app;

import javax.servlet.http.HttpSession;

public interface UserManager {
    public boolean login(String username, String password, HttpSession session);

    public String encryptPassword(String password);
}
