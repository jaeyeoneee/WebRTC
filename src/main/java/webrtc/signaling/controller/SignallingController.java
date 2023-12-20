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

        //TODO:diplayName 변경
        Webcam webcam = new Webcam(sessionId, initiateKey, String.valueOf(++sequence));
        webcamRepository.save(webcam);

        log.info("new webcam={}", webcam);
    }
}
