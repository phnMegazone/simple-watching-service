package com.fleta.watchingservice.port;

import com.fleta.watchingservice.domain.model.PtLvDongInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL reactive repository for the PtLvDongInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PtLvDongInfoRepository extends JpaRepository<PtLvDongInfo, Long> {
}
