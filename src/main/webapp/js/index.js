function getRecentPosts() {
    // Build the query string
    let query = buildQueryString();

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

function buildQueryString() {
    // Get query string params
    const urlSearchParams = new URLSearchParams(window.location.search)
    const queryParams = urlSearchParams.entries();

    // Build the query string
    let query = '';
    for(const [k, v] of queryParams)
        if(v != '')
            query += k + '=' + v + '&';

    return query;
}

$(document).ready(function() {
    getRecentPosts();
});