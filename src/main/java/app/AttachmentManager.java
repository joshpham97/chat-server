package app;

import server.database.dao.AttachmentDAO;
import server.database.dao.PostDAO;
import server.database.model.Attachment;

import javax.servlet.http.Part;
import java.io.IOException;

public class AttachmentManager {
    public static boolean insertAttachment(Part filePart, int postId){
        try{
            Attachment attachment = constructAttachmentFromPart(filePart, postId);
            attachment = AttachmentDAO.insert(attachment, filePart.getInputStream());
            if (attachment != null){
                PostDAO.updateAttachmentId(postId, attachment.getAttachmentId());
                PostDAO.updateModifiedDate(postId);
                return true;
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean deleteAttachment(int postId, int attachmentId) {
        if (PostDAO.updateAttachmentId(postId, null) && PostDAO.updateModifiedDate(postId)){
            AttachmentDAO.delete(attachmentId);
            return true;
        }
        else
            return false;
    }

    public static Attachment getAttachment(int attachmentId){
        return AttachmentDAO.select(attachmentId);
    }

    public static boolean updateAttachment(Part filePart, int postId, int attachmentId){
        try{
            Attachment attachment = constructAttachmentFromPart(filePart, postId, attachmentId);

            if(AttachmentDAO.update(attachment, filePart.getInputStream())){
                return PostDAO.updateModifiedDate(postId);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    public static Attachment constructAttachmentFromPart(Part filePart, int postId){
        Attachment attachment = new Attachment();

        attachment.setFilename(filePart.getSubmittedFileName());
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
