package br.com.moraesit.rsocket.service;

import br.com.moraesit.rsocket.dto.RequestDTO;
import br.com.moraesit.rsocket.dto.ResponseDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MathService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving: " + ObjectUtil.toObject(payload, RequestDTO.class));
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDTO requestDTO = ObjectUtil.toObject(payload, RequestDTO.class);
            ResponseDTO responseDTO = new ResponseDTO(requestDTO.getInput(), requestDTO.getInput() * requestDTO.getInput());
            return ObjectUtil.toPayload(responseDTO);
        });
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        RequestDTO requestDTO = ObjectUtil.toObject(payload, RequestDTO.class);
        return Flux.range(1, 10)
                .map(i -> i * requestDTO.getInput())
                .map(i -> new ResponseDTO(requestDTO.getInput(), i))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .map(ObjectUtil::toPayload);
    }
}
