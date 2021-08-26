package com.fleta.watchingservice.adapter.rest;


import com.fleta.watchingservice.adapter.persistence.RedisSql00901DtoRepository;
import com.fleta.watchingservice.domain.dto.Sql009_01Dto;
import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.port.CommonRepository;
import com.fleta.watchingservice.port.SetJdbcForRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Redis Controller - for SQL009_01 DTO
 */
@RestController
@RequestMapping("/redis/sql009-01")
@Transactional
public class RedisSql00901DtoController {

    protected final CommonRepository commonRepository;

    protected final RedisSql00901DtoRepository redisRepositoryForSet;

    private final SetJdbcForRedisRepository setJdbcForRedisRepository;

    @Autowired
    public RedisSql00901DtoController(
            CommonRepository commonRepository,
            RedisSql00901DtoRepository redisRepositoryForSet,
            SetJdbcForRedisRepository setJdbcForRedisRepository
    ) {
        this.commonRepository = commonRepository;
        this.setJdbcForRedisRepository = setJdbcForRedisRepository;
        this.redisRepositoryForSet = redisRepositoryForSet;
//        setLoggerClass(this.getClass());
    }

    /**
     * 전체 목록 Set
     */
    @PostMapping("/list")
    public int setList() {
        List<Sql009_01Dto> dtoFlux = setJdbcForRedisRepository.getSql009_01List();
        Map<String, List<String>> mapMono = redisRepositoryForSet.groupByDongCd(dtoFlux);
        redisRepositoryForSet.setList(mapMono);
        return 1;
    }

    @GetMapping("/{dongCd}")
    public List<WatchingDto1> getData(@PathVariable String dongCd) {
        return commonRepository.nxvod211TobeSql00901(dongCd);
    }
}
