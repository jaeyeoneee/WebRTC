const remoteVideo = document.getElementById("remoteVideo");
const myLocalKey = Math.random().toString(36).substring(2, 12);
let localPeer;

async function connect() {
    return new Promise((resolve, reject) => {
        // connect to server, test done
        const socket = new SockJS('/signaling');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            console.log(frame);

            localPeer = createPeer();

            var subscription = stompClient.subscribe("/queue/webcam/answer/" + myLocalKey, (answer) => {
                var camSDP = JSON.parse(answer.body)["answer"];
                localPeer.setRemoteDescription(new RTCSessionDescription(camSDP));
                console.log(localPeer);
            });
            if (localPeer && subscription){
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

    peer.onicecandidate = (event) => {
        // candidate message 만들고, 서버의 웹캠 엔드포인트에 전송해준다.
    }

    return peer;
}

async function main() {
    await connect(); // connect 함수가 완료될 때까지 기다림
    sendOffer(); // connect 함수 완료 후 sendOffer 함수 실행
}

main(); // main 함수를 호출하여 실행
