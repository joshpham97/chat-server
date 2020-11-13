package servlet;

import app.PostManager;
import com.google.gson.Gson;
import server.dabatase.model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "servlet.PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String uname = (String)request.getSession(false).getAttribute("username");
        String message = request.getParameter("message");
        String title = request.getParameter("title");
        String strPostID = request.getParameter("postId");

        Post post = null;
        if(action.equals("post"))
        {
            Integer postId = PostManager.createPost(uname, title, message);
            if (postId != null)
                response.sendRedirect(String.format("PostEditServlet?postId=%d", postId));
            else
                response.sendRedirect("index.jsp?success=false");
        }
        else if(action.equals("update"))
        {
            int postID = Integer.parseInt(strPostID);
            boolean success = PostManager.updatePost(postID,uname, title, message);
            if (success)
                response.sendRedirect(String.format("PostEditServlet?postId=%d", postID));
            else
                response.sendRedirect("index.jsp?success=false");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        PrintWriter responseWriter = response.getWriter();

        if(action!=null && !action.isEmpty()) {
            String paramName = "postID";
            String strPostID = request.getParameter(paramName);
            int postID = Integer.parseInt(strPostID);
            if(action.equals("delete"))
            {
                boolean isSuccess = PostManager.deletePost(postID);
                response.sendRedirect(String.format("index.jsp?postId=%d&success=%b", postID, isSuccess));
            }
            else if(action.equals("update"))
            {
                response.sendRedirect(String.format("edit.jsp?postId=%d", postID));
            }
        }
        else
        {
            try {
                ArrayList<Post> posts = PostManager.getRecentPosts();

                Gson gson = new Gson();
                responseWriter.append(gson.toJson(posts));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseWriter.append("Invalid request");
            }
            responseWriter.close();
        }


    }
}