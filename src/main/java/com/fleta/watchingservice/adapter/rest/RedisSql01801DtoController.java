package com.fleta.watchingservice.adapter.rest;

import com.fleta.watchingservice.adapter.persistence.JdbcWatchingRepository;
import com.fleta.watchingservice.adapter.persistence.RedisSql01801DtoRepository;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.port.CommonRepository;
import com.fleta.watchingservice.port.SetJdbcForRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Redis Controller - for SQL018_01 DTO
 */
@RestController
@RequestMapping("/redis/sql018-01")
@Transactional
public class RedisSql01801DtoController {

    protected final CommonRepository commonRepository;
    private final RedisSql01801DtoRepository redisRepositoryForSet;
    private final SetJdbcForRedisRepository setJdbcForRedisRepository;

    @Autowired
    public RedisSql01801DtoController(
            CommonRepository commonRepository,
            RedisSql01801DtoRepository redisRepositoryForSet,
            SetJdbcForRedisRepository setJdbcForRedisRepository
    ) {
        this.commonRepository = commonRepository;
        this.redisRepositoryForSet = redisRepositoryForSet;
        this.setJdbcForRedisRepository = setJdbcForRedisRepository;
//        setLoggerClass(this.getClass());
    }

    /**
     * 전체 목록 Set
     */
    @PostMapping("/list")
    public int setList() {
        final int LIMIT = 10000;

        int totalCnt = setJdbcForRedisRepository.getSQL018_01Count();
        double pageCnt = Math.ceil(totalCnt / LIMIT);
        for (int i = 0; i < pageCnt; i++) {
            var dtoFlux = setJdbcForRedisRepository.getSQL018_01List(LIMIT, i * LIMIT);
            redisRepositoryForSet.setList(dtoFlux);
        }
        return 1;
    }

    /**
     * 테스트를 위한 추가
     */
    @GetMapping("/{cSaId}/{pIdxSa}")
    public List<WatchingDto2> getData(@PathVariable String cSaId, @PathVariable String pIdxSa) {
        return commonRepository.nxvod211TobeSql01801(cSaId, Integer.parseInt(pIdxSa));
    }
}
