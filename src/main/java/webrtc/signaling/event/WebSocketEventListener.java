package webrtc.signaling.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import webrtc.signaling.repository.WebcamRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebcamRepository webcamRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        boolean isWebcam = webcamRepository.hasWebcamBySessionId(sessionId);
        if (isWebcam) {
            webcamRepository.removeWebcamBySessionId(sessionId);
            log.info("delete webcam={}", sessionId);
        }
        // client인 경우
    }
}
