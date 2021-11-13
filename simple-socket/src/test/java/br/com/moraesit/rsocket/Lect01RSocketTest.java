package br.com.moraesit.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lect01RSocketTest {

    private RSocket rSocket;

    @BeforeAll
    public void setup() {
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();
    }

    @RepeatedTest(3)
    @DisplayName("fireAndForget")
    public void fireAndForget() {
        Payload payload = DefaultPayload.create("Hello World!");
        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier.create(mono)
                .verifyComplete();
    }
}
