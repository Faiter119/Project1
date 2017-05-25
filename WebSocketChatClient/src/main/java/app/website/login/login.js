//When this file is fully loaded, initialize board with context
$(document).ready(function(){

    var ws = new WebSocket("ws://localhost:8081"); // hvis du bruke localhost funke det ikke på andre maskina

    ws.onerror = (error) => {
        $("#connection_label").html("Not connected");
    };
    ws.onopen = () => {
        $("#connection_label").html("Connected");
    };

    ws.onmessage = (event) => {

        console.log(event.data);

    };


    $("#loginButton").click(function () {

      var userName = $("#userNameInput").val();

      var toSend = JSON.stringify({userName});


      ws.send(toSend);

    })

});
