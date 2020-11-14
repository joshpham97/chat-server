<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-11-02
  Time: 12:10 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/utils.js"></script>
    <title>Login Page</title>
</head>

<%
    String username = (String) session.getAttribute("username");
    if (null != username) {
        response.sendRedirect("index.jsp");
    }
%>

<body>
    <nav class="navbar navbar-dark bg-dark">
        <span class="navbar-brand mb-0 h1">Simple Message Board</span>
        <button class="btn btn-primary" onclick="featureNotImplemented()"><i class="fas fa-user-plus mr-2"></i>Sign up</button>
    </nav>
    <div class="container">
        <div class="row mt-2">
            <div class="col-12">
                <h4>Login</h4>
                <%--    3 defined users
                    username: username1
                    passsword password1

                    username: username2
                    password: password2

                    username: username3
                    password: password3

                --%>
                <%
                    Object objErrorMessage = session.getAttribute("errorMessage");
                    String errorMessage = objErrorMessage == null ? "" : (String) objErrorMessage;
                    if(!errorMessage.isEmpty()) {
                %>
                        <div class="alert alert-danger" role="alert">
                            <%= errorMessage %>
                        </div>
                <%
                    }
                %>
                <form action="AuthServlet" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input id="username" class="form-control" name="username" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" class="form-control" name="password" placeholder="Password">
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-sign-in-alt mr-2"></i>Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
