package br.com.moraesit.rsocket.service;

import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.dto.ResponseDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BatchJobService implements RSocket {

    private RSocket rSocket;

    public BatchJobService(RSocket rSocket) {
        this.rSocket = rSocket;
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        RequestDTO requestDTO = ObjectUtil.toObject(payload, RequestDTO.class);
        System.out.println("Received: " + requestDTO);

        Mono.just(requestDTO)
                .delayElement(Duration.ofSeconds(10))
                .doOnNext(i -> System.out.println("emitting"))
                .flatMap(this::findCube)
                .subscribe();

        return Mono.empty();
    }

    private Mono<Void> findCube(RequestDTO requestDTO) {
        int input = requestDTO.getInput();
        int output = input * input * input;
        ResponseDTO responseDTO = new ResponseDTO(input, output);
        Payload payload = ObjectUtil.toPayload(responseDTO);
        return this.rSocket.fireAndForget(payload);
    }
}
