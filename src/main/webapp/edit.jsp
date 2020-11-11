<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-11-10
  Time: 9:55 p.m.
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
    <title>Edit post</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
    <span class="navbar-brand mb-0 h1">Simple Message Board</span>
    <a class="btn btn-primary mb-0 h1" href="index.jsp"><i class="fas fa-eye mr-2"></i>View posts</a>
</nav>
<div class="container">
    <div class="col-12 mt-2">
        <div class="h3">Manage Posts</div>
        <div>
            <div>
                <div class="h5">Update Post</div>
                <form action="PostServlet" method="post">
                    <div class="form-group">
                        <label for="postContent"></label>
                        <input type="text" name="action" value="update" hidden/>
                        <input type="text" name="postId" value="${param.postId}" hidden/>
                        <textarea id="postTitle" name="title" class="form-control" rows="1" placeholder="Title" required></textarea>
                        <textarea id="postContent" name="message" class="form-control" rows="2" placeholder="Type your post here..." required></textarea>
                    </div>
                    <div class="col text-center">
                        <button type="submit" class="btn btn-primary mt-2 mi">Update Post</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

