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
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</head>
    <body>
        <nav class="navbar navbar-light bg-light">
            <span class="navbar-brand mb-0 h1">Chat Server</span>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Anonymous" />
                    </div>
                </div>
                <div class="col-md-6">
                    <form action="PostMessageSevlet" method="POST">
                        <table style="with: 50%">

                            <tr>
                                <td>UserName</td>
                                <td><input type="text" name="username" /></td>
                            </tr>
                            <tr>
                                <td>Message</td>
                                <td><textarea name="message" cols="30" rows="10" ></textarea></td>
                            </tr>
                        </table>
                        <input type="submit" value="Post Message" /></form>
                    <br>
                    Delete Message:

                    <br>

                    <form id="deleteMessage">
                        <input type="text" name = "StartDate" placeholder = "dd/mm/year"/>
                        <br />
                        <input type="text" class = "datepicker" name = "EndDate" placeholder = "dd/mm/yyyy"/>
                        <br />
                        <input type = "submit" name = "submit" value = "Delete"/>
                    </form>
                </div>

                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <a data-toggle="collapse" data-target="#archiveCardBody">
                                Archive Messages
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
                                        <button type="Submit" class="btn btn-primary mb-2">Download</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            Delete Message
                        </div>
                        <div class="card-body">
                            <input type="text" name = "StartDate" placeholder = "dd/mm/year"/>
                            <br />
                            <input type="text" class = "datepicker" name = "EndDate" placeholder = "dd/mm/yyyy"/>
                            <br />
                            <input type = "submit" name = "submit" value = "Delete"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
