package com.fleta.watchingservice.domain.service;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.grpc.common.WatchingInput1;
import com.fleta.watchingservice.grpc.common.WatchingInput2;
import com.fleta.watchingservice.grpc.common.WatchingOutput1;
import com.fleta.watchingservice.grpc.common.WatchingOutput2;
import com.fleta.watchingservice.port.CommonRepository;
import com.fleta.watchingservice.port.WatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WatchingServiceImpl implements WatchingService {

        private final CommonRepository commonRepository;
//    private final JdbcWatchingRepository jdbcWatchingRepository;

    public WatchingServiceImpl(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    @Override
    public List<WatchingOutput1> nxvod211TobeSql00901(WatchingInput1 input) {
        log.info("---------------------->nxvod211TobeSql00901");
        String cHjdongNo = input.getCHjdongNo();
        List<WatchingDto1> watchingDto1List = commonRepository.nxvod211TobeSql00901(cHjdongNo);
        return watchingDto1List.stream().map(WatchingDto1::toWatchingOutput1).collect(Collectors.toList());
    }

    @Override
    public List<WatchingOutput2> nxvod211TobeSql01801(WatchingInput2 input) {
        log.info("---------------------->nxvod211TobeSql01801");
        String cSaId = input.getCSaId();
        int pIdxSa = input.getPIdxSa();
        List<WatchingDto2> watchingDto2List = commonRepository.nxvod211TobeSql01801(cSaId, pIdxSa);
        return watchingDto2List.stream().map(WatchingDto2::toWatchingOutput2).collect(Collectors.toList());
    }
}
