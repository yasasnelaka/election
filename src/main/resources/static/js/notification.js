var stompClient = null;

$(document).ready(function () {
    console.log("Index page is ready");
    connect();

    $("#send").click(function () {
        sendMessage();
    });

    $("#send-private").click(function () {
        sendPrivateMessage();
    });
});

function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function showMessage(message) {
    // $("#messages").append("<tr><td>" +  + "</td></tr>");
    // Display an info toast with no title
    localStorage.clear();
    if (message.canVote) {
        localStorage.setItem('vt', '1');
        setVisibleTrue();
    } else {
        localStorage.setItem('vt', '2');
        setVisibleFalse();
    }
    if (message.type === 1) {
        window.location.replace(message.message);
    } else {
        Swal.fire({
            icon: 'warning',
            title: "Notification!",
            text: message.message,
            // footer: '<a href="">Why do I have this issue?</a>'
        });
    }
}


function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}