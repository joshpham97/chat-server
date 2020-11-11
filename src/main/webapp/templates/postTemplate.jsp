<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    JSONParser parser = new JSONParser();
    JSONArray posts = (JSONArray) parser.parse(request.getParameter("posts"));

    for (Object obj: posts) {
        JSONObject post = (JSONObject) obj;
%>
    <div class="card mb-2">
        <div class="card-header">
            <div class="float-left text-muted">
                <span><%= post.get("username") %></span>
                <small><i class="far fa-clock pr-1"></i><%= post.get("datePostedStr") %></small>
            </div>

            <div class="float-right">
                <%
                    if(post.get("attID") != null) {
                %>
                    <a href="/AttachmentServlet"><i class="fas fa-paperclip mr-2" title="Download attachment"></i></a>
                <%
                    }
                %>
                <a href="PostEditServlet?postId=<%= post.get("postID") %>"><i class="fas fa-edit mr-2"></i></a>
                <a href="PostServlet?action=delete&postID=<%= post.get("postID") %>"><i class="fas fa-trash mr-2"></i></a>
            </div>
        </div>

        <div>
            <div class="card-body ">
                <div>Postid: <%= post.get("postID") %></div>
                <div><%= post.get("message") %></div>
                <div class="float-right">
                </div>
            </div>
        </div>
    </div>
<%
    }
%>