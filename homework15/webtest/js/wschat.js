var ws;
document.getElementById("connect").style.backgroundColor='red';
var isconnected;
isconnected = false;
function doConnection() {
    if (!isconnected) {
        console.log("Connecting...");
        isconnected = true;
        init();
    } else {
        console.log("Disconnecting...");
        ws.close();
        isconnected = false;
    }
}
init = function () {
    ws = new WebSocket("ws://localhost:8080/homework15/chat");
    ws.onopen = function (event) {
		console.log("Connected");
        document.getElementById("connect").style.backgroundColor='green';
        document.getElementById("connect").value = 'Disconnect';
    }
    ws.onmessage = function (event) {
        var result = JSON.parse(event.data);
        //console.log("result: " + result);
        //console.log("result.content: " + result.content);
        document.getElementById("greeting").value = result.content;
        document.getElementById("name").value = result.sender;
        document.getElementById("counter").value = result.received;
        console.log("onmessage: " + event.data);
    }
    ws.onclose = function (event) {
        isconnected = false;
        console.log("Disconnected");
        document.getElementById("connect").style.backgroundColor='red';
        document.getElementById("connect").value = 'Connect';
    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}