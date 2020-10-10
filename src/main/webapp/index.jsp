<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-09-28
  Time: 9:06 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

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
    </head>
    <body>
        <jsp:useBean
                id= "theme"
                scope= "session"
                class= "Beans.ThemeManager">
        </jsp:useBean>
        <div class="<%= theme.getBackgroundCSSClass() %>">
            <div id="navbar">
                <div id="appLogo">
                    <i class="fas fa-comments"></i>
                    <span>Chat Server</span>
                </div>
                <div id="navBarRightItems">
                    <span id="usernameNavBar">Anonymous<i class="fas fa-user ml-1"></i></span>
                    <a id="themeDropdown">Change theme<i class="fas fa-cog ml-1"></i></a>
                </div>
            </div>
            <div>
                <div>
                    <div id="chatUI" class="rounded">
                        <div id="messagesContainer" class="overflow-auto container">
                            <div class="row mb-1 message rounded">
                                <small class="m-1">Username</small>
                                <div class="m-1 messageContent">Here are some content...</div>
                            </div>
                            <div class="row mb-1">
                                <div class="rounded">
                                    <small class="m-1">Username</small>
                                    <div class="m-1">Here are some content...</div>
                                </div>
                            </div>
                            <div class="row mb-1">
                                <div class="rounded">
                                    <small class="m-1">Username</small>
                                    <div class="m-1">Here are some content...</div>
                                </div>
                            </div>
                        </div>

                        <form action="Servlet" method="POST">
                            <input id="username" name="username" type="text" class="form-control" placeholder="Anonymous" hidden/>
                            <div>
                                <span>Currently sending messages as Anonymous</span>
                            </div>
                            <div class="form-group">
                                <textarea id="message" name="message" class="form-control" rows="3" placeholder="Enter your message here..."></textarea>
                            </div>
                            <div class="form-group">
                                <input type="submit" value="Send" class="btn btn-primary mb-2"/>
                            </div>
                        </form>
                    </div>

                    <div id="utilitiesUI">
                        <div>
                            <div class="card-header bg-dark text-light">
                                <a data-toggle="collapse" data-target="#archiveCardBody">
                                    <i class="fas fa-caret-down"></i> Archive Messages
                                </a>
                            </div>
                            <div class="collapse show" id="archiveCardBody">
                                <div class="card-body">
                                    <form action="Servlet">
                                        <div class="form-group">
                                            <label for="archiveMessage_from">From: </label>
                                            <input id="archiveMessage_from" name="from" type="datetime-local" class="form-control" />
                                        </div>

                                        <div class="form-group">
                                            <label for="archiveMessage_to">To: </label>
                                            <input id="archiveMessage_to" name="to" type="datetime-local" class="form-control" />
                                        </div>

                                        <div class="form-group">
                                            <label class="radio">
                                                <input type="radio" name="fileFormat" value="TEXT" checked/> TEXT
                                            </label>
                                            <label class="radio">
                                                <input type="radio" name="fileFormat" value="XML"> XML
                                            </label>
                                        </div>

                                        <div class="form-group">
                                            <button type="Submit" class="btn btn-dark mb-2">Download</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header bg-dark text-light">
                                <a data-toggle="collapse" data-target="#DeleteCardBody">
                                    Delete Messages
                                </a>
                            </div>
                            <div class="collapse show" id="deleteCardBody">
                                <div class="card-body">
                                    <form action="Servlet" method="post">
                                        <div class="form-group">
                                            <label for="deleteMessage_from">From: </label>
                                            <input id="deleteMessage_from" name="from" type="datetime-local" class="form-control" />
                                        </div>

                                        <div class="form-group">
                                            <label for="deleteMessage_to">To: </label>
                                            <input id="deleteMessage_to" name="to" type="datetime-local" class="form-control" />
                                        </div>

                                        <div class="form-group">
                                            <button type="Submit" name="clearChat" class="btn btn-primary mb-2">Delete</button>
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
