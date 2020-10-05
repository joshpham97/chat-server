import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "PostMessageSevlet")
public class PostMessageSevlet extends HttpServlet {
    ArrayList<Message> msg = new ArrayList<Message>();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String message = request.getParameter("message");
        request.getSession().setAttribute("username", username);

        Date date = new Date();

        msg.add(new Message(username, message, date));

        System.out.println(msg.toString());

        RequestDispatcher req = request.getRequestDispatcher("index.jsp");
        req.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
