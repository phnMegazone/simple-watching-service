package com.fleta.watchingservice.grpc.server;


import com.fleta.watchingservice.grpc.common.WatchingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatchingServiceStub {

    @Value("${grpc.host}")
    public String host;

    @Value("${grpc.port}")
    public int port;

    @Bean
    public ManagedChannel channel() {
        return ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build();
    }

    @Bean
    public WatchingServiceGrpc.WatchingServiceBlockingStub serviceStub() {
        return WatchingServiceGrpc.newBlockingStub(channel());
    }

}
