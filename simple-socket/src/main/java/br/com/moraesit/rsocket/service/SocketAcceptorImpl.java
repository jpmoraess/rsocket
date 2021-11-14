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

        if (isValidClient(connectionSetupPayload.getDataUtf8()))
            return Mono.just(new MathService());
        else
            return Mono.just(new FreeService());

        //Mono.fromCallable(MathService::new);
        //return Mono.fromCallable(() -> new BatchJobService(rSocket));
        //return Mono.fromCallable(FastProducerService::new);
    }

    private boolean isValidClient(String credentials) {
        return ("user:password".equals(credentials));
    }
}
