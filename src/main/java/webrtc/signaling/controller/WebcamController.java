package webrtc.signaling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebcamController {

    @GetMapping("/webcam")
    public String videoStream() {
        return "webcam/webcam-streaming";
    }
}
