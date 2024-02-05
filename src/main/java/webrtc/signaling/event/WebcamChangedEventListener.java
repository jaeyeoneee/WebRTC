package webrtc.signaling.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WebcamChangedEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebcamChangedEventListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @EventListener
    public void webcamChangedEventListen(WebcamChangedEvent event){
        List<String> webcamDisplayInfo = event.getWebcamDisplayInfo();
        log.info("webcamDisplayInfo={}", webcamDisplayInfo.toString());
        simpMessagingTemplate.convertAndSend("/queue/updateDisplayInfo", webcamDisplayInfo);
    }
}
