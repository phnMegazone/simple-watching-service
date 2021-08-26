package com.fleta.watchingservice.port;

import com.fleta.watchingservice.domain.dto.Sql009_01Dto;
import com.fleta.watchingservice.domain.dto.Sql018_01Dto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SetJdbcForRedisRepository {

    List<Sql009_01Dto> getSql009_01List();

    List<Sql018_01Dto> getSQL018_01List(int limit, int offset);

    int getSQL018_01Count();


}
