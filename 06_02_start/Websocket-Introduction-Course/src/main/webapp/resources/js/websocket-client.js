/**
 * Created by readlearncode.com on 29/11/2016.
 * Leaner Chinatsu Kawakami
 */
var wsocket;
var serviceLocation = "ws://localhost:8080/dukechat/chat";
var $nickName;
var $message;
var $chatWindow;
var $alert;
var room = '';
var user = '';

$(document).ready(function () {
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $alert = $('#alert');
    $('.chat-wrapper').hide();

    $('#enterRoom').click(function (evt) {
        evt.preventDefault();

        // Extract room selected from drop-down
        room = $('#chatroom option:selected').val();
        user = $('#nickname').val();

        // Connect to chat server
        connectToChatServer();

        $('.chat-wrapper h2').html("Welcome " + user + "! <br/>You are in the " + room + " chat room.");
        $('.chat-signin').hide();
        $('.chat-wrapper').show();
        $message.focus();
    });
    $('#do-chat').submit(function (evt) {
        evt.preventDefault();
        sendMessage()
    });

    $('#leave-room').click(function () {
        leaveRoom();
    });
});


function connectToChatServer() {
    // Create websocket connection to server endpoint URI
    // TODO: Connect to endpoint
    wsocket = new WebSocket(constructURI(serviceLocation,encodeURI(room),user));


    // Set message and error handlers
    wsocket.onerror = onConnectionError;
    wsocket.onmessage = onMessageReceived;
    // TODO: add callback for error events
}

function onMessageReceived(evt) {
    // Parse JSON String to JavaScript Object
    // TODO: deserialise the JSON string to JavaScript object;
      var msg = JSON.parse)(evt.data);
    // Construct HTML snippet and print to screen
    var $messageLine = constructHTMLSnippet(msg.sender, msg.content, msg.received);
    $chatWindow.append($messageLine);
}

function onConnectionError(evt) {
    // Print error to chat window
    $alert.append($(evt));
}

function sendMessage() {
    // Construct message to send to server
    var msg = '{"content":"' + $message.val() + '", "sender":"' + user + '", "received":"' + '"}';
    wsocket.send(msg);
    // Put back focus
    $message.val('').focus();
}

function leaveRoom() {
    wsocket.close();
    $chatWindow.empty();
    $('.chat-wrapper').hide();
    $('.chat-signin').show();
}


function constructURI(serviceLocation, room, user) {
    return serviceLocation + "/" + room + "/" + user;
}

function constructHTMLSnippet(nickName, content, received) {
    return $('<tr><td class="received">' + received.substring(0, 8)
        + '</td><td class="user">' + nickName
        + '</td><td class="message">' + content
        + '</td></tr>');
}
