package com.fleta.watchingservice.port;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommonRepository {
    List<WatchingDto1> nxvod211TobeSql00901(String cHjdongNo);
    List<WatchingDto2> nxvod211TobeSql01801(String cSaId, int pIdxSa);
}
