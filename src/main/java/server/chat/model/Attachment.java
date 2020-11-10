package server.chat.model;

import java.sql.Blob;

public class Attachment {
    private int attachmentId;
    private String filename;
    private int filesize;
    private String mediaType;
    private Blob fileBlob;

    public Attachment() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Blob getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(Blob fileBlob) {
        this.fileBlob = fileBlob;
    }
}
