const localVideo = document.getElementById("localVideo");
const myCamKey = Math.random().toString(36).substring(2, 12);
let pcListMap = new Map();
let localStream;
let stompClient;


async function getLocalStream(){
    try {
        localStream = await navigator.mediaDevices.getUserMedia({video: true, audio: true});
        localVideo.srcObject = localStream;
        console.log("succeed in getting localStream");
    } catch (err) {
        console.log("fail to get localStream");
    }
}

async function connect(){
    return new Promise((resolve, reject) => {
        var socket = new SockJS('/signaling');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {

            var offerSubscription = stompClient.subscribe('/queue/offer/' + myCamKey, handleOffer)
            var iceSubscription = stompClient.subscribe('/queue/iceCandidate/' + myCamKey, handleIceCandidate)

            if (offerSubscription && iceSubscription){
                stompClient.send('/app/initiate', {}, myCamKey);
                resolve();
            }
        });
    });
}

function handleOffer(offer){
    var userKey = JSON.parse(offer.body)["userKey"];
    var description = JSON.parse(offer.body)["description"];

    var localPeer = createPeer(userKey);
    pcListMap.set(userKey, localPeer);
    localPeer.setRemoteDescription(new RTCSessionDescription(description))
    sendAnswer(userKey);
}

function handleIceCandidate(iceCandidate){
    var userKey = JSON.parse(iceCandidate.body)["userKey"];
    var description = JSON.parse(iceCandidate.body)["description"];

    var localPeer = pcListMap.get(userKey);

    var candidate = new RTCIceCandidate({
        candidate: description.candidate,
        sdpMLineIndex: description.sdpMLineIndex,
        sdpMid: description.sdpMid
    })

    localPeer.addIceCandidate(candidate);
}

function sendAnswer(userKey){
    var localPeer = pcListMap.get(userKey);
    localPeer.createAnswer().then(description => {
        localPeer.setLocalDescription(description);
        stompClient.send("/app/answer/"+userKey, {}, JSON.stringify({
            "camKey" : myCamKey,
            "description" : description
        }));
    })
}

function createPeer(userKey){
    var peer = new RTCPeerConnection();

    localStream.getTracks().forEach((track) => {
        peer.addTrack(track, localStream);
    })

    peer.onicecandidate = (event) => {
        if (event.candidate){
            stompClient.send("/app/iceCandidate/"+userKey, {}, JSON.stringify({
                "camKey" : myCamKey,
                "description" : event.candidate
            }))
        }
    }

    peer.oniceconnectionstatechange = (event) => {
        var iceConnectionState = peer.iceConnectionState;
        if (iceConnectionState === 'connected'){
            stompClient.send("/app/connected", {}, userKey);
        }

        if (iceConnectionState === 'disconnected' || iceConnectionState === 'failed' || iceConnectionState === 'closed'){
            stompClient.send("/app/disconnected", {}, userKey);
            pcListMap.delete(userKey);
        }
    }

    return peer;
}

async function main() {
    await getLocalStream();
    await connect();
}

main();