package com.fleta.watchingservice.port;

import com.fleta.watchingservice.domain.model.PtLvNodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL reactive repository for the PtLvNodeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PtLvNodeInfoRepository extends JpaRepository<PtLvNodeInfo, Long> {
}
