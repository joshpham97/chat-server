<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-04
  Time: 10:21 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <title>Filter Posts</title>
</head>
<body>
    <nav class="navbar navbar-dark bg-dark">
        <span class="navbar-brand mb-0 h1">Simple Message Board</span>
        <a class="btn btn-primary mb-0 h1" href="index.jsp"><i class="fas fa-eye mr-2"></i>View posts</a>
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
