package br.com.moraesit.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    private final Logger log = LoggerFactory.getLogger(SocketAcceptorImpl.class);

    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
        log.info("accept method");
        //Mono.fromCallable(MathService::new);
        return Mono.fromCallable(() -> new BatchJobService(rSocket));
    }
}
