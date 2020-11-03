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
    <title>Login Page</title>
</head>
<body>
<h3>Login Form</h3>
<%--    3 defined users
    username: username1
    passsword password1

    username: username2
    password: password2

    username: username3
    password: password3

--%>

<form action="AuthServlet" method="post">
    UserName:<input type="text" name="username"/><br/><br/>
    Password:<input type="password" name="password"/><br/><br/>
    <input type="submit" value="login"/>
</form>
</body>
</html>
