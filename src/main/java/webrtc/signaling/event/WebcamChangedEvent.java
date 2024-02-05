package webrtc.signaling.event;

import lombok.Getter;

import java.util.List;

public class WebcamChangedEvent {

    private List<String> webcamDisplayInfo;

    public WebcamChangedEvent(List<String> webcamDisplayInfo) {
        this.webcamDisplayInfo = webcamDisplayInfo;
    }

    public List<String> getWebcamDisplayInfo() {
        return webcamDisplayInfo;
    }
}
