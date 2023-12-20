package webrtc.signaling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webrtc.signaling.domain.Webcam;
import webrtc.signaling.repository.WebcamKeyRepository;
import webrtc.signaling.repository.WebcamRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final WebcamRepository webcamRepository;

    @Value("${connectionLimit}")
    private int connectionLimit;

    @GetMapping("/client")
    public String clientChannels(Model model) {

        List<String> allDisplayInfo = webcamRepository.getAllDisplayInfo();
        model.addAttribute("allDisplayInfo", allDisplayInfo);

        return "client/channel";
    }

    @GetMapping("/client/{displayInfo}")
    public String clientChannel(@PathVariable String displayInfo, Model model) {

        Webcam webcam = webcamRepository.getWebcamByWebcamDisplayName(displayInfo).get();

        // 만약 해당 cam에 허용 이상의 사용자가 들어오면 연결 불가 안내 페이지로 간다.
        if (webcam.getClientList().size() >= connectionLimit) {
            return "client/limit";
        }

        model.addAttribute("camKey", webcam.getWebcamKey());

        return "client/channel-choose";
    }

    @GetMapping("/client/redirect")
    public String clientRedirect(){
        return "client/redirect";
    }
}

