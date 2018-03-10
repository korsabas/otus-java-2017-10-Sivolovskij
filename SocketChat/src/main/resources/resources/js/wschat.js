var ws;
document.getElementById("connect").style.backgroundColor='red';
var isconnected;
var receiver;
var context = document.getElementById("context").value;
var chatarea = document.getElementById("chatarea");

var newline = '\n';

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

    ws = new WebSocket("ws://" + window.location.host + context +"/messaging");
	
    ws.onopen = function (event) {
		console.log("Connected");
        document.getElementById("connect").style.backgroundColor='green';
        document.getElementById("connect").value = 'Disconnect';
        receiver = document.getElementById("receiver").value;
        document.getElementById("receiver").setAttribute("disabled","true");
        sendMessage();
    }
    ws.onmessage = function (event) {
        var result = JSON.parse(event.data);
        console.log("onmessage: " + event.data);
        chatarea.value += result.sender + ":" + result.text + newline;
    }
    ws.onclose = function (event) {
        isconnected = false;
        console.log("Disconnected");
        document.getElementById("connect").style.backgroundColor='red';
        document.getElementById("connect").value = 'Connect';
        document.getElementById("receiver").removeAttribute("disabled");
    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    //var receiverField = document.getElementById("receiver");
    var message = messageField.value;
    //var receiver = messageField.value;
    if (message.length > 0) { 
    chatarea.value += "you:" + message + newline;
    }
    ws.send(receiver + ";" + message);
    messageField.value = '';
}