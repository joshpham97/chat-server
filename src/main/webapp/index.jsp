<%@ page import="server.chat.model.PostList" %>
<%--
  Created by IntelliJ IDEA.
  User: Stefan JB
  Date: 2020-09-28
  Time: 9:06 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="posts" scope="request" class="server.chat.model.PostList" />
<jsp:include page="/PostServlet" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    String username = (String) session.getAttribute("username");
    if (null == username) {
        session.setAttribute("errorMessage", "You have to be logged in to access the home page ");
        response.sendRedirect("login.jsp");
    }
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>A simple Chat Server</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/utils.js"></script>
    </head>
    <body>
        <input id="refreshDate" type="text" style="display: none"/>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <span class="navbar-brand mb-0 h1">Simple Message Board</span>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="mb-0 nav-link active" href="index.jsp"><i class="fas fa-home mr-2"></i>Home</a>
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

        <div class="container mt-2">
            <div class="row mt-2">
                <div id="posts" class="col-12">
                    <c:choose>
                        <c:when test="${requestScope.posts.getSize() > 0}">
                            <c:forEach items="${requestScope.posts.getPosts()}" var="post">
                                <div class="card mb-2">
                                    <div class="card-header">
                                        <div class="float-left text-muted">
                                            <span class="font-weight-bold">${post.getTitle()}</span>
                                            <span>by ${post.getUsername()}</span>
                                            <small><i class="far fa-clock pr-1"></i>${post.getDatePostedStr()}</small>
                                        </div>

                                        <div class="float-right">
                                            <c:if test="${post.getAttID() != null}">
                                                    <a href="AttachmentServlet?action=get&attachmentId=${post.getAttID()}"><i class="fas fa-paperclip mr-2" title="Download attachment"></i></a>
                                            </c:if>
                                            <c:if test="${post.getUsername().equals(sessionScope.username)}">
                                                <a href="PostEditServlet?postId=${post.getPostID()}"><i class="fas fa-edit mr-2"></i></a>
                                                <a href="PostServlet?action=delete&postID=${post.getPostID()}"><i class="fas fa-trash mr-2"></i></a>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div>
                                        <div class="card-body ">
                                            <div>${post.getMessage()}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            No posts to display
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${requestScope.pages > 0}">
                <div class="mt-3 mb-3 text-center">
                    <div id="pagination" class="pagination d-inline-flex">
                        <c:choose>
                            <c:when test="${requestScope.currentPage == 1}">
                                <span class="page-item disabled">
                                    <a class="page-link">Previous</a>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="page-item">
                                    <a class="page-link" href="?${requestScope.queryString}&page=${requestScope.currentPage - 1}">Previous</a>
                                </span>
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="i" begin="1" end="${requestScope.pages}">
                            <c:choose>
                                <c:when test="${i == requestScope.currentPage}">
                                    <span class="page-item active">
                                        <a class="page-link">${i}</a>
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="page-item">
                                        <a class="page-link" href="?${requestScope.queryString}&page=${i}">${i}</a>
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${requestScope.currentPage == requestScope.pages}">
                                <span class="page-item disabled">
                                    <a class="page-link">Next</a>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="page-item">
                                    <a class="page-link" href="?${requestScope.queryString}&page=${requestScope.currentPage + 1}">Next</a>
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>
    </body>
</html>
