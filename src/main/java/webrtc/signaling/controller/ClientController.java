package webrtc.signaling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webrtc.signaling.repository.WebcamKeyRepository;
import webrtc.signaling.repository.WebcamRepository;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final WebcamRepository webcamRepository;

    @GetMapping("/client")
    public String clientChannels(Model model) {

        List<String> allDisplayInfo = webcamRepository.getAllDisplayInfo();
        model.addAttribute("allDisplayInfo", allDisplayInfo);

        return "client/channel";
    }

    @GetMapping("/client/{displayInfo}")
    public String clientChannel(@PathVariable String displayInfo, Model model) {

        String camKey = webcamRepository.getWebcamKeyByDisplayInfo(displayInfo);
        model.addAttribute("camKey", camKey);

        return "client/channel-choose";
    }

    @GetMapping("/client/redirect")
    public String clientRedirect(){
        return "client/redirect";
    }
}

