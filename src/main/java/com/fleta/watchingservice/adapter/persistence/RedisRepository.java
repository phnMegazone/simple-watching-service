package com.fleta.watchingservice.adapter.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.domain.dto.WatchingDto2;
import com.fleta.watchingservice.port.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RedisRepository implements CommonRepository {

    private final JdbcWatchingRepository jdbcWatchingRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX_009_01 = "WatchingSql009_01:";
    private final String KEY_PREFIX_018_01 = "WatchingSql018_01:";

    @Autowired
    public RedisRepository(JdbcWatchingRepository jdbcWatchingRepository, RedisTemplate<String, String> redisTemplate) {
        this.jdbcWatchingRepository = jdbcWatchingRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<WatchingDto1> nxvod211TobeSql00901(String cHjdongNo) {
        List<WatchingDto1> watchingDto1s = getFromRedisNxvod211TobeSql00901(cHjdongNo);
        if (!watchingDto1s.isEmpty()) {
            return watchingDto1s;
        }
        List<WatchingDto1> fetchData = jdbcWatchingRepository.nxvod211TobeSql00901(cHjdongNo);

        // TODO: should run on background
        Map<String, List<String>> map = groupBycHjdongNo(cHjdongNo, fetchData);
        setToRedisNxvod211TobeSql00901(cHjdongNo, map);

        return fetchData;
    }

    @Override
    public List<WatchingDto2> nxvod211TobeSql01801(String cSaId, int pIdxSa) {
        List<WatchingDto2> dto2 = getFromRedisNxvod211TobeSql01801(cSaId, pIdxSa);
        if (!dto2.isEmpty()) {
            return dto2;
        }
        List<WatchingDto2> fetchData = jdbcWatchingRepository.nxvod211TobeSql01801(cSaId, pIdxSa);
        // TODO: should run on background
        Map<String, List<String>> map = groupByCSaIdAndPIdxSa(cSaId, pIdxSa, fetchData);
        setToRedisNxvod211TobeSql01801(cSaId, pIdxSa, map);

        return fetchData;
    }

    /**
     * Flux DTO Redis 적재
     */
    private void setToRedisNxvod211TobeSql00901(String cHjdongNo, Map<String, List<String>> map) {
        map.forEach((k, v) -> {
            redisTemplate
                    .opsForList()
                    .rightPushAll(KEY_PREFIX_009_01 + k, v);
        });
//        redisWatchingRepository.saveAll(map);
    }

    /**
     * cHjdongNo로 그룹화
     */
    private Map<String, List<String>> groupBycHjdongNo(String cHjdongNo, List<WatchingDto1> watchingDto1List) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        watchingDto1List.forEach(dto -> {
            if (!map.containsKey(cHjdongNo)) {
                map.put(cHjdongNo, new ArrayList<>());
            }
            List<String> getList = map.get(cHjdongNo);
            try {
                getList.add(objectMapper.writeValueAsString(dto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * saId, pIdxSa로 그룹화
     */
    private Map<String, List<String>> groupByCSaIdAndPIdxSa(String cSaId, int pIdxSa, List<WatchingDto2> watchingDto2List) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        watchingDto2List.forEach(dto -> {
            String key = cSaId + ":" + pIdxSa;
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            List<String> getList = map.get(key);
            try {
                getList.add(objectMapper.writeValueAsString(dto));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * Redis 조회
     */
    private List<WatchingDto1> getFromRedisNxvod211TobeSql00901(String cHjdongNo) {
        ObjectMapper objectMapper = new ObjectMapper();
        return redisTemplate
            .opsForList()
            .range(KEY_PREFIX_009_01 + cHjdongNo, 0, -1).stream()
            .map(
                str -> {
                    try {
                        return objectMapper.readValue(str, WatchingDto1.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            ).collect(Collectors.toList());
    }

    /**
     * Redis 조회
     */
    private List<WatchingDto2> getFromRedisNxvod211TobeSql01801(String cSaId, int pIdxSa) {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = cSaId + ":" + pIdxSa;

        return redisTemplate
            .opsForList()
            .range(KEY_PREFIX_018_01 + key, 0, -1).stream()
            .map(
                str -> {
                    try {
                        return objectMapper.readValue(str, WatchingDto2.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            ).collect(Collectors.toList());
    }

    /**
     * Flux DTO Redis 적재
     */
    private void setToRedisNxvod211TobeSql01801(String cSaId, int pIdxSa, Map<String, List<String>> map) {
        map.forEach((k, v) -> {
            redisTemplate
                    .opsForList()
                    .rightPushAll(KEY_PREFIX_018_01 + k, v);
        });
    }
}
