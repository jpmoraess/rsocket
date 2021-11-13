package br.com.moraesit.rsocket;

import br.com.moraesit.rsocket.dto.ChartResponseDTO;
import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.dto.ResponseDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

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

    @Test
    @DisplayName("requestResponse")
    public void requestResponse() {
        Payload payload = ObjectUtil.toPayload(new RequestDTO(5));

        Mono<ResponseDTO> responseDTOMono = this.rSocket.requestResponse(payload)
                .map(p -> ObjectUtil.toObject(p, ResponseDTO.class))
                .doOnNext(System.out::println);

        StepVerifier.create(responseDTOMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("requestStream")
    public void requestStream() {
        Payload payload = ObjectUtil.toPayload(new RequestDTO(5));

        Flux<ResponseDTO> flux = this.rSocket.requestStream(payload)
                .map(p -> ObjectUtil.toObject(p, ResponseDTO.class))
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    @DisplayName("requestChannel")
    public void requestChannel() {
        Flux<Payload> payloadFlux = Flux.range(-10, 21)
                .delayElements(Duration.ofMillis(500))
                .map(RequestDTO::new)
                .map(ObjectUtil::toPayload);

        Flux<ChartResponseDTO> chartResponseDTOFlux = this.rSocket.requestChannel(payloadFlux)
                .map(p -> ObjectUtil.toObject(p, ChartResponseDTO.class))
                .doOnNext(System.out::println);

        StepVerifier.create(chartResponseDTOFlux)
                .expectNextCount(21)
                .verifyComplete();
    }
}
