<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-04
  Time: 10:21 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<c:if test="${sessionScope.username == null}">
    <% session.setAttribute("errorMessage", "You have to be logged in to access the home page "); %>
    <c:redirect url="login.jsp" />
</c:if>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="js/utils.js"></script>
    <title>Filter Posts</title>
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
                    <a class="mb-0 nav-link" href="create.jsp"><i class="fas fa-plus mr-2"></i>New Post</a>
                </li>
                <li class="nav-item">
                    <a class="mb-0 nav-link active" href="filter.jsp?${requestScope.queryString}"><i class="fas fa-filter mr-2"></i>Filter</a>
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
            <div class="h4">Filter Posts</div>

            <form action="index.jsp">
                <div class="form-group">
                    <label for="username">By username</label>
                    <input type="text" placeholder="Username"  id="username" class="form-control" name="username" value="${param.username}" />
                </div>

                <div class="form-group">
                    <label for="from">From date</label>
                    <input type="date" id="from" class="form-control" name="from" value="${param.from}" />
                </div>

                <div class="form-group">
                    <label for="to">To date</label>
                    <input type="date" id="to" class="form-control" name="to" value="${param.to}" />
                </div>

                <div class="form-group">
                    <label for="hashtags">By hashtags</label>
                    <input type="text" placeholder="Hashtag1 Hashtag2 ..." id="hashtags" class="form-control" name="hashtags" value="${param.hashtags}" />
                </div>

                <div class="text-center">
                    <button class="btn btn-primary" type="submit">Filter</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
