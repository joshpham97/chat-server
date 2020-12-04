package datamapper;

import app.UserManager;
import server.database.dao.UserDAO;
import server.database.model.User;

import javax.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManagerImpl implements UserManager {
    public  boolean login(String username, String password, HttpSession session)
    {
        boolean result = false;

        String generatedPassword = encryptPassword(password);

        UserDAO userDao = new UserDAO();

        User user = userDao.getUserByUsername(username);

        String pass = user.getPassword();
        //String username = user.getUsername();
        int userID = user.getUserID();

        if (user != null && generatedPassword.equals(pass)) {
            String errMsg = (String)session.getAttribute("errorMessage");
            if(errMsg != null)
            {
                session.removeAttribute("errorMessage");
            }

            session.setAttribute("userID", userID);
            session.setAttribute("username", username);
            result = true;

        } else {
            String message = "Invalid username/password";
            session.setAttribute("errorMessage", message);
            result = false;
        }
        return result;
    }

    private static String encryptPassword(String password)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
