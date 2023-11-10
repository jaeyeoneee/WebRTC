const video = document.getElementById("localVideo");
const myCamKey = Math.random().toString(36).substring(2, 12);
let pcListMap = new Map();
let localStream;

const configuration = {
    iceServers: [
        {
            urls: "stun:stun.l.google.com:19302" // STUN 서버 주소
        }
    ]
};

async function getLocalStream(){
    try {
        localStream = await navigator.mediaDevices.getUserMedia({video: true, audio: true});
        video.srcObject = localStream;
        console.log("succeed localVideo");
    } catch (err) {
        //TODO
        console.log("fail local video");
    }
}

async function connect(){
    // connect to server, test done
    const socket = new SockJS('/signaling');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        console.log(frame);

        stompClient.subscribe('/queue/webcam/offer/' + myCamKey,  (offer) =>{
            var userKey = JSON.parse(offer.body)["userKey"];
            var userSDP = JSON.parse(offer.body)["offer"];
            console.log("userKey="+userKey+", userSDP="+userSDP);

            var localPeer = createPeer(userKey);
            localPeer.setRemoteDescription(new RTCSessionDescription(userSDP))
            pcListMap.set(userKey, localPeer);
            sendAnswer(userKey);
        })

        stompClient.subscribe('/queue/webcam/iceCandidate/' + myCamKey, (iceCandidate)=>{
            var userKey = JSON.parse(iceCandidate.body)["userKey"];
            var message = JSON.parse(iceCandidate.body)["candidate"];

            var localPeer = pcListMap.get(userKey);
            console.log("localpeer is undefined test");
            console.log(localPeer);


            candidate = new RTCIceCandidate({
                candidate: message.candidate,
                sdpMLineIndex: message.sdpMLineIndex,
                sdpMid: message.sdpMid
            })

            localPeer.addIceCandidate(candidate);

            console.log("candidate accepted");
            console.log(candidate);
            console.log(localPeer);
        })

        //TODO:subscribe가 보장
        stompClient.send('/app/webcam/initiate', {}, myCamKey);

    })
}

function sendAnswer(userKey){
    let localPeer = pcListMap.get(userKey);
    localPeer.createAnswer().then(description => {
        localPeer.setLocalDescription(description);
        console.log("setting Local Description");
        console.log(description);
        stompClient.send("/app/webcam/answer/"+userKey, {}, JSON.stringify({
            "camKey" : myCamKey,
            "answer" : description
        }));
    })
}

function createPeer(userKey){
    let peer = new RTCPeerConnection(configuration);
    console.log("createPeer called");

    // begin sending the local video across the peer connection
    localStream.getTracks().forEach((track) => {
        peer.addTrack(track, localStream);
        console.log("track added");
    })

    peer.onicecandidate = (event) => {
        console.log("iceCandidate create!");
        if (event.candidate){
            stompClient.send("/app/webcam/iceCandidate/"+userKey, {}, JSON.stringify({
                "camKey" : myCamKey,
                "candidate" : event.candidate
            }))
        }
    }
    console.log("peer created!");
    console.log(peer);
    return peer;
}

getLocalStream();
connect();