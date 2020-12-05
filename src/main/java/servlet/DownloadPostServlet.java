package servlet;

import app.PostManager;
import app.PostTransformer;
import server.database.model.PostList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DownloadPostServlet")
public class DownloadPostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            PostList posts = PostManager.searchPosts(username, from, to, hashtags);
            String xmlPosts = PostTransformer.toXMLDocument(posts);

            PrintWriter responseWriter = response.getWriter();
            System.out.println(xmlPosts);
            responseWriter.append(xmlPosts);

            response.setHeader("Content-Disposition", "attachment; filename=\"posts.xml\"");
            response.setContentType("text/plain");
            response.setDateHeader("Expires", 0);

            responseWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}