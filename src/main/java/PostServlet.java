import com.google.gson.Gson;
import org.json.simple.JSONObject;
import server.chat.Post;
import server.chat.model.PostList;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String strFrom = request.getParameter("from");
        String strTo = request.getParameter("to");
        String strHashtags = request.getParameter("hashtags");
        String strCurrentPage = request.getParameter("page");

        try {
            // Parse request parameters
            LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDate.parse(strFrom).atStartOfDay();
            LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDate.parse(strTo).plusDays(1).atStartOfDay();
            List<String> hashtags = (strHashtags == null || strHashtags.isEmpty()) ? null : Arrays.asList(strHashtags.split(" "));
            int currentPage = (strCurrentPage == null || strCurrentPage.isEmpty()) ? 1 : Integer.parseInt(strCurrentPage);

            PostList posts = PostManager.searchPostsWithPagination(username, from, to, hashtags, currentPage);
            int pages = PostManager.getNumberOfPages(username, from, to, hashtags);

            String queryString = "";
            for (Map.Entry<String, String[]> p: request.getParameterMap().entrySet()) {
                if(!p.getValue()[0].isEmpty() && !p.getKey().equals("page")) // Don't consider page param
                    queryString += "&" + p.getKey() + "=" + p.getValue()[0];
            }

            request.setAttribute("posts", posts);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pages", pages);
            request.setAttribute("queryString", queryString);
        } catch(Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
