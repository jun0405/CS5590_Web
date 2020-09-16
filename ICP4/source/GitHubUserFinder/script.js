function getGithubInfo(user) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "https://api.github.com/users/" + user, false);
    xhttp.send();
    return xhttp;
}

function showUser(user) {
    $("#pic").html("<img height='150' width='150' src='" + user.avatar_url + "'/>");
    $("#name").text("GitHub name: " + user.login);
    $("#id").text("GitHub id:" + user.id);
    $("#followers").text("GitHub followers count:" + user.followers);
    $("#following").text("GitHub Following count:" + user.following);
    $("#url").html("<a href='"+user.html_url+"' target='_blank'>GitHub Link</a>");
}

function noSuchUser(username) {
    alert("User " + username + " not found!!!");
}

$(document).ready(function () {
    $(document).on('keypress',  '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});