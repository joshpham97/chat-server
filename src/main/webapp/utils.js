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
    let data = {
        "username": $("#usernameHidden").val(),
        "postMessage": $("#message").val()
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

function addMessages(messages){
    if (messages.length > 0){
        let placeholder = $("#noMessagePlaceholder");
        if(placeholder.length){
            placeholder.remove();
        }

        $.each(messages, function (index, value){
            let newMessage = '<div class="row mb-1 message rounded">' +
                '<small class="senderName textSecondary m-1">' + value.username + '</small>' +
                '<div class="m-1 messageContent">' + value.message + '</div>' +
                '</div>'
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
        document.styleSheets[1].disabled = false;
        document.styleSheets[2].disabled = true;
    }else{
        document.styleSheets[2].disabled = false;
        document.styleSheets[1].disabled = true;
    }
}

//Set up the refresh functionality
let refreshDate = new Date();
//Get all messages from 2 weeks ago
refreshDate.setDate(refreshDate.getDate() - 14);
$('#refreshDate').val(formatDate(refreshDate));

setInterval(refresh, 1000*2);

//Set up the theme switching functionality
document.styleSheets[1].disabled = false;
document.styleSheets[2].disabled = true;
