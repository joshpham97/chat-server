import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Post[] posts = */String posts = PostManager.getRecentPosts();
        PrintWriter responseWriter = response.getWriter();

        try {
            responseWriter.append(posts);
        } catch(Exception e) {

        }

        responseWriter.close();
//        request.setAttribute("posts", posts);

//        RequestDispatcher dispatcher = request.getRequestDispatcher("posts.jsp");
//        try {
//            dispatcher.forward(request, response);
//        } catch(Exception e) {
//
//        }
    }
}
