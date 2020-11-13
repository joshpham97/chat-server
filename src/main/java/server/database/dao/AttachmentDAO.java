package server.database.dao;

import server.database.db.DBConnection;
import server.database.model.Attachment;

import java.io.InputStream;
import java.sql.*;

public class AttachmentDAO{
    public static Attachment insert(Attachment attachment, InputStream fileInputStream){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO attachments (file_size, filename, file_type, attachment) values (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, attachment.getFilesize());
            statement.setString(2, attachment.getFilename());
            statement.setString(3, attachment.getMediaType());
            statement.setBlob(4, fileInputStream);

            int row = statement.executeUpdate();
            if (row > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    attachment.setAttachmentId((int) generatedKeys.getLong(1));
                    return attachment;
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return null;
    }

    public static boolean delete(int attachmentId){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM attachments WHERE att_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, attachmentId);

            int row = statement.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return false;
    }

    public static Attachment select(int attachmentId){
        Attachment attachment = null;

        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM attachments WHERE att_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, attachmentId);

            ResultSet rs = statement.executeQuery();


                if(rs.next()) {
                    attachment = new Attachment();
                    attachment.setAttachmentId(rs.getInt("att_id"));
                    attachment.setFilename(rs.getString("filename"));
                    attachment.setFilesize(rs.getInt("file_size"));
                    attachment.setMediaType(rs.getString("file_type"));
                    attachment.setFileBlob(rs.getBlob("attachment"));
                }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return attachment;
    }

    public static boolean update(Attachment attachment, InputStream fileInputStream){
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE attachments SET filename = ?, file_size = ?, file_type = ?, attachment = ?  WHERE att_id = ? ";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, attachment.getFilename());
            statement.setInt(2, attachment.getFilesize());
            statement.setString(3, attachment.getMediaType());
            statement.setBlob(4, fileInputStream);
            statement.setInt(5, attachment.getAttachmentId());


            int row = statement.executeUpdate();
            if (row > 0) {
                return true;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            DBConnection.closeConnection();
        }

        return false;
    }
}
