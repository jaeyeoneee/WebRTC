package webrtc.signaling.repository;

import org.springframework.stereotype.Repository;
import webrtc.signaling.domain.Webcam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WebcamRepository {

    private final List<Webcam> webcamList = new ArrayList<>();

    // 새로운 webcam이 들어왔을 때 저장
    public void save(Webcam webcam) {
        webcamList.add(webcam);
    }

    // user에게 webcam display & 선택한 webcam key 전달
    public List<String> getAllDisplayInfo() {
        return webcamList.stream()
                .map(Webcam::getDisplayInfo)
                .collect(Collectors.toList());
    }

    public String getWebcamKeyByDisplayInfo(String displayInfo) {
        return webcamList.stream()
                .filter(webcam -> webcam.getDisplayInfo().equals(displayInfo))
                .findFirst()
                .map(Webcam::getWebcamKey)
                .orElse(null);
    }

    // 웹캠 삭제
    public boolean hasWebcamBySessionId(String sessionId){
        return webcamList.stream()
                .anyMatch(webcam -> webcam.getWebcamSessionID().equals(sessionId));
    }

    public void removeWebcamBySessionId(String sessionId) {
        webcamList.removeIf(webcam -> webcam.getWebcamSessionID().equals(sessionId));
    }

    // 사용자 웹캠 연결 시 추가
    public Optional<Webcam> getWebcamByWebcamSessionId(String sessionId) {
        return webcamList.stream()
                .filter(webcam -> webcam.getWebcamSessionID().equals(sessionId))
                .findFirst();
    }

    public Optional<Webcam> getWebcamByWebcamDisplayName(String displayName) {
        return webcamList.stream()
                .filter(webcam -> webcam.getDisplayInfo().equals(displayName))
                .findFirst();
    }
    
    //TODO: repository 함수 수정


    public void clear(){
        webcamList.clear();
    }

}
