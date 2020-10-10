<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-09-28
  Time: 9:06 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="server.chat.Message" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Post Message</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="defaultTheme.css" />
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
        <% ArrayList<Message> messages = (ArrayList<Message>)request.getAttribute("messages"); %>
    </head>
    <body>
        <jsp:useBean
                id= "theme"
                scope= "session"
                class= "Beans.ThemeManager">
        </jsp:useBean>
        <div id="navbar">
            <div id="appLogo">
                <i class="fas fa-comments"></i>
                <span>Chat Server</span>
            </div>
            <div id="navBarRightItems">
                <span id="usernameNavBar"><i class="fas fa-user mr-1"></i>Anonymous</span>
                <a id="themeDropdown"><i class="fas fa-cog mr-1"></i>Change theme</a>
            </div>
        </div>
        <div id="mainUI" class="<%= theme.getBackgroundCSSClass() %>">
            <div>
                <div>
                    <div id="chatUI" class="rounded">
                        <div id="messagesContainer" class="overflow-auto container">
                            <%
                                if(messages == null || messages.size() == 0) {
                            %>
                                <div class="row mb-1">
                                    No messages to display
                                </div>
                            <%
                                } else {
                                    for(Message m: messages) {
                            %>
                                        <div class="row mb-1 message rounded">
                                            <small class="senderName textSecondary m-1"><%= m.getUsername() %></small>
                                            <div class="m-1 messageContent"><%= m.getMessage() %></div>
                                        </div>
                            <%
                                    }
                                }
                            %>
                        </div>

                        <div id="usernameChatUI">
                            Signed in as <span class="textSecondary">Anonymous</span>
                        </div>
                        <form class="form" action="Servlet" method="POST">
                            <input id="username" name="username" type="text" class="form-control" placeholder="Anonymous" hidden/>
                            <div>
                                <textarea id="message" name="message" class="form-control" rows="2" placeholder="Enter your message here..."></textarea>
                            </div>
                            <div id="btnPostMessageContainer">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-paper-plane mr-1"></i>Send
                                </button>
                            </div>
                        </form>
                    </div>

                    <div id="utilitiesUI">
                        <div class="card mb-1">
                            <div class="card-header">
                                <a data-toggle="collapse" data-target="#filterCardBody">
                                    <i class="fas fa-filter mr-1 textPrimary"></i>
                                    <span class="textPrimary">Filter Messages</span>
                                </a>
                            </div>
                            <div class="collapse show" id="filterCardBody">
                                <div class="card-body">
                                    <form action="Servlet" class="customForm">
                                        <div>
                                            <label for="filterMessage_from">From: </label>
                                            <input id="filterMessage_from" name="from" type="date" class="form-control"/>
                                        </div>

                                        <div>
                                            <label for="filterMessage_to">To: </label>
                                            <input id="filterMessage_to" name="to" type="date" class="form-control" />
                                        </div>

                                        <div class="utilitiesUIBtnContainer">
                                            <button type="Submit" class="btn btn-primary" >
                                                <i class="fas fa-filter mr-1"></i>Filter
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="card mb-1">
                            <div class="card-header">
                                <a data-toggle="collapse" data-target="#archiveCardBody">
                                    <i class="fas fa-download mr-1 textPrimary"></i>
                                    <span class="textPrimary">Archive Messages</span>
                                </a>
                            </div>
                            <div class="collapse show" id="archiveCardBody">
                                <div class="card-body">
                                    <form action="Servlet" class="customForm">
                                        <div>
                                            <label for="archiveMessage_from">From: </label>
                                            <input id="archiveMessage_from" name="from" type="date" class="form-control"/>
                                        </div>

                                        <div>
                                            <label for="archiveMessage_to">To: </label>
                                            <input id="archiveMessage_to" name="to" type="date" class="form-control" />
                                        </div>

                                        <div>
                                            <label for="archiveMessage_to">Format: </label>
                                            <span class="radio">
                                                <input type="radio" name="fileFormat" value="TEXT" checked/> TEXT
                                            </span>
                                            <span class="radio">
                                                <input type="radio" name="fileFormat" value="XML"> XML
                                            </span>
                                        </div>

                                        <div class="utilitiesUIBtnContainer">
                                            <button type="Submit" class="btn btn-primary" name="downloadChat">
                                                <i class="fas fa-download mr-1"></i>Download
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header">
                                <a data-toggle="collapse" data-target="#DeleteCardBody">
                                    <i class="fas fa-trash-alt mr-1 textPrimary"></i>
                                    <span class="textPrimary">Delete Messages</span>
                                </a>
                            </div>
                            <div class="collapse show" id="deleteCardBody">
                                <div class="card-body">
                                    <form action="Servlet" method="post" class="customForm">
                                        <div>
                                            <label for="deleteMessage_from">From: </label>
                                            <input id="deleteMessage_from" name="from" type="date" class="form-control" />
                                        </div>

                                        <div>
                                            <label for="deleteMessage_to">To: </label>
                                            <input id="deleteMessage_to" name="to" type="date" class="form-control" />
                                        </div>

                                        <div class="utilitiesUIBtnContainer">
                                            <button type="Submit" name="clearChat" class="btn btn-primary">
                                                <i class="fas fa-trash-alt mr-1"></i>Delete
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
