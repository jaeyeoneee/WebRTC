package webrtc.signaling.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Webcam {

    private String webcamSessionID;
    private String webcamKey;
    private String displayInfo;
    private List<String> clientList;

    public Webcam(String webcamSessionID, String webcamKey, String displayInfo) {
        this.webcamSessionID = webcamSessionID;
        this.webcamKey = webcamKey;
        this.displayInfo = displayInfo;
        this.clientList = new ArrayList<>();
    }
}
