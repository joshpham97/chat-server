package servlet;

import app.PostManager;
import server.database.model.Post;
import server.database.model.PostList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(name = "servlet.PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String uname = (String)request.getSession(false).getAttribute("username");
        String message = request.getParameter("message");
        String title = request.getParameter("title");
        String strPostID = request.getParameter("postId");
        String group = request.getParameter("group");

        //Post post = null;
        if(action.equals("post"))
        {
            Integer postId = PostManager.createPost(uname, title, message, group);
            if (postId != null)
                response.sendRedirect(String.format("PostEditServlet?postId=%d", postId));
            else
                response.sendRedirect("index.jsp?success=false");
        }
        else if(action.equals("update"))
        {
            int postID = Integer.parseInt(strPostID);
            boolean success = PostManager.updatePost(postID, title, message, group);
            if (success)
                response.sendRedirect(String.format("PostEditServlet?postId=%d", postID));
            else
                response.sendRedirect("index.jsp?success=false");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        //PrintWriter responseWriter = response.getWriter();

        if(action!=null && !action.isEmpty()) {
            String paramName = "postID";
            String strPostID = request.getParameter(paramName);
            int postID = Integer.parseInt(strPostID);
            boolean isSuccess;
            if(action.equals("delete"))
            {
                /** MODIFIED BY STEFAN SO ONLY AUTHORIZED USERS CAN DELETE POSTS */
                HttpSession session=request.getSession(false);

                isSuccess = PostManager.deletePost(postID, session);
                response.sendRedirect(String.format("index.jsp?postId=%d&success=%b", postID, isSuccess));

            }
            else if(action.equals("update"))
            {
                response.sendRedirect(String.format("edit.jsp?postId=%d", postID));
            }
        }
        else
        {
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

                HttpSession session = request.getSession();
                ArrayList<String> groups = (ArrayList<String>) session.getAttribute("impliedMemberships");

                PostList posts = PostManager.searchPostsWithPagination(username, from, to, hashtags, currentPage, groups);
                int pages = PostManager.getNumberOfPages(username, from, to, hashtags, groups);

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
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}