function refresh(){
    let newRefreshDate = new Date()
    let query = "from=" + $("#refreshDate").val()
    $.ajax({
        url: 'Servlet?' + query,
        type: 'PUT',
        success: function(data) {
            console.log(data);
            addMessages(JSON.parse(data));
            $("#refreshDate").val(formatDate(newRefreshDate));
        }
    })
}

function sendMessage(){
    if($("#message").val() == ""){
        alert("Please enter some message before sending");
    }else{
        let data = {
            "username": $("#usernameHidden").val(),
            "message": $("#message").val()
        };
        $.ajax({
            url: 'Servlet',
            type: 'POST',
            data: data,
            success: function(data) {
                $("#message").val(""); //Empty the field
            }
        })
    }
}

function addMessages(messages){
    if (messages.length > 0){
        let placeholder = $("#noMessagePlaceholder");
        if(placeholder.length){
            placeholder.remove();
        }

        $.each(messages, function (index, value){
            let newMessage = '<div class="row mb-1 message rounded">' +
                '<small class="senderName textSecondary m-1">' + value.username + '</small>' +
                '<div class="m-1 messageContent rounded">' + value.message + '</div>' +
                '</div>';

            $("#messagesContainer").append(newMessage);
        })
    }
}

function formatDate(date){
    let strDate = "";
    let year = date.getFullYear();
    let month = (date.getMonth() + 1);
    month = (month < 10) ? "0" + month: month;
    let day = date.getDate();
    day = (day < 10) ? "0" + day: day;
    let minutes = date.getMinutes();
    minutes = (minutes < 10) ? "0" + minutes: minutes;
    let hours = date.getHours();
    hours = (hours < 10) ? "0" + hours: hours;
    let seconds = date.getSeconds();
    seconds = (seconds < 10) ? "0" + seconds: seconds;
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

function setUsername(){
    let person = prompt("Please choose a username", "Anonymous");
    if (person != null) {
        document.getElementById("usernameDisplay").innerHTML = person;
        document.getElementById("usernameHidden").value = person;
        document.getElementById("usernameNavBar").innerHTML = person;
    }
}

function switchTheme(){
    if (document.styleSheets[1].disabled) {
        if(confirm("Switch to default theme?")){
            document.styleSheets[1].disabled = false;
            document.styleSheets[2].disabled = true;
        }
    }else{
        if(confirm("Switch to dark theme?")) {
            document.styleSheets[2].disabled = false;
            document.styleSheets[1].disabled = true;
        }
    }
}

function deleteMessages(from, to){
    let query = "from=" + from + "&to=" + to;
    $.ajax({
        url: 'Servlet?' + query,
        type: 'DELETE',
        data: {
            from: from,
            to: to
        },
        success: function(data) {
            $("#messagesContainer").text("");

            let date = new Date();
            date.setDate(date.getDate() - 14);
            $('#refreshDate').val(formatDate(refreshDate));
            refresh();
        }
    })
}

//Set up the refresh functionality
let refreshDate = new Date();
//Get all messages from 2 weeks ago
refreshDate.setDate(refreshDate.getDate() - 14);
$('#refreshDate').val(formatDate(refreshDate));

$(document).ready(refresh);
setInterval(refresh, 1000*2);

//Set up the theme switching functionality
document.styleSheets[1].disabled = false;
document.styleSheets[2].disabled = true;

//Set up the delete messages functionality
$(document).on('click', '#deleteMessagesBtn', function(e) {
    e.preventDefault();
    const from = $('#deleteMessage_from').val();
    const to = $('#deleteMessage_to').val();
    deleteMessages(from, to);
});
