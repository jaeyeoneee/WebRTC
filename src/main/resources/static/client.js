const remoteVideo = document.getElementById("remoteVideo");
const myLocalKey = Math.random().toString(36).substring(2, 12);
let localStream;
let localPeer;


async function getLocalStream(){
    try {
        localStream = await navigator.mediaDevices.getUserMedia({video: true, audio: true});
        console.log("succeed localVideo");
    } catch (err) {
        //TODO
        console.log("fail local video");
    }
}

async function connect() {
    return new Promise((resolve, reject) => {
        // connect to server, test done
        const socket = new SockJS('/signaling');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log(frame);

            localPeer = createPeer();

            var subscription1 = stompClient.subscribe("/queue/webcam/answer/" + myLocalKey, (answer) => {
                var camSDP = JSON.parse(answer.body)["answer"];
                localPeer.setRemoteDescription(new RTCSessionDescription(camSDP));
                console.log(localPeer);
            });

            var subscription2  = stompClient.subscribe('/queue/webcam/iceCandidate/' + myLocalKey, (iceCandidate)=>{
                        var camKey = JSON.parse(iceCandidate.body)["camKey"];
                        var message = JSON.parse(iceCandidate.body)["candidate"];

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

            if (localPeer && subscription1 && subscription2){
                resolve(); // stompClient.connect가 완료되면 resolve 호출
            }
        });
    });
}

function sendOffer() {
    localPeer.createOffer().then(description => {
        localPeer.setLocalDescription(description);
        stompClient.send("/app/webcam/offer/" + camKey, {}, JSON.stringify({
            "userKey": myLocalKey,
            "offer": description
        }));
    });
}

function createPeer() {
    let peer = new RTCPeerConnection();
    console.log(peer);

    peer.ontrack = (event) => {
        remoteVideo.srcObject = event.streams[0];
    }

    console.log("localSteam created");

    // begin sending the local video across the peer connection, need to delete after dummy track creation
    localStream.getTracks().forEach((track) => {
        peer.addTrack(track, localStream);
        console.log("track added");
    })

    peer.onicecandidate = (event) => {
         console.log("iceCandidate create!");
         if (event.candidate){
             stompClient.send("/app/webcam/iceCandidate/"+camKey, {}, JSON.stringify({
                 "userKey" : myLocalKey,
                 "candidate" : event.candidate
                 }))
            }
    }

    return peer;
}

async function main() {
    await getLocalStream();
    await connect(); // connect 함수가 완료될 때까지 기다림
    sendOffer(); // connect 함수 완료 후 sendOffer 함수 실행
}

main(); // main 함수를 호출하여 실행
