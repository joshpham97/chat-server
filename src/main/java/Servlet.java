import com.google.gson.Gson;
import server.chat.Post;
import server.chat.dao.UserDAO;
import server.chat.dao.UserFileDAO;
import server.chat.daoimpl.UserFileDaoImpl;
import server.chat.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    private enum Parameters {
        FROM("from"),
        TO("to"),
        FILE_FORMAT("fileFormat"),
        USERNAME("username"),
        MESSAGE("message");

        private final String value;

        private Parameters(String value){
            this.value = value;
        }

        public String toString(){
            return this.value;
        }
    }


    private PostManager chatManager;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init() throws ServletException {
        super.init();

        chatManager = new PostManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        String uname = (String)request.getSession(false).getAttribute("username");
        String message = request.getParameter("message");
        String title = request.getParameter("title");

        Post post = null;
        post = chatManager.postMessageDatabase(uname,title, message);
        chatManager.postMessage(uname, message);
        response.sendRedirect("post.jsp");
       
        if(request.getHeader("referer") != null) {
            String messageParam= request.getParameter(Parameters.MESSAGE.toString());

            if(messageParam == null || messageParam.isEmpty()){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseWriter.append("Invalid request. No message found.");
            }else{
                String userParam = request.getParameter(Parameters.USERNAME.toString());
                userParam = (userParam == null) ? "" : userParam;
                Post newMessage = chatManager.postMessage(userParam, messageParam);

                Gson gson = new Gson();
                String jsonMessage = gson.toJson(newMessage);
                responseWriter.append(jsonMessage);
            }

        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.append("Invalid request. No Referrer found.");
        }

        responseWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();
        try{
            if(request.getHeader("referer") != null){
                String strFrom = request.getParameter(Parameters.FROM.toString());
                String strTo = request.getParameter(Parameters.TO.toString());

                LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDate.parse(strFrom).atStartOfDay();
                LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDate.parse(strTo).plusDays(1).atStartOfDay();


                Stream<Post> filteredMessagesStream = chatManager.listMessages(from, to).stream();

                String strFileFormat = request.getParameter(Parameters.FILE_FORMAT.toString());
                FileFormat fileFormat = (strFileFormat == null || strFileFormat.isEmpty()) ? FileFormat.TEXT : FileFormat.valueOf(strFileFormat);

                StringBuilder fileContent = new StringBuilder();

                if (fileFormat == FileFormat.XML) {
                    fileContent.append("<Messages>\n");
                    filteredMessagesStream.forEach((Post m) -> fileContent.append(m.toXML()));
                    fileContent.append("</Messages>");
                    response.setHeader("Content-Disposition", "attachment; filename=\"messages.xml\"");
                } else {
                    filteredMessagesStream.forEach((Post m) -> fileContent.append(m.toString()).append("\n"));
                    response.setHeader("Content-Disposition", "attachment; filename=\"messages.txt\"");
                }

                response.setContentType("text/plain");
                response.setDateHeader("Expires", 0);
                responseWriter.append(fileContent.toString());
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseWriter.append("Invalid request. No Referrer found.");
            }
        }catch (Exception ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.append("An error has occurred while generating the Message Archive file.");
        }

        responseWriter.close();
    }
  
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        try{
            if(request.getHeader("referer") != null) {
                String strFrom = request.getParameter(Parameters.FROM.toString());
                String strTo = request.getParameter(Parameters.TO.toString());

                LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDateTime.parse(strFrom, FORMATTER);
                LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDateTime.parse(strTo, FORMATTER);

                Gson gson = new Gson();
                String x = gson.toJson(chatManager.listMessages(from, to));
                responseWriter.append(x);
            }
        }catch (Exception ex){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.append("An error has occurred while generating the Message Archive file.");
        }

        responseWriter.close();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        if(request.getHeader("referer") != null) {
            String strFromParam = request.getParameter(Parameters.FROM.toString());
            String strToParam = request.getParameter(Parameters.TO.toString());

            try {
                LocalDateTime fromParam = (strFromParam == null || strFromParam.isEmpty()) ? null : LocalDate.parse(strFromParam).atStartOfDay();
                LocalDateTime toParam = (strToParam == null || strToParam.isEmpty()) ? null : LocalDate.parse(strToParam).plusDays(1).atStartOfDay();
                chatManager.clearChat(fromParam, toParam);
            } catch (DateTimeParseException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseWriter.append("Invalid request. Unexpected date/time format.");
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseWriter.append("Invalid request. No Referrer found.");
        }

        responseWriter.close();
    }
}
