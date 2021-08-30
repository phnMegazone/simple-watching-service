package com.fleta.watchingservice.adapter.persistence;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.port.WatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RedisRepository implements WatchingRepository {

    private final JdbcWatchingRepository jdbcWatchingRepository;

    @Autowired
    public RedisRepository(JdbcWatchingRepository jdbcWatchingRepository) {
        this.jdbcWatchingRepository = jdbcWatchingRepository;
    }

    @Override
    @Cacheable(value = CacheKey.WATCHING_cHjdongNo_KEY, key = "{#cHjdongNo}")
    public List<WatchingDto1> nxvod211TobeSql00901(String cHjdongNo) {
        return jdbcWatchingRepository.nxvod211TobeSql00901(cHjdongNo);
    }

    @Override
    @Cacheable(value = CacheKey.WATCHING_cSaId_pIdxSa_KEY, key = "{#cSaId, #pIdxSa}")
    public List<WatchingDto2> nxvod211TobeSql01801(String cSaId, int pIdxSa) {
        return jdbcWatchingRepository.nxvod211TobeSql01801(cSaId, pIdxSa);
    }
}
