function getRecentPosts() {
    // Build the query string
    let query = buildQueryString();

    $.ajax({
        url: 'PostServlet?' + query,
        type: 'GET',
        success: function(response) {
            const parsedResponse = JSON.parse(response);

            displayPosts(JSON.parse(parsedResponse.posts));
            displayPagination(parsedResponse.pages, parsedResponse.currentPage);
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
        $("#posts").text("No posts to display")
}

function buildQueryString(includePage = true) {
    // Get query string params
    const urlSearchParams = new URLSearchParams(window.location.search)
    const queryParams = urlSearchParams.entries();

    // Build the query string
    let query = '';
    if(includePage) {
        for (const [k, v] of queryParams)
            if (v != '')
                query += k + '=' + v + '&';
    }
    else {
        for (const [k, v] of queryParams)
            if (v != '' && k != 'page') // Don't consider page param
                query += k + '=' + v + '&';
    }

    return query;
}

function displayPagination(pages, currentPage) {
    if(pages == 1) // No need for pagination
        return;

    let queryString = buildQueryString(false);

    // Previous nav
    if(currentPage == 1) { // First page: disable previous nav
        $("#pagination").append(
            "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">Previous</a></li>"
        );
    }
    else {
        $("#pagination").append(
            "<li class=\"page-item\"><a class=\"page-link\" href=\"?" + queryString + "page=" + (currentPage - 1) + "\">Previous</a></li>"
        );
    }

    // Page number navs
    for (let i = 1; i <= pages; i++) {
        if(i == currentPage) { // Active page
            $("#pagination").append(
                "<li class=\"page-item active\"><a class=\"page-link\">" + i + "</a></li>"
            );
        }
        else {
            $("#pagination").append(
                "<li class=\"page-item\"><a class=\"page-link\" href=\"?" + queryString + "page=" + i + "\">" + i + "</a></li>"
            );
        }
    }

    // Next nav button
    if(currentPage == pages) { // Last page: disable next nav
        $("#pagination").append(
            "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">Next</a></li>"
        );
    }
    else {
        $("#pagination").append(
            "<li class=\"page-item\"><a class=\"page-link\" href=\"?" + queryString + "page=" + (currentPage + 1) + "\">Next</a></li>"
        );
    }
}

$(document).ready(function() {
    getRecentPosts();
});