//connecting to our signaling server
var conn = new WebSocket('ws://localhost:8080/socket');

conn.onopen = function() {
    console.log("Connected to the signaling server");
    initialize();
};

conn.onmessage = function(msg) {
    console.log("Got message", msg.data);
    var content = JSON.parse(msg.data);
    var data = content.data;
    switch (content.event) {
    // when somebody wants to call us
    case "offer":
        handleOffer(data);
        break;
    case "answer":
        handleAnswer(data);
        break;
    // when a remote peer sends an ice candidate to us
    case "candidate":
        handleCandidate(data);
        break;
    default:
        break;
    }
};

function send(message) {
    conn.send(JSON.stringify(message));
}

var peerConnection;
var dataChannel;
var input = document.getElementById("messageInput");

function initialize() {
    var configuration = null;

    peerConnection = new RTCPeerConnection(configuration, {
        optional : [ {
            RtpDataChannels : true
        } ]
    });

    // Setup ice handling
    peerConnection.onicecandidate = function(event) {
        if (event.candidate) {
            send({
                event : "candidate",
                data : event.candidate
            });
        }
    };

    // creating data channel
    dataChannel = peerConnection.createDataChannel("dataChannel", {
        reliable : true
    });

    dataChannel.onerror = function(error) {
        console.log("Error occured on datachannel:", error);
    };

    // when we receive a message from the other peer, printing it on the console
    dataChannel.onmessage = function(event) {
        console.log("message:", event.data);
    };

    dataChannel.onclose = function() {
        console.log("data channel is closed");
    };
}

function createOffer() {
    peerConnection.createOffer(function(offer) {
        send({
            event : "offer",
            data : offer
        });
        peerConnection.setLocalDescription(offer);
    }, function(error) {
        alert("Error creating an offer");
    });
}

function handleOffer(offer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

    // create and send an answer to an offer
    peerConnection.createAnswer(function(answer) {
        peerConnection.setLocalDescription(answer);
        send({
            event : "answer",
            data : answer
        });
    }, function(error) {
        alert("Error creating an answer");
    });

};

function handleCandidate(candidate) {
    peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
};

function handleAnswer(answer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
    console.log("connection established successfully!!");
};

function sendMessage() {
    dataChannel.send(input.value);
    input.value = "";
}

//var connection = new WebSocket('ws://localhost:8080/socket');
//
////const constraints {
////    video: true,
////    audio: true
////}
//
//var constraints = {
//    video : {
//        frameRate : {
//            ideal : 10,
//            max : 15
//        },
//        width : 1280,
//        height : 720,
//        facingMode : "user"
//    },
//
//    audi: true
//};
//
//var configuration = {
//    "iceServers" : [ {
//        "url" : "stun:stun2.1.google.com:19302"
//    } ]
//};
//
//function sendMessage(message) {
//    connection.send(JSON.stringify(message));
//};
//
//var peerConnection = new RTCPeerConnection(configuration, {
//    optional : [{
//        RtpDataChannels : true
//    }]
//});
//
//var dataChannel = peerConnection.createDataChannel("dataChannel", {reliable : true});
//
//dataChannel.onerror = function(error) {
//    console.log("Error: ", error)
//};
//
//dataChannel.onclose = function() {
//    console.log("Data channel is closed!")
//};
//
//// Send method makes a call to the signaling server to pass the offer information.
//peerConnection.createOffer(function(offer) {
//    send({
//        event: "offer",
//        data: offer
//    });
//    peerConnection.setLocalDescription(offer);
//}, function(error) {
//    console.log("ERROR: ", error);
//});
//
//// Handle the ICE candidates
//peerConnection.onicecandidate = function(event) {
//    if (event.candidate) {
//        send({
//            event : "candidate",
//            data : event.candidate
//        });
//    }
//};
//// Process the ICE candidate sent by the other peer.
//// The remote peer, upon receiving this candidate, should add it to its candidate pool.
//peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
//
//// When the other peer receives the offer, it must set it as the remote description.
//peerConnection.setRemoteDescription(new RTCSesionDescription(offer));
//peerConnection.createAnswer(function(answer) {
//    peerConnection.setLocalDescription(answer);
//        send({
//            event : "answer",
//            data: answer
//        })
//}, function(error) {
//    console.log("ERROR: ", error);
//});
//
//// The initiating peer receives the answer and sets it as the remote description
////handleAnswer(answer) {
////    peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
////}
//
//// Send a message to server.
//dataChannel.send("message")
//
//dataChannel.onmessage = function(event) {
//    console.log("Message: ", event.data);
//};
//
//navigator.mediaDevices.getUserMedia(constraints)
//         .then(function(stream) { /* stream */})
//         .catch(function(error) { console.log("ERROR: ", error); })
//
//peerConnection.addStream(stream);
//
//// Receive the stream on the remote peer, we can create a listener
//peerConnection.onaddstream = function(event) {
//    videoElement.srcObject = event.stream;
//};
//
//
