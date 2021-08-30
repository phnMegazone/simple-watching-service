package com.fleta.watchingservice.adapter.persistence;

import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.port.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("jdbcRepository")
@Primary
@ConditionalOnProperty(name="repository-type", havingValue = "jdbc")
public class JdbcRepository implements CommonRepository {

    private final JdbcWatchingRepository jdbcWatchingRepository;

    @Autowired
    public JdbcRepository(JdbcWatchingRepository jdbcWatchingRepository) {
        this.jdbcWatchingRepository = jdbcWatchingRepository;
    }

    @Override
    public List<WatchingDto1> nxvod211TobeSql00901(String cHjdongNo) {
        return jdbcWatchingRepository.nxvod211TobeSql00901(cHjdongNo);
    }

    @Override
    public List<WatchingDto2> nxvod211TobeSql01801(String cSaId, int pIdxSa) {
        return jdbcWatchingRepository.nxvod211TobeSql01801(cSaId, pIdxSa);
    }
}
