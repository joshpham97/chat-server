<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Post Message</title>
</head>
<body>
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
    <a id="btnDownloadMessages" href="Servlet">Download Messages</a>
</body>
</html>
