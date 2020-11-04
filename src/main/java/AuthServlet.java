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

        HttpSession session = request.getSession();
        String destPage = "login.jsp";
        UserManager manager = new UserManager();

        try {
                if(manager.login(uname, password, session))
                {
                    destPage = "index.jsp";
                }
                else
                {
                    destPage = "login.jsp";
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
