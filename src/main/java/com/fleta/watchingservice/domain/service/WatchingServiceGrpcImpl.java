package com.fleta.watchingservice.domain.service;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.grpc.common.*;
import com.fleta.watchingservice.port.CommonRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WatchingServiceGrpcImpl extends WatchingServiceGrpc.WatchingServiceImplBase {

    private final CommonRepository commonRepository;

    @Autowired
    public WatchingServiceGrpcImpl(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    @Override
    public void nxvod211TobeSql00901(WatchingInput1 request, StreamObserver<WatchingOutput1> responseObserver) {
        List<WatchingDto1> list = getWatchingDto1Condition(request);
        list.stream()
            .map(WatchingDto1::toWatchingOutput1)
            .forEach(responseObserver::onNext);

        responseObserver.onCompleted();
    }
    private List<WatchingDto1> getWatchingDto1Condition(WatchingInput1 r) {
        String cHjdongNo = r.getCHjdongNo();
        return commonRepository.nxvod211TobeSql00901(cHjdongNo);
    }

    @Override
    public void nxvod211TobeSql01801(WatchingInput2 request, StreamObserver<WatchingOutput2> responseObserver) {
        List<WatchingDto2> list = getPvsCustListCondition(request);
        list.stream()
            .map(WatchingDto2::toWatchingOutput2)
            .forEach(responseObserver::onNext);

        responseObserver.onCompleted();
    }
    private List<WatchingDto2> getPvsCustListCondition(WatchingInput2 r) {
        String cSaId = r.getCSaId();
        int pIdxSa = r.getPIdxSa();
        return commonRepository.nxvod211TobeSql01801(cSaId, pIdxSa);
    }
}
