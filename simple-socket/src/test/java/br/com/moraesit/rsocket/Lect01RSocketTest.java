package br.com.moraesit.rsocket;

import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
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
        Payload payload = ObjectUtil.toPayload(new RequestDTO(5));

        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier.create(mono)
                .verifyComplete();
    }
}
