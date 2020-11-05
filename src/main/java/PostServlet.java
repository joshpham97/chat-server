import com.google.gson.Gson;
import server.chat.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<Post> posts = PostManager.getRecentPosts();
        PrintWriter responseWriter = response.getWriter();

        try {
            Gson gson = new Gson();
            responseWriter.append(gson.toJson(posts));
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
