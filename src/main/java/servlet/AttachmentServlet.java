package servlet;

import app.AttachmentManager;
import app.PostManager;
import server.database.model.Attachment;
import server.database.model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "servlet.AttachmentServlet")
@MultipartConfig
public class AttachmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String strMessage = request.getParameter("message");
        String action = request.getParameter("action");

        if (action != null && !action.isEmpty()) {
            if(action.equals("post")){
                uploadAttachment(request, response);
            }else if(action.equals("put")){
                updateAttachment(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && !action.isEmpty()){
            if (action.equals("get")){
                getAttachment(request, response);
            }else if(action.equals("delete")){
                deleteAttachment(request, response);
            }
        }
    }

    private void uploadAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String strPostId = request.getParameter("postId");
        Part filePart = request.getPart("attachment");

        if (strPostId != null && !strPostId.isEmpty() && filePart != null){
            int postId = Integer.parseInt(strPostId);

            boolean success = AttachmentManager.insertAttachment(filePart, postId);
            response.sendRedirect(String.format("PostEditServlet?postId=%d&success=%b", postId, success));
        }
    }

    private void getAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String strAttachmentId = request.getParameter("attachmentId");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Attachment att = AttachmentManager.getAttachment(Integer.parseInt(strAttachmentId));
        Post post = PostManager.getPostByAttId(att.getAttachmentId());

        if(username == null || !username.equals(post.getUsername())) {
            response.sendRedirect("index.jsp");
        }
        else {
            OutputStream responseWriter = response.getOutputStream();
            if (strAttachmentId != null && !strAttachmentId.isEmpty()) {
                int attachmentId = Integer.parseInt(strAttachmentId);

                try {
                    Attachment attachment = AttachmentManager.getAttachment(attachmentId);
                    InputStream fileInputStream = attachment.getFileBlob().getBinaryStream();
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = fileInputStream.read(buffer)) > 0) {
                        responseWriter.write(buffer, 0, count);
                    }
                    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", attachment.getFilename()));
                    response.setContentType(attachment.getMediaType());
                    response.setDateHeader("Expires", 0);
                    responseWriter.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void updateAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String strPostId = request.getParameter("postId");
        String strAttachmentId = request.getParameter("attachmentId");
        Part filePart = request.getPart("attachment");

        if(strPostId != null && !strPostId.isEmpty() &&
           strAttachmentId != null && !strAttachmentId.isEmpty() &&
           filePart != null){
            int postId = Integer.parseInt(strPostId);
            int attachmentId = Integer.parseInt(strAttachmentId);

            boolean success = AttachmentManager.updateAttachment(filePart, postId, attachmentId);
            response.sendRedirect(String.format("PostEditServlet?postId=%d&success=%b", postId, success));
        }

    }

    private void deleteAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String strPostId = request.getParameter("postId");
        String strAttachmentId = request.getParameter("attachmentId");

        if (strPostId != null && !strPostId.isEmpty() &&
            strAttachmentId != null && !strAttachmentId.isEmpty()){
            int postId = Integer.parseInt(strPostId);
            int attachmentId = Integer.parseInt(strAttachmentId);

            boolean isSuccess = AttachmentManager.deleteAttachment(postId, attachmentId);
            response.sendRedirect(String.format("PostEditServlet?postId=%d&success=%b", postId, isSuccess));
        }
    }
}
