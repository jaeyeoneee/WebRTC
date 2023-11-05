let video = document.getElementById("localVideo");
let stompClient;
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

function connect(){
    // connect to server, test done
    var socket = new SockJS('/signaling');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        console.log(frame);
    })
}