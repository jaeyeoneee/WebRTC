const video = document.getElementById("localVideo");
const myCamKey = Math.random().toString(36).substring(2, 12);
let pcListMap = new Map();
let localStream;

async function getLocalStream(){
    try {
        localStream = await navigator.mediaDevices.getUserMedia({video: true, audio: false});
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

            var localPeer = createPeer();
            localPeer.setRemoteDescription(new RTCSessionDescription(userSDP))
            pcListMap.set(userKey, localPeer);
            sendAnswer(userKey);
        })

        stompClient.subscribe('/queue/webcam/iceCandidate/' + myCamKey, (iceCandidate)=>{

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

function createPeer(){
    let peer = new RTCPeerConnection();

    // begin sending the local video across the peer connection
    localStream.getTracks().forEach((track) => {
        peer.addTrack(track, localStream);
    })

    peer.onicecandidate = (event) => {
        // candidate message 만들고, 서버의 웹캠 엔드포인트에 전송해준다.
    }

    return peer;
}

getLocalStream();
connect();