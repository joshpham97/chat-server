import server.chat.daoimpl.UserFileDaoImpl;
import server.chat.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "AuthServlet")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
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

        UserFileDaoImpl userDao = new UserFileDaoImpl();
        HttpSession session = request.getSession();
        String destPage = "login.jsp";

        try {
            User user = userDao.getUserByUsername(uname);

            String pass = user.getPassword();
            String username = user.getUsername();
            int userID = user.getUserID();

            if (user != null && generatedPassword.equals(pass)) {
                String errMsg = (String)session.getAttribute("errorMessage");
                if(errMsg != null)
                {
                    session.removeAttribute("errorMessage");
                }

                session.setAttribute("userID", userID);
                session.setAttribute("username", username);
                destPage = "index.jsp";
            } else {
                String message = "Invalid username/password";
                System.out.println("Invalid Username or Password");
                session.setAttribute("errorMessage", message);
                request.setAttribute("message", message);
            }

            response.sendRedirect(destPage);
            //RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            //dispatcher.forward(request, response);
        }catch (Exception ex) {
            session.setAttribute("errorMessage", "No user found");
            response.sendRedirect(destPage);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("userID");
            session.removeAttribute("username");

            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
