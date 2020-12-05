<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-11-10
  Time: 9:55 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%
    String username = (String) session.getAttribute("username");
    if (null == username) {
        session.setAttribute("errorMessage", "You have to be logged in to access the home page ");
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/utils.js"></script>
    <title>New post</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <span class="navbar-brand mb-0 h1">Simple Message Board</span>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="mb-0 nav-link" href="index.jsp"><i class="fas fa-home mr-2"></i>Home</a>
                </li>
                <li class="nav-item">
                    <a class="mb-0 nav-link active" href="create.jsp"><i class="fas fa-plus mr-2"></i>New Post</a>
                </li>
                <li class="nav-item">
                    <a class="mb-0 nav-link" href="filter.jsp?${requestScope.queryString}"><i class="fas fa-filter mr-2"></i>Filter</a>
                </li>
            </ul>
        </div>
        <div>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown">
                        <i class="fas fa-user mr-2"></i>Hello, ${sessionScope['username']}
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="#" onclick="featureNotImplemented()"><i class="fas fa-key mr-2" ></i>Change Password</a>
                        <a class="dropdown-item" href="#" onclick="featureNotImplemented()"><i class="fas fa-user-circle mr-2"></i>Update account</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="AuthServlet"><i class="fas fa-sign-out-alt mr-2"></i>Sign Out</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <div class="col-12 mt-2">
            <div class="h4">Create New Post</div>
            <form action="PostServlet" method="post">
                <input type="text" name="action" value="post" hidden/>
                <div class="form-group">
                    <label for="postTitle">Title</label>
                    <input id="postTitle" name="title" class="form-control" rows="1" placeholder="Title" required />
                </div>
                <div class="form-group">
                    <label for="postContent">Message</label>
                    <textarea id="postContent" name="message" class="form-control" rows="2" placeholder="Type your post here..." required></textarea>
                </div>
                <c:choose>
                    <c:when test="${sessionScope.membership[0].equals('admins') || sessionScope.membership[0].equals('concordia')}">
                        <select name="group">
                            <option value="public" selected = "selected">Public</option>
                            <option value="concordia">Concordia</option>
                            <option value="encs">ENCS</option>
                            <option value="comp">COMP</option>
                            <option value="soen">SOEN</option>
                        </select>
                    </c:when>
                    <c:when test="${sessionScope.membership[0].equals('encs')}">
                        <select name="group">
                            <option value="public" selected = "selected">Public</option>
                            <option value="encs">ENCS</option>
                            <option value="comp">COMP</option>
                            <option value="soen">SOEN</option>
                        </select>
                    </c:when>
                    <c:when test="${sessionScope.membership[0].equals('comp')}">
                        <select name="group">
                            <option value="public" selected = "selected">Public</option>
                            <option value="comp">COMP</option>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="group">
                            <option value="public" selected = "selected">Public</option>
                            <option value="soen">SOEN</option>
                        </select>
                    </c:otherwise>
                </c:choose>
                <div class="col text-center">
                    <button type="submit" class="btn btn-primary mt-2 mi">Create Post</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

