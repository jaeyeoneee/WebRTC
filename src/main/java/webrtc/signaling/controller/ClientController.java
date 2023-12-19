package webrtc.signaling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webrtc.signaling.repository.WebcamKeyRepository;

import java.util.List;

@Slf4j
@Controller
public class ClientController {

    @Autowired
    WebcamKeyRepository webcamKeyRepository;

    @GetMapping("/client")
    public String clientChannels(Model model) {

        List<Long> ids = webcamKeyRepository.getIds();
        model.addAttribute("ids", ids);

        return "client/channel";
    }

    @GetMapping("/client/{camId}")
    public String clientChannel(@PathVariable Long camId, Model model) {

        String camKey = webcamKeyRepository.getKey(camId);
        model.addAttribute("camKey", camKey);

        return "client/channel-choose";
    }

    @GetMapping("/client/redirect")
    public String clientRedirect(){
        return "client/redirect";
    }
}

