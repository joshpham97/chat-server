<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String postID = request.getParameter("postID");
    String username = request.getParameter("username");
    String title = request.getParameter("title");
    String datePosted = request.getParameter("datePosted");
    String dateModified = request.getParameter("dateModified");
    String message = request.getParameter("message");
    String attID = request.getParameter("attID");
    //System.out.println(postID + ":" + username + ":" + title + ":" + datePosted + ":" + dateModified + ":" + message + ":" + attID);
%>
<div class="card mb-2">
    <div class="card-header">
        <div class="float-left text-muted">
            <span><%= username %></span>
            <small><i class="far fa-clock pr-1"></i><%= datePosted %></small>
        </div>

        <div class="float-right">
            <%
                if(attID != null) {
            %>
                <a href="/AttachmentServlet"><i class="fas fa-paperclip mr-2" title="Download attachment"></i></a>
            <%
                }
            %>
            <a href="/AttachmentServlet"><i class="fas fa-edit mr-2"></i></a>
            <a href="/AttachmentServlet"><i class="fas fa-trash mr-2"></i></a>
        </div>
    </div>

    <div>
        <div class="card-body ">
            <div><%= message %></div>
            <div class="float-right">
            </div>
        </div>
    </div>
</div>