package com.fleta.watchingservice.adapter.grpc.server;
import com.fleta.watchingservice.domain.service.WatchingServiceGrpcImpl;
import com.fleta.watchingservice.port.WatchingRepository;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WatchingServer implements ApplicationRunner {

    @Value("${grpc.port}")
    public int port;

    @Value("${grpc.host}")
    public String host;

    private final WatchingRepository watchingRepository;

    public WatchingServer(WatchingRepository watchingRepository) {
        this.watchingRepository = watchingRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("host {} {}", host, port);
        ServerBuilder.forPort(port)
            .addService(new WatchingServiceGrpcImpl(watchingRepository))
            .build().start();
    }
}
