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


<c:choose>
    <c:when test="${sessionScope.username == null}">
        <% session.setAttribute("errorMessage", "You have to be logged in to access the home page "); %>
        <c:redirect url="login.jsp" />
    </c:when>
    <c:when test="${!post.getUsername().equals(sessionScope.username) && !sessionScope.membership.contains('admins')}">
        <c:redirect url="index.jsp" />
    </c:when>
</c:choose>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/utils.js"></script>
    <title>Create post</title>
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
            <c:if test="${param.success == false}">
                <div class="alert alert-danger">
                    An error has occur. Please try again or contact support.
                </div>
            </c:if>
        </div>
        <div class="col-12 mt-2">
            <div class="h3">Update Post</div>
                <form action="PostServlet" method="post">
                    <input type="text" name="action" value="update" hidden/>
                    <input type="text" name="postId" value="${param.postId}" hidden/>
                    <div class="form-group">
                        <label for="postTitle">Title</label>
                        <input id="postTitle" name="title" class="form-control" rows="1" placeholder="Title" value="${post.title}" required/>
                    </div>
                    <div class="form-group">
                        <label for="postContent">Message</label>
                        <textarea id="postContent" name="message" class="form-control" rows="2" required><c:out value="${post.message}" /></textarea>
                    </div>
                    <select name="group">
                        <c:forEach items="${sessionScope.impliedMemberships}" var="membership">
                            <c:choose>
                                <c:when test="${post.permissionGroup.equals(membership)}">
                                    <option value="${membership}" selected>${membership}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${membership}">${membership}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary mt-2 mi">Update Post</button>
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