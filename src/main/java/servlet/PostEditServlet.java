package servlet;

import Business.PostManager;
import server.chat.AttachmentManager;
import server.chat.daoimpl.PostDAO;
import server.chat.model.Attachment;
import server.chat.model.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PostEditServlet")
public class PostEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strPostId = request.getParameter("postId");
        if (strPostId != null && !strPostId.isEmpty()){
            int postId = Integer.parseInt(strPostId);
            Post post = PostManager.getPostById(postId);

            Attachment attachment = new Attachment();
            if(post.getAttID() != null)
                attachment = AttachmentManager.getAttachment(post.getAttID());

            request.setAttribute("post", post);
            request.setAttribute("attachment", attachment);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/post.jsp");
            rd.forward(request, response);
        }
    }
}
