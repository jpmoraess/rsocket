package br.com.moraesit.rsocket.client;

import br.com.moraesit.rsocket.dto.ResponseDTO;
import br.com.moraesit.rsocket.util.ObjectUtil;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class CallbackService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Client received: " + ObjectUtil.toObject(payload, ResponseDTO.class));
        return Mono.empty();
    }
}
