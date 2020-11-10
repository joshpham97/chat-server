import com.google.gson.Gson;
import org.json.simple.JSONObject;
import server.chat.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter responseWriter = response.getWriter();

        String username = request.getParameter("username");
        String strFrom = request.getParameter("from");
        String strTo = request.getParameter("to");
        String strHashtags = request.getParameter("hashtags");
        String strCurrentPage = request.getParameter("page");

        try {
            // Parse request parameters
            LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDate.parse(strFrom).atStartOfDay();
            LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDate.parse(strTo).plusDays(1).atStartOfDay();
            List<String> hashtags = (strHashtags == null) ? null : Arrays.asList(strHashtags.split(" "));
            int currentPage = (strCurrentPage == null) ? 1 : Integer.parseInt(strCurrentPage);

            ArrayList<Post> posts = PostManager.searchPostsWithPagination(username, from, to, hashtags, currentPage);
            int pages = PostManager.getNumberOfPages(username, from, to, hashtags);

            JSONObject jo = new JSONObject();
            Gson gson = new Gson();
            jo.put("currentPage", currentPage);
            jo.put("pages", pages);
            jo.put("posts", gson.toJson(posts));
            responseWriter.append(gson.toJson(jo));
        } catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.append("Invalid request");
        }

        responseWriter.close();
    }
}
