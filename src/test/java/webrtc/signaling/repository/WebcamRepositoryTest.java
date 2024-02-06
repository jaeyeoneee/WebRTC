package webrtc.signaling.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import webrtc.signaling.domain.Webcam;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class WebcamRepositoryTest {

    private final ApplicationEventPublisher eventPublisher = event -> {};
    private final WebcamRepository webcamRepository = new WebcamRepository(eventPublisher);

    @BeforeEach
    void beforeEach() {
        webcamRepository.clear();
    }

    @Test
    void getAllDisplayInfoTest(){
        Webcam webcam1 = new Webcam("testSessionId1", "testKey1", "testDisplay1");
        Webcam webcam2 = new Webcam("testSessionId2", "testKey2", "testDisplay2");
        Webcam webcam3 = new Webcam("testSessionId3", "testKey3", "testDisplay3");
        webcamRepository.save(webcam1);
        webcamRepository.save(webcam2);
        webcamRepository.save(webcam3);

        List<String> allDisplayInfo = webcamRepository.getAllDisplayInfo();

        assertThat(allDisplayInfo.size()).isEqualTo(3);
        assertThat(allDisplayInfo.contains("testDisplay1")).isTrue();
        assertThat(allDisplayInfo.contains("testDisplay2")).isTrue();
        assertThat(allDisplayInfo.contains("testDisplay3")).isTrue();
    }

    @Test
    void getWebcamKeyByDisplayInfo() {
        Webcam webcam1 = new Webcam("testSessionId1", "testKey1", "testDisplay1");
        Webcam webcam2 = new Webcam("testSessionId2", "testKey2", "testDisplay2");
        Webcam webcam3 = new Webcam("testSessionId3", "testKey3", "testDisplay3");
        webcamRepository.save(webcam1);
        webcamRepository.save(webcam2);
        webcamRepository.save(webcam3);

        String webcamKey = webcamRepository.getWebcamKeyByDisplayInfo("testDisplay1");

        assertThat(webcamKey).isEqualTo("testKey1");
    }

    @Test
    void hasWebcamBySessionIdTest() {
        Webcam webcam1 = new Webcam("testSessionId1", "testKey1", "testDisplay1");
        Webcam webcam2 = new Webcam("testSessionId2", "testKey2", "testDisplay2");
        Webcam webcam3 = new Webcam("testSessionId3", "testKey3", "testDisplay3");
        webcamRepository.save(webcam1);
        webcamRepository.save(webcam2);
        webcamRepository.save(webcam3);

        boolean hasSessionId = webcamRepository.hasWebcamBySessionId("testSessionId1");
        boolean hasFakeSessionId = webcamRepository.hasWebcamBySessionId("testFakeSessionId");

        assertThat(hasSessionId).isTrue();
        assertThat(hasFakeSessionId).isFalse();
    }

    @Test
    void getWebcamByWebcamSessionIdTest() {
        Webcam webcam1 = new Webcam("testSessionId1", "testKey1", "testDisplay1");
        webcamRepository.save(webcam1);

        Webcam webcam = webcamRepository.getWebcamByWebcamSessionId("testSessionId1").get();

        assertThat(webcam).isEqualTo(webcam1);
    }

    @Test
    void getWebcamByWebcamDisplayName() {
        Webcam webcam1 = new Webcam("testSessionId1", "testKey1", "testDisplay1");
        webcamRepository.save(webcam1);

        Webcam testDisplay1 = webcamRepository.getWebcamByWebcamDisplayName("testDisplay1").get();

        assertThat(webcam1).isEqualTo(testDisplay1);
    }
}