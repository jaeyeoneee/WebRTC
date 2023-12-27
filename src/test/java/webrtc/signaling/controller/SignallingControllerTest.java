package webrtc.signaling.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.lang.NonNull;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignallingControllerTest {

    @LocalServerPort
    private Integer port;

    @Test
    public void sendOfferTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient client = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        client.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = client.connectAsync(String.format("http://localhost:%d/signaling", port), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

        stompSession.subscribe("/queue/offer/1", new StompFrameHandler() {
            @NonNull
            @Override
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                blockingQueue.add((String) payload);
            }
        });

        stompSession.send("/app/offer/1", "hello");

        await().atMost(1, SECONDS).untilAsserted(()-> assertEquals("hello", blockingQueue.poll()));
    }

    @Test
    public void sendAnswerTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient client = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        client.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = client.connectAsync(String.format("http://localhost:%d/signaling", port), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

        stompSession.subscribe("/queue/answer/1", new StompFrameHandler() {
            @NonNull
            @Override
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                blockingQueue.add((String) payload);
            }
        });

        stompSession.send("/app/answer/1", "hello");

        await().atMost(1, SECONDS).untilAsserted(()-> assertEquals("hello", blockingQueue.poll()));

    }

    @Test
    public void sendIceCandidateTest() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketStompClient client = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        client.setMessageConverter(new StringMessageConverter());

        StompSession stompSession = client.connectAsync(String.format("http://localhost:%d/signaling", port), new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

        stompSession.subscribe("/queue/iceCandidate/1", new StompFrameHandler() {
            @NonNull
            @Override
            public Type getPayloadType(@NonNull StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(@NonNull StompHeaders headers, Object payload) {
                blockingQueue.add((String) payload);
            }
        });

        stompSession.send("/app/iceCandidate/1", "hello");

        await().atMost(1, SECONDS).untilAsserted(()-> assertEquals("hello", blockingQueue.poll()));
    }

}