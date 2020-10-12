import com.google.gson.Gson;
import server.chat.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        POST_MESSAGE("postMessage"),
        CLEAR_CHAT("clearChat"),
        DOWNLOAD_CHAT("downloadChat"),
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


    private ChatManager chatManager;
    //private final String TIME_ZONE_ID = TimeZone.getDefault().toString();
    //private final Locale LOCALE = Locale.ENGLISH;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init() throws ServletException {
        super.init();

        chatManager = new ChatManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getHeader("referer") != null) {
            String postMessageParam = request.getParameter(Parameters.POST_MESSAGE.toString());
            String clearChatParam= request.getParameter(Parameters.CLEAR_CHAT.toString());
            String userParam = request.getParameter(Parameters.USERNAME.toString());
            String messageParam= request.getParameter(Parameters.MESSAGE.toString());

            // POST MESSAGE
            if(postMessageParam != null) {
                // TODO: post message
                String username = request.getParameter(Parameters.USERNAME.toString());
                Message newMessage = chatManager.postMessage(username, postMessageParam);

                Gson gson = new Gson();
                String jsonMessage = gson.toJson(newMessage);
                PrintWriter responseWriter = response.getWriter();
                responseWriter.append(jsonMessage);
                responseWriter.close();
            }
            // CLEAR CHAT
            else if (clearChatParam != null) {
                String strFromParam = request.getParameter(Parameters.FROM.toString());
                String strToParam = request.getParameter(Parameters.TO.toString());

                try {
                    LocalDateTime fromParam = strFromParam.isEmpty() ? null : LocalDate.parse(strFromParam).atStartOfDay();
                    LocalDateTime toParam = strToParam.isEmpty() ? null : LocalDate.parse(strToParam).plusDays(1).atStartOfDay();
                    chatManager.ClearChat(fromParam, toParam);
                }
                catch(DateTimeParseException e) {
                    PrintWriter responseWriter = response.getWriter();
                    responseWriter.append("Invalid request. Unexpected date/time format.");
                    responseWriter.close();
                }
            }
        }
        else {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.append("Invalid request. No Referrer found.");
            responseWriter.close();
        }

//        request.setAttribute("messages", chatManager.ListMessages());
        //request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        try{
            if(request.getHeader("referer") != null){
                String strFrom = request.getParameter(Parameters.FROM.toString());
                String strTo = request.getParameter(Parameters.TO.toString());

                LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDate.parse(strFrom).atStartOfDay();
                LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDate.parse(strTo).plusDays(1).atStartOfDay();


                Stream<Message> filteredMessagesStream = chatManager.ListMessages(from, to).stream();

                String strFileFormat = request.getParameter(Parameters.FILE_FORMAT.toString());
                FileFormat fileFormat = strFileFormat.isEmpty() ? FileFormat.TEXT : FileFormat.valueOf(strFileFormat);

                StringBuilder fileContent = new StringBuilder();

                if (fileFormat == FileFormat.XML) {
                    fileContent.append("<Messages>\n");
                    filteredMessagesStream.forEach((Message m) -> fileContent.append(m.toXML()));
                    fileContent.append("</Messages>");
                    response.setHeader("Content-Disposition", "attachment; filename=\"messages.xml\"");
                } else {
                    filteredMessagesStream.forEach((Message m) -> fileContent.append(m.toString()).append("\n"));
                    response.setHeader("Content-Disposition", "attachment; filename=\"messages.txt\"");
                }

                response.setContentType("text/plain");
                response.setHeader("expires", LocalDateTime.now().format(FORMATTER));
                responseWriter.append(fileContent.toString());
            }else{
                responseWriter.append("Invalid request. No Referrer found.");
            }
        }catch (Exception ex){
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

                LocalDateTime from = (strFrom == null || strFrom.isEmpty()) ? null : LocalDateTime.parse(strFrom, FORMATTER2);
                LocalDateTime to = (strTo == null || strTo.isEmpty()) ? null : LocalDateTime.parse(strTo, FORMATTER2);

                Gson gson = new Gson();
                String x = gson.toJson(chatManager.ListMessages(from, to));
                responseWriter.append(x);
            }
        }catch (Exception ex){
            responseWriter.append("An error has occurred while generating the Message Archive file.");
        }

        responseWriter.close();
    }
}
