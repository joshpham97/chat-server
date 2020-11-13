<%@ page import="server.database.dao.PostDAO" %>
<%@ page import="server.database.model.Post" %><%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-04
  Time: 4:53 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="post" scope="request" type="server.database.model.Post"/>
<jsp:useBean id="attachment" scope="request" type="server.database.model.Attachment"/>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <title>Create post</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
    <span class="navbar-brand mb-0 h1">Simple Message Board</span>
    <a class="btn btn-primary mb-0 h1" href="index.jsp"><i class="fas fa-eye mr-2"></i>View posts</a>
</nav>
<div class="container">
    <div class="col-12 mt-2">
        <div class="h4">Create New Post</div>
        <form action="Servlet" method="post">
            <div class="form-group">
                <label for="postContent"></label>
                <textarea id="postTitle" name="title" class="form-control" rows="1" placeholder="Title" required></textarea>
                <textarea id="postContent" name="message" class="form-control" rows="2" placeholder="Type your post here..." required></textarea>
            </div>
            <div>
                <button type="submit" class="btn btn-primary mt-2 mi">Edit post</button>
            </div>
        </form>
    </div>
    <div class="col-12 mt-2">
        <div class="h3">Manage attachment</div>
        <div>
            <c:choose>
                <c:when test="${requestScope.post.attID == null}">
                    <div>
                        <div class="h5">Add attachment</div>
                        <form method="post" action="AttachmentServlet" enctype="multipart/form-data">
                            <input type="text" name="postId" value="${param.postId}" hidden/>
                            <input type="text" name="action" value="post" hidden/>
                            <div class="form-group">
                                <input type="file" id="addAttachment" name="attachment" >
                            </div>
                            <div>
                                <button type="submit" class="btn btn-primary mt-2 mi">Upload</button>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="h5">Attachment information</div>
                    <div>
                        <div>
                            <span></span>
                            <div class="alert alert-primary" role="alert">
                                <div>
                                    <span class="font-weight-bold">File name:</span>
                                    <span>
                                            <c:out value="${attachment.filename}"/>
                                        </span>
                                </div>
                                <div>
                                    <span class="font-weight-bold">File size:</span>
                                    <span>
                                            <c:out value="${attachment.filesize}"/> bytes
                                        </span>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div class="d-inline-block">
                                <form method="get" action="AttachmentServlet" enctype="multipart/form-data">
                                    <input type="text" name="attachmentId" value="${post.attID}" hidden/>
                                    <input type="text" name="action" value="get" hidden/>
                                    <div>
                                        <button type="submit" class="btn btn-primary mt-2 mi">Download</button>
                                    </div>
                                </form>
                            </div>
                            <div class="d-inline-block">
                                <form method="get" action="AttachmentServlet" enctype="multipart/form-data">
                                    <input type="text" name="postId" value="${post.postID}" hidden/>
                                    <input type="text" name="attachmentId" value="${post.attID}" hidden/>
                                    <input type="text" name="action" value="delete" hidden/>
                                    <button type="submit" class="btn btn-danger mt-2 mi"><i class="fas fa-trash mr-2"></i>Delete</button>
                                </form>
                            </div>
                        </div>
                        <div class="h5">Change attachment</div>
                        <div>
                            <form method="post" action="AttachmentServlet" enctype="multipart/form-data">
                                <input type="text" name="postId" value="${post.postID}" hidden/>
                                <input type="text" name="attachmentId" value="${post.attID}" hidden/>
                                <input type="text" name="action" value="put" hidden/>
                                <div class="form-group">
                                    <input type="file" id="attachment" name="attachment" >
                                </div>
                                <div>
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>