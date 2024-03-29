const PORT = 8443;
const MAPPING = "/socket";
const peerConnectionConfig = {
    'iceServers': [
        {'urls': 'stun:stun.l.google.com:19302'}
    ]
};

var ws;
var localStream;
var connections = {};
var uuidInBig;

var container = document.getElementById("remoteVideosContainer");

/**
 * this initiate websocket connection
 * it is caled on page reload
 */
function init() {

	// get a local stream, show it in a self-view and add it to be sent
	navigator.mediaDevices.getUserMedia({video: true, audio: false}).then(function (stream) {
		console.log("Stream OK");
		localStream = stream;
		console.log(stream)
		selfView.srcObject = localStream;
		ws = new WebSocket('wss://' + window.location.hostname + ':' + PORT + MAPPING);
		ws.onmessage = processWsMessage;
		ws.onopen = logMessage;
		ws.onclose = logMessage;
		ws.onerror = logMessage;

	    console.log("Stream OK2");
	}).catch(function (error) {
		console.log("Stream NOT OK: " + error.name + ': ' + error.message);
	});

}

function processWsMessage(message) {
	var signal = JSON.parse(message.data);
    logMessage(signal);
	// you have logged in
	console.log(signal.type)
	switch (signal.type) {
		case 'INIT':
			handleInit(signal);
			break;
		case 'LOGOUT':
			handleLogout(signal);
			break;
		case 'OFFER':
            handleOffer(signal);
            break;
        case 'ANSWER':
             handleAnswer(signal);
             break;
         case 'ICE':
             handleIce(signal);
             break;
	}

	console.log("Stream OK3");

}

function handleInit(signal) {
    var peerId = signal.sender;
    var connection = getRTCPeerConnectionObject(peerId);
console.log("Stream OK4");
    // make an offer, and send the SDP to sender.
    connection.createOffer().then(function (sdp) {
        connection.setLocalDescription(sdp);
        console.log('Creating an offer for', peerId);
        sendMessage({
             type: "OFFER",
             receiver: peerId,
             data: sdp,
             roomId: 10
        });
    }).catch(function(e) {
        console.log('Error in offer creation.', e);
    });
console.log("Stream OK5");
}

function handleLogout(signal) {
    var peerId = signal.sender;
    if(peerId == uuidInBig) {
        remoteView.srcObject = null;
    }
    delete connections[peerId];
    var videoElement = document.getElementById(peerId);
    videoElement.outerHTML = "";
    delete videoElement;
}

function handleOffer(signal) {
    var peerId = signal.sender;
    var connection = getRTCPeerConnectionObject(peerId);
    connection.setRemoteDescription(new RTCSessionDescription(signal.data)).then(function () {
        console.log('Setting remote description by offer from ' + peerId);
        // create an answer for the peedId.
        connection.createAnswer().then( function(sdp) {
            // and after callback set it locally and send to peer
            connection.setLocalDescription(sdp);
            sendMessage({
                type: "ANSWER",
                receiver: peerId,
                data: sdp,
                roomId: 10
            });

        }).catch(function(e) {
          console.log('Error in offer handling.', e);
        });

    }).catch(function(e) {
        console.log('Error in offer handling.', e);
    });
}

function handleAnswer(signal) {
    var connection = getRTCPeerConnectionObject(signal.sender);
    connection.setRemoteDescription(new RTCSessionDescription(signal.data)).then(function() {
        console.log('Setting remote description by answer from' + signal.sender);
    }).catch(function(e) {
        console.log('Error in answer acceptance.', e);
    });
}

function handleIce(signal) {
    if (signal.data) {
        console.log('Adding ice candidate');
        var connection = getRTCPeerConnectionObject(signal.sender);
        connection.addIceCandidate(new RTCIceCandidate(signal.data));
    }getRTCPeerConnectionObject
}

function getRTCPeerConnectionObject(uuid) {
console.log("Stream OK66");
    if(connections[uuid]) {
        return connections[uuid];
    }

    var connection = new RTCPeerConnection(peerConnectionConfig);
//    var connection = new RTCPeerConnection();
    connection.addStream(localStream);

    // handle on ice candidate
    connection.onicecandidate = function (event) {
        console.log("candidate is: " + event.candidate);
        if (event.candidate) {
          sendMessage({
                type: "ICE",
                receiver: uuid,
                data: event.candidate,
                roomId: 10
              });
        }
    };

    // handle on track / onaddstream
    connection.onaddstream = function(event) {
        console.log('Received new stream from ' + uuid);
        var video = document.createElement("video");
        container.appendChild(video);
        video.id = uuid;
        video.width=160;
        video.height=120;
        video.className += " videoElement";
        video.autoplay = true;
        video.srcObject = event.stream;
        video.addEventListener('click', function (event) {
            setBigVideo(uuid);
        }, false);
        if (!remoteView.srcObject) {
            setBigVideo(uuid);
        }
    };

    connections[uuid] = connection;
    return connection;
    console.log("Stream OK77");
}

function setBigVideo(uuid) {
    remoteView.srcObject = document.getElementById(uuid).srcObject;
    if(uuidInBig && document.getElementById(uuidInBig)) {
        document.getElementById(uuidInBig).classList.remove("active");
    }
    document.getElementById(uuid).classList.add("active");
    uuidInBig = uuid;
}

function sendMessage(payload) {
  ws.send(JSON.stringify(payload));
}

function logMessage(message) {
	console.log(message);
}

function disconnect() {
	console.log('Disconnecting ');
	if(ws != null) {
		ws.close();
	}
}

// start
window.onload = init;