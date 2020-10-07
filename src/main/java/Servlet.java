import jdk.vm.ci.meta.Local;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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


    ChatManager chatManager;

    @Override
    public void init() throws ServletException {
        super.init();

        chatManager = new ChatManager();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        }else{
            filteredMessagesStream.forEach((Message m) -> fileContent.append(m.toString()));
        }

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.append(fileContent.toString());
        out.close();
    }
}
