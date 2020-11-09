function getRecentPosts() {
    // Get query string params
    const urlSearchParams = new URLSearchParams(window.location.search)
    const username = urlSearchParams.get("username");

    // Build query string
    let query = '';
    if(username)
        query += 'username=' + username;

    $.ajax({
        url: 'PostServlet?' + query,
        type: 'GET',
        success: function(response) {
            displayPosts(JSON.parse(response));
        },
        error: function(e) {
            alert(e.responseText);
        }
    });
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