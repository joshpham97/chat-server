function getRecentPosts() {
    $.ajax({
        url: 'PostServlet',
        type: 'GET',
        success: function(response) {
            displayPosts(JSON.parse(response));
        },
        error: function(e) {
            alert(e.responseText);
        }
    })
}

function displayPosts(posts) {
    if(posts.length > 0) {
        $("#posts").text("");

        $.get("templates/postTemplate.jsp", { "posts": JSON.stringify(posts) }, function(response) {
            $("#posts").append(response);
        });
    }
    else
        $("posts").text("No posts to display")
}

$(document).ready(function() {
    getRecentPosts();
});