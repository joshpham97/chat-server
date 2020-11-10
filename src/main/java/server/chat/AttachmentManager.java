package server.chat;

import server.chat.daoimpl.AttachmentDAO;
import server.chat.daoimpl.PostDAO;
import server.chat.model.Attachment;

import javax.servlet.http.Part;
import java.io.IOException;

public class AttachmentManager {
    public static boolean insertAttachment(Part filePart, int postId){
        try{
            Attachment attachment = constructAttachmentFromPart(filePart, postId);
            attachment = AttachmentDAO.insert(attachment, filePart.getInputStream());
            if (attachment != null){
                PostDAO.updateAttachmentId(postId, attachment.getAttachmentId());
                return true;
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean deleteAttachment(int postId) {
        return AttachmentDAO.delete(postId);
    }

    public static Attachment getAttachment(int attachmentId){
        return AttachmentDAO.select(attachmentId);
    }

    public static boolean updateAttachment(Part filePart, int attachmentId){
        try{
            Attachment attachment = constructAttachmentFromPart(filePart, attachmentId);
            return AttachmentDAO.update(attachment, filePart.getInputStream());
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public static Attachment constructAttachmentFromPart(Part filePart, int postId){
        Attachment attachment = new Attachment();
        attachment.setFilename(filePart.getName());
        attachment.setFilesize((int)filePart.getSize());
        attachment.setMediaType(filePart.getContentType());

        return attachment;
    }

    public static Attachment constructAttachmentFromPart(Part filePart, int postId, int attachmentId){
        Attachment attachment = constructAttachmentFromPart(filePart, postId);
        attachment.setAttachmentId(attachmentId);

        return attachment;
    }
}
