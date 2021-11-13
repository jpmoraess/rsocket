package br.com.moraesit.rsocket;

import br.com.moraesit.rsocket.client.CallbackService;
import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02CallbackTest {

    private RSocket rSocket;

    @BeforeAll
    public void setup() {
        this.rSocket = RSocketConnector.create()
                .acceptor(SocketAcceptor.with(new CallbackService()))
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();
    }

    @Test
    @DisplayName("callback")
    public void callback() throws InterruptedException {
        RequestDTO requestDTO = new RequestDTO(5);
        Mono<Void> mono = this.rSocket.fireAndForget(ObjectUtil.toPayload(requestDTO));

        StepVerifier.create(mono)
                .verifyComplete();

        System.out.println("going to wait");

        Thread.sleep(12000);
    }
}
