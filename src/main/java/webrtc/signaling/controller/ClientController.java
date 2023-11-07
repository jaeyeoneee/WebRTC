package webrtc.signaling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webrtc.signaling.repository.WebcamKeyRepository;

import java.util.List;

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
    public String clientChannel(@PathVariable String camId, Model model) {

        model.addAttribute("camId", camId);

        return "client/channel-choose";
    }
}

