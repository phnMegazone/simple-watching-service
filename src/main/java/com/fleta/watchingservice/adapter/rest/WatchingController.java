package com.fleta.watchingservice.adapter.rest;

import com.fleta.watchingservice.grpc.common.*;
import com.fleta.watchingservice.port.WatchingService;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Transactional
public class WatchingController {

    private final WatchingService watchingService;

    @Autowired
    public WatchingController(WatchingService watchingService) {
        this.watchingService = watchingService;
    }

    @PostMapping("/requestpost")
    public Integer myHandler(@RequestBody String value, StreamObserver<WatchingOutput1> responseObserver1, StreamObserver<WatchingOutput2> responseObserver2) {

        WatchingServiceGrpc.WatchingServiceStub stub = WatchingServiceGrpc.newStub(
            ManagedChannelBuilder.forAddress("localhost", 6569)
            .usePlaintext()
            .build()
        );
        stub.nxvod211TobeSql00901(WatchingInput1.newBuilder().setCHjdongNo(value).build(), responseObserver1);

        stub.nxvod211TobeSql01801(WatchingInput2.newBuilder().setCSaId("500069234833").setPIdxSa(0).build(), responseObserver2);

        return 1;
    }

    @GetMapping("/requestGet")
    public List<String> watchingTestApi(@RequestParam(defaultValue = "500069234833") String cSaId,
                                        @RequestParam(defaultValue = "0") int pIdxSa) {
        WatchingInput2 watchingInput2 = WatchingInput2.newBuilder().setCSaId(cSaId).setPIdxSa(pIdxSa).build();
        List<WatchingOutput2> watchingOutput2List = watchingService.nxvod211TobeSql01801(watchingInput2);
        return watchingOutput2List.stream().map(o ->
            String.format("%s : %s\n", o.getAdiAlbumId(), o.getWatchDate())).collect(Collectors.toList());
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
