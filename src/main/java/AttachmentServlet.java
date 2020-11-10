import server.chat.AttachmentManager;
import server.chat.daoimpl.AttachmentDAO;
import server.chat.model.Attachment;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet(name = "AttachmentServlet")
@MultipartConfig
public class AttachmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String strMessage = request.getParameter("message");
        String action = request.getParameter("action");
        String strPostId = request.getParameter("postId");
        String strAttachmentId = request.getParameter("attachmentId");
        Part filePart = request.getPart("attachment");

        if (filePart != null && action != null && strPostId != null) {
            int postId = Integer.parseInt(strPostId);

            if(action.equals("post")){
                boolean success = AttachmentManager.insertAttachment(filePart, postId);
                response.sendRedirect(String.format("post.jsp?postId=%d&success=%b", postId, success));
            }else if(action.equals("put") && strAttachmentId != null){
                int attachmentId = Integer.parseInt(strAttachmentId);
                boolean success = AttachmentManager.updateAttachment(filePart, attachmentId);
                response.sendRedirect(String.format("post.jsp?postId=%d&success=%b", postId, success));
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String strPostId = request.getParameter("postId");
        String strAttachmentId = request.getParameter("attachmentId");

        if (action != null){
            if (action.equals("get") && strPostId != null){
                int attachmentId = Integer.parseInt(strAttachmentId);
                getAttachment(attachmentId, response);
            }else if(action.equals("delete")  && strAttachmentId != null){
                int postId = Integer.parseInt(strPostId);
                boolean isSuccess = AttachmentManager.deleteAttachment(postId);
                response.sendRedirect(String.format("post.jsp?postId=%d&success=%b", postId, isSuccess));
            }
        }
    }

    private void getAttachment(int attachmentId, HttpServletResponse response) throws IOException {
        Attachment attachment = AttachmentManager.getAttachment(attachmentId);
        OutputStream responseWriter = response.getOutputStream();

        try {
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
