package webrtc.signaling.repository;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@ToString
@Repository
public class WebcamKeyRepository {

    private final Map<Long, String> webcamKeyMap = new HashMap<>();
    private Long sequence = 0L;
    
    public Long save(String camKey) {
        webcamKeyMap.put(++sequence, camKey);
        return sequence;
    }
    public List<Long> getIds() {
        return new ArrayList<>(webcamKeyMap.keySet());
    }

    public String getKey(Long id) {
        return webcamKeyMap.get(id);
    }

    //TODO: delete

    public void clear() {
        webcamKeyMap.clear();
    }
}
