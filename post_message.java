import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "post_message")
public class post_message extends HttpServlet {
    ArrayList<Message> msg = new ArrayList<Message>();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String username = request.getParameter("username");
        String message = request.getParameter("message");
        request.getSession().setAttribute("username", username);

        Date date = new Date();

        msg.add(new Message(username, message, date));

        System.out.println(msg.toString());

        RequestDispatcher req = request.getRequestDispatcher("message.jsp");
        req.forward(request, response);
    }

}