import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Stream;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    private enum Parameters {
        FROM("from"),
        TO("to"),
        FILE_FORMAT("fileFormat");

        private final String value;

        private Parameters(String value){
            this.value = value;
        }

        public String toString(){
            return this.value;
        }
    }


    private ChatManager chatManager;
    private final String TIME_ZONE_ID = TimeZone.getDefault().toString();
    private final Locale LOCALE = Locale.ENGLISH;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", LOCALE);

    @Override
    public void init() throws ServletException {
        super.init();

        chatManager = new ChatManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();

        if(request.getHeader("referrer") != null){
            String strFrom = request.getParameter(Parameters.FROM.toString());
            String strTo = request.getParameter(Parameters.TO.toString());
            String strFileFormat = request.getParameter(Parameters.FILE_FORMAT.toString());

            LocalDateTime from = LocalDateTime.parse(strFrom);
            LocalDateTime to = LocalDateTime.parse(strTo);
            FileFormat fileFormat = FileFormat.valueOf(strFileFormat);

            Stream<Message> filteredMessagesStream = chatManager.ListMessages(from, to).stream();

            StringBuilder fileContent = new StringBuilder();

            if(fileFormat == FileFormat.XML){
                fileContent.append("<Messages>\n");
                filteredMessagesStream.forEach((Message m) -> fileContent.append(m.toXML()));
                fileContent.append("</Messages>");
                response.setHeader("content-disposition", "attachment; filename=\"messages.xml\"");
            }else{
                filteredMessagesStream.forEach((Message m) -> fileContent.append(m.toString()));
                response.setHeader("content-disposition", "attachment; filename=\"messages.txt\"");
            }

            response.setContentType("text/plain");
            response.setHeader("expires", FORMATTER.format(LocalDateTime.now()));
            responseWriter.append(fileContent.toString());

        }else{
            responseWriter.append("Invalid request. No Referrer found.");
        }

        responseWriter.close();
    }
}
