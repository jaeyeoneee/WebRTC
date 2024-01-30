const offerOptions = {'offerToReceiveAudio':true,'offerToReceiveVideo':true};
const remoteVideo = document.getElementById("remoteVideo");
const messageInput = document.getElementById("message");
const sendButton = document.getElementById("sendButton")
const myLocalKey = Math.random().toString(36).substring(2, 12);
let stompClient;
let localPeer;
let localChannel;

async function connect() {
    return new Promise((resolve, reject) => {
        var socket = new SockJS('/signaling');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {

            localPeer = createPeer();

            var answerSubscription = stompClient.subscribe("/queue/answer/" + myLocalKey, handleAnswer);
            var iceSubscription  = stompClient.subscribe('/queue/iceCandidate/' + myLocalKey, handleIceCandidate)

            if (localPeer && answerSubscription && iceSubscription){
                resolve();
            }
        });
    });
}

function handleAnswer(answer){
    var description = JSON.parse(answer.body)["description"];
    localPeer.setRemoteDescription(new RTCSessionDescription(description));
}

function handleIceCandidate(iceCandidate){
    var camKey = JSON.parse(iceCandidate.body)["camKey"];
    var description = JSON.parse(iceCandidate.body)["description"];

    var candidate = new RTCIceCandidate({
        candidate: description.candidate,
        sdpMLineIndex: description.sdpMLineIndex,
        sdpMid: description.sdpMid
    })

    localPeer.addIceCandidate(candidate);
}

function sendOffer() {
    localPeer.createOffer(offerOptions).then(description => {
        localPeer.setLocalDescription(description);
        stompClient.send("/app/offer/" + camKey, {}, JSON.stringify({
            "userKey": myLocalKey,
            "description": description
        }));
    });
}

// todo: peer, channel 변수 반환할지 결정
function createPeer() {
    var peer = new RTCPeerConnection();
    // reliable, ordered datachannel
    localChannel = peer.createDataChannel("chat", { ordered: true});

    peer.ontrack = (event) => {
        remoteVideo.srcObject = event.streams[0];
    }

    peer.onicecandidate = (event) => {
         if (event.candidate){
             stompClient.send("/app/iceCandidate/"+camKey, {}, JSON.stringify({
                 "userKey" : myLocalKey,
                 "description" : event.candidate
                 }))
            }
    }

    peer.oniceconnectionstatechange = (event) => {
        var iceConnectionState = peer.iceConnectionState;

        if (iceConnectionState === 'disconnected' || iceConnectionState === 'failed' || iceConnectionState === 'closed'){
            window.location.href = "/client/redirect";
        }
    }

    // 데이터 채널: 채팅
    localChannel.onopen = (event) => {
        console.log("Data Channel opened");
        sendButton.addEventListener("click", () => {
                const message = messageInput.value;
                localChannel.send(message);
                messageInput.value = "";
            });
    };

    localChannel.onmessage= (event) => {
        alert(event.data);
    }

    return peer;
}

async function main() {
    await connect();
    sendOffer();
}

main();