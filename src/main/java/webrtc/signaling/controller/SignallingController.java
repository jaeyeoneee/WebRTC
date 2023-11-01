package webrtc.signaling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignallingController {

    @MessageMapping("/webcam/offer/{webcamId}")
    @SendTo("/queue/webcam/offer/{webcamId}")
    public String sendOffer(@Payload String offer, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, offer={}", webcamId, offer);
        return offer;
    }

    @MessageMapping("/webcam/answer/{webcamId}")
    @SendTo("/queue/webcam/answer/{webcamId}")
    public String sendAnswer(@Payload String answer, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, answer={}", webcamId, answer);
        return answer;
    }

    @MessageMapping("/webcam/iceCandidate/{webcamId}")
    @SendTo("/queue/webcam/iceCandidate/{webcamId}")
    public String sendIceCandidate(@Payload String iceCandidate, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("id={}, candidate={}", webcamId, iceCandidate);
        return iceCandidate;
    }

}
