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