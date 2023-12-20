package webrtc.signaling.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class webcamTest {

    @Test
    void addClientSessionIdTest() {
        Webcam webcam = new Webcam("1234", "1234", "test");

        webcam.addClient("clientSessionId");
        List<String> webcamClientList = webcam.getClientList();

        assertThat(webcamClientList.size()).isEqualTo(1);
        assertThat(webcamClientList.contains("clientSessionId")).isTrue();
    }

    @Test
    void deleteClientSessionIdTest() {
        Webcam webcam = new Webcam("1234", "1234", "test");

        webcam.addClient("clientSessionId");
        webcam.removeClient("clientSessionId");
        List<String> webcamClientList = webcam.getClientList();

        assertThat(webcamClientList.size()).isEqualTo(0);
    }
}