package br.com.moraesit.rsocket;

import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.dto.ResponseDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec05ConnectionSetupTest {

    private RSocketClient rSocketClient;

    @BeforeAll
    public void setup() {
        Mono<RSocket> socketMono = RSocketConnector.create()
                .setupPayload(DefaultPayload.create("user:passwordError"))
                .connect(TcpClientTransport.create("localhost", 6565))
                .doOnNext(r -> System.out.println("going to connect"));

        this.rSocketClient = RSocketClient.from(socketMono);
    }

    @Test
    @DisplayName("connectionSetup")
    public void connectionSetup() {

        Payload payload = ObjectUtil.toPayload(new RequestDTO(5));

        Flux<ResponseDTO> flux = this.rSocketClient
                .requestStream(Mono.just(payload))
                .map(p -> ObjectUtil.toObject(p, ResponseDTO.class))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
