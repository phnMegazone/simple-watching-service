package com.fleta.watchingservice.adapter.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleta.watchingservice.domain.dto.Sql018_01Dto;
import com.fleta.watchingservice.port.RedisDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisSql01801DtoRepository implements RedisDtoRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX = "Sql018_01:";

    @Autowired
    public RedisSql01801DtoRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 전체 목록 Set (Hash Type)
     */
    public void setList(List<Sql018_01Dto> sql018_01DtoList) {
        ObjectMapper objectMapper = new ObjectMapper();

        sql018_01DtoList.stream().forEach(dto -> {
            try {
                redisTemplate.opsForHash().put(
                        KEY_PREFIX + dto.getSaId(),
                        dto.getPIdxSa(),
                        objectMapper.writeValueAsString(dto)
                );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
