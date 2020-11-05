<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Posts</title>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
        function getRecentPosts() {
            $.ajax({
                url: 'posts',
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

                $.each(posts, function (key, value) {
                    let post = '<div>' +
                        value.message +
                        '</div>';

                    $("#posts").append(post);
                });
            }
            else
                $("posts").text("No posts to display")
        }

        $(document).ready(function() {
            getRecentPosts();
        });
    </script>
</head>
<body>
    <div id="posts">
        No posts to display
    </div>
</body>
</html>
