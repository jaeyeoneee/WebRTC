package webrtc.signaling.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class WebcamKeyRepositoryTest {

    WebcamKeyRepository webcamKeyRepository = new WebcamKeyRepository();

    @BeforeEach
    void before() {
        webcamKeyRepository.clear();
    }

    @Test
    void save() {
        String key1 = "webcamTest1";

        Long id = webcamKeyRepository.save(key1);

        assertThat(id).isEqualTo(1L);
    }

    @Test
    void getIds() {
        String key1 = "webcamTest1";
        String key2 = "webcamTest2";
        webcamKeyRepository.save(key1);
        webcamKeyRepository.save(key2);

        List<Long> ids = webcamKeyRepository.getIds();

        List<Long> expectedIds = new ArrayList<>(Arrays.asList(1L, 2L));
        assertThat(ids).isEqualTo(expectedIds);
    }

    @Test
    void getKey() {
        String key1 = "webcamTest1";
        webcamKeyRepository.save(key1);

        String key = webcamKeyRepository.getKey(1L);

        assertThat(key1).isEqualTo(key);
    }
}