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
           //데이터 가져오기 (추후 테스트 필요함)

        })

        stompClient.subscribe('/queue/webcam/iceCandidate/' + myCamKey, (iceCandidate)=>{

        })

        //TODO:subscribe가 보장
        stompClient.send('/app/webcam/initiate', {}, myCamKey);

    })
}


connect();