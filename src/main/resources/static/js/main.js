'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageFormChat001 = document.querySelector('#messageForm-chat-001');
var messageFormChat002 = document.querySelector('#messageForm-chat-002');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

document.getElementById('subscribe-chat-001').addEventListener('click', function() {
    subscribeAndAddUser('1');
    const button = document.querySelector('#btn-chat-001');
    button.disabled = false;
});

function saveUserNameTosessionStorage(event) {
    event.preventDefault();
    username = document.querySelector('#name').value.trim();
    if(username) {
        sessionStorage.setItem("username", username);
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
    }
}

function subscribeAndAddUser(chatUuid) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        login: sessionStorage.getItem("username")
    }, function () {
        onConnected(chatUuid);
    }, onError);
    let connectingElement = document.querySelector(`.connecting-${chatUuid}`);
    connectingElement.classList.add('hidden');
}

function onConnected(chatUuid) {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public/'+chatUuid, onMessageReceived);
}

function sendMessage(event) {
    let chatUuid = event.currentTarget.name;
    let messageInput = document.querySelector(`#message-${chatUuid}`);
    let messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        let chatMessage = {
            sender: sessionStorage.getItem("username"),
            content: messageInput.value,
            chatUuid: chatUuid,
            type: 'CHAT'
        };
        stompClient.send("/app/chat-sendMessage/"+chatUuid, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {

    let message = JSON.parse(payload.body);
    console.log(message.chatUuid);

    let messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);
    let chatUuid = message.chatUuid;
    let messageArea = document.querySelector(`#messageArea-${chatUuid}`);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function onError(error) {
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
    // sessionStorage.removeItem("username");
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', saveUserNameTosessionStorage, true)
messageFormChat001.addEventListener('submit', sendMessage, true)
messageFormChat002.addEventListener('submit', sendMessage, true)