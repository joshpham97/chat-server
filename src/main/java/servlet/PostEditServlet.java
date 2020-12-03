package servlet;

import app.PostManager;
import app.AttachmentManager;
import server.database.model.Attachment;
import server.database.model.Post;

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
            /** Stefan Code */
            String postUser = post.getUsername();

            /** End stefan code */

            if (post != null){
                Attachment attachment = new Attachment();
                if(post.getAttID() != null)
                    attachment = AttachmentManager.getAttachment(post.getAttID());

                request.setAttribute("post", post);
                request.setAttribute("attachment", attachment);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/edit.jsp");
                rd.forward(request, response);
            }else{
                response.sendRedirect("index.jsp?success=false");
            }

        }
    }
}
