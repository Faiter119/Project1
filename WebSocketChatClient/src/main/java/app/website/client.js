//When this file is fully loaded, initialize board with context
$(document).ready(function(){

    var ws = new WebSocket("ws://localhost:8080"); // hvis du bruke localhost funke det ikke på andre maskina

    ws.onerror = (error) => {
        $("#connection_label").html("Not connected");
    };
    ws.onopen = () => {
        $("#connection_label").html("Connected");
    };

    ws.onmessage = (event) => {

        console.log(event);

        $('#tableOfMessages tr:last')
            .after(
                "<tr>"+
                "<td>"+event.data+"</td>"+
                "</tr>");

    };

    $("#sendButton").click(function() {

        var message = $("#messageInput").val();

        ws.send(message);

        $("#messageInput").val("");

    });

    $("#loginButton").click(function () {

      var userName = $("#userNameInput").val();
      var password = $("#passWordInput").val();

      ws.send(JSON.stringify({userName, password}))

    })

});
