package com.fleta.watchingservice.adapter.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleta.watchingservice.domain.dto.Sql009_01Dto;
import com.fleta.watchingservice.domain.dto.WatchingDto1;
import com.fleta.watchingservice.port.RedisDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RedisSql00901DtoRepository implements RedisDtoRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX = "Sql009_01:";

    @Autowired
    public RedisSql00901DtoRepository(
            RedisTemplate<String, String> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * DongCd로 그룹화
     */
    public Map<String, List<String>> groupByDongCd(List<Sql009_01Dto> param) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> map = new HashMap<>();
        param.stream().forEach(item -> {
            String dongCd = item.getDongCd();
            if (!map.containsKey(dongCd)) {
                map.put(dongCd, new ArrayList<>());
            }
            List<String> getList = map.get(dongCd);
            try {
                getList.add(objectMapper.writeValueAsString(WatchingDto1.builder()
                        .stbPlayIp1(item.getStbPlayIp1())
                        .cdnLocalTyp(item.getCdnLocalTyp())
                        .ipv6PlayIp(item.getIpv6PlayIp())
                        .build()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * 전체 목록 Set (List Type)
     * FIXME - 오류 발생함...
     */
    public void setList(Map<String, List<String>> map) {
        map.forEach((k, v) -> {
            redisTemplate.opsForList().rightPushAll(KEY_PREFIX + k, v);
        });
    }
}
