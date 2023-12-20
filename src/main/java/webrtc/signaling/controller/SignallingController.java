package webrtc.signaling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import webrtc.signaling.domain.Webcam;
import webrtc.signaling.repository.WebcamKeyRepository;
import webrtc.signaling.repository.WebcamRepository;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignallingController {

    private final WebcamRepository webcamRepository;
    private Long sequence = 0L;

    @MessageMapping("/offer/{webcamId}")
    @SendTo("/queue/offer/{webcamId}")
    public String sendOffer(@Payload String offer, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, offer={}", webcamId, offer);
        return offer;
    }

    @MessageMapping("/answer/{webcamId}")
    @SendTo("/queue/answer/{webcamId}")
    public String sendAnswer(@Payload String answer, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, answer={}", webcamId, answer);
        return answer;
    }

    @MessageMapping("/iceCandidate/{webcamId}")
    @SendTo("/queue/iceCandidate/{webcamId}")
    public String sendIceCandidate(@Payload String iceCandidate, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, candidate={}", webcamId, iceCandidate);
        return iceCandidate;
    }

    @MessageMapping("/initiate")
    public void webcamConnectionInitiate(@Payload String initiateKey, Message<String> message) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        String sessionId = headerAccessor.getSessionId();

        //TODO:displayName 변경
        Webcam webcam = new Webcam(sessionId, initiateKey, String.valueOf(++sequence));
        webcamRepository.save(webcam);

        log.info("new webcam={}", webcam);
    }

    @MessageMapping("/connected")
    public void webcamConnected(@Payload String userKey, Message<String> message) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        String sessionId = headerAccessor.getSessionId();

        // 웹캠을 sessionId를 이용해 찾은 다음 clientList에 해당 userKey를 넣어준다.
        Webcam webcam = webcamRepository.getWebcamByWebcamSessionId(sessionId).get();
        webcam.getClientList().add(userKey);
        log.info("CONNECTED: sessionId={}, key={}, displayName={}, clientList={}", webcam.getWebcamSessionID(), webcam.getWebcamKey(), webcam.getDisplayInfo(), webcam.getClientList());
    }

    @MessageMapping("/disconnected")
    public void webcamDisconnected(@Payload String userKey, Message<String> message) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
        String sessionId = headerAccessor.getSessionId();

        //웹캠을 sessionId를 이용해 찾은 다음 clientList에서 해당 userKey를 제거한다.
        Webcam webcam = webcamRepository.getWebcamByWebcamSessionId(sessionId).get();
        webcam.getClientList().remove(userKey);
        log.info("DISCONNECTED: sessionId={}, key={}, displayName={}, clientList={}", webcam.getWebcamSessionID(), webcam.getWebcamKey(), webcam.getDisplayInfo(), webcam.getClientList());
    }
}
