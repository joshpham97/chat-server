function getPosts() {
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

    const queryString = buildQueryString(false); // To preserve the query string (i.e. filter)
    const previousDisabled = (currentPage == 1) ? ' disabled' : '';
    const nextDisabled = (currentPage == pages) ? ' disabled' : '';

    // Previous nav
    $("#pagination").append(
        "<span class=\"page-item" + previousDisabled + "\">" +
            "<a class=\"page-link\" href=\"?" + queryString + "page=" + (currentPage - 1) + "\">Previous</a>" +
        "</span>"
    );

    // Page number navs
    for (let i = 1; i <= pages; i++) {
        if(i == currentPage) { // Active page
            $("#pagination").append(
                "<span class=\"page-item active\">" +
                    "<a class=\"page-link\">" + i + "</a>" +
                "</span>"
            );
        }
        else {
            $("#pagination").append(
                "<span class=\"page-item\">" +
                    "<a class=\"page-link\" href=\"?" + queryString + "page=" + i + "\">" + i + "</a>" +
                "</span>"
            );
        }
    }

    // Next nav button
    $("#pagination").append(
        "<span class=\"page-item" + nextDisabled + "\">" +
            "<a class=\"page-link\" href=\"?" + queryString + "page=" + (currentPage + 1) + "\">Next</a>" +
        "</span>"
    );
}

$(document).ready(function() {
    getPosts();
});