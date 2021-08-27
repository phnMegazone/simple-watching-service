package com.fleta.watchingservice.adapter.rest;

import com.fleta.watchingservice.adapter.rest.errors.BadRequestAlertException;
import com.fleta.watchingservice.domain.model.PtLvNodeInfo;
import com.fleta.watchingservice.port.PtLvNodeInfoRepository;
import com.megazone.framework.web.util.HeaderUtil;
import com.megazone.framework.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link PtLvNodeInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PtLvNodeInfoResourceController {

    private final Logger log = LoggerFactory.getLogger(PtLvNodeInfoResourceController.class);

    private static final String ENTITY_NAME = "watchingServicePtLvNodeInfo";

    @Value("${mega.clientApp.name}")
    private String applicationName;

    private final PtLvNodeInfoRepository ptLvNodeInfoRepository;

    public PtLvNodeInfoResourceController(PtLvNodeInfoRepository ptLvNodeInfoRepository) {
        this.ptLvNodeInfoRepository = ptLvNodeInfoRepository;
    }

    /**
     * {@code POST  /pt-lv-node-infos} : Create a new ptLvNodeInfo.
     *
     * @param ptLvNodeInfo the ptLvNodeInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ptLvNodeInfo, or with status {@code 400 (Bad Request)} if the ptLvNodeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pt-lv-node-infos")
    public ResponseEntity<PtLvNodeInfo> createPtLvNodeInfo(@Valid @RequestBody PtLvNodeInfo ptLvNodeInfo) throws URISyntaxException {
        log.debug("REST request to save PtLvNodeInfo : {}", ptLvNodeInfo);
        if (ptLvNodeInfo.getId() != null) {
            throw new BadRequestAlertException("A new ptLvNodeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PtLvNodeInfo result = ptLvNodeInfoRepository.save(ptLvNodeInfo);
        try {
            return ResponseEntity
                    .created(new URI("/api/xcion-sbc-tbl-uniteds/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code PUT  /pt-lv-node-infos/:id} : Updates an existing ptLvNodeInfo.
     *
     * @param id           the id of the ptLvNodeInfo to save.
     * @param ptLvNodeInfo the ptLvNodeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptLvNodeInfo,
     * or with status {@code 400 (Bad Request)} if the ptLvNodeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ptLvNodeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pt-lv-node-infos/{id}")
    public ResponseEntity<PtLvNodeInfo> updatePtLvNodeInfo(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody PtLvNodeInfo ptLvNodeInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PtLvNodeInfo : {}, {}", id, ptLvNodeInfo);
        if (ptLvNodeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ptLvNodeInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        boolean exists = ptLvNodeInfoRepository.existsById(id);
        if (!exists) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        try {
            PtLvNodeInfo result = ptLvNodeInfoRepository.save(ptLvNodeInfo);
            return ResponseEntity
                    .ok()
                    .headers(
                            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString())
                    )
                    .body(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@code PATCH  /pt-lv-node-infos/:id} : Partial updates given fields of an existing ptLvNodeInfo, field will ignore if it is null
     *
     * @param id           the id of the ptLvNodeInfo to save.
     * @param ptLvNodeInfo the ptLvNodeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptLvNodeInfo,
     * or with status {@code 400 (Bad Request)} if the ptLvNodeInfo is not valid,
     * or with status {@code 404 (Not Found)} if the ptLvNodeInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the ptLvNodeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pt-lv-node-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PtLvNodeInfo> partialUpdatePtLvNodeInfo(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PtLvNodeInfo ptLvNodeInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PtLvNodeInfo partially : {}, {}", id, ptLvNodeInfo);
        if (ptLvNodeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ptLvNodeInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        boolean exists = ptLvNodeInfoRepository
                .existsById(id);
        if (!exists) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PtLvNodeInfo> result = ptLvNodeInfoRepository
                .findById(ptLvNodeInfo.getId())
                .map(
                        existingPtLvNodeInfo -> {
                            if (ptLvNodeInfo.getSubNodeCd() != null) {
                                existingPtLvNodeInfo.setSubNodeCd(ptLvNodeInfo.getSubNodeCd());
                            }
                            if (ptLvNodeInfo.getSubNodeName() != null) {
                                existingPtLvNodeInfo.setSubNodeName(ptLvNodeInfo.getSubNodeName());
                            }
                            if (ptLvNodeInfo.getSubNodeIp1() != null) {
                                existingPtLvNodeInfo.setSubNodeIp1(ptLvNodeInfo.getSubNodeIp1());
                            }
                            if (ptLvNodeInfo.getSubNodeIp2() != null) {
                                existingPtLvNodeInfo.setSubNodeIp2(ptLvNodeInfo.getSubNodeIp2());
                            }
                            if (ptLvNodeInfo.getStbPlayIp1() != null) {
                                existingPtLvNodeInfo.setStbPlayIp1(ptLvNodeInfo.getStbPlayIp1());
                            }
                            if (ptLvNodeInfo.getStbPlayIp2() != null) {
                                existingPtLvNodeInfo.setStbPlayIp2(ptLvNodeInfo.getStbPlayIp2());
                            }
                            if (ptLvNodeInfo.getServiceYn() != null) {
                                existingPtLvNodeInfo.setServiceYn(ptLvNodeInfo.getServiceYn());
                            }
                            if (ptLvNodeInfo.getUseYn() != null) {
                                existingPtLvNodeInfo.setUseYn(ptLvNodeInfo.getUseYn());
                            }
                            if (ptLvNodeInfo.getStorageSize() != null) {
                                existingPtLvNodeInfo.setStorageSize(ptLvNodeInfo.getStorageSize());
                            }
                            if (ptLvNodeInfo.getSubNodePort1() != null) {
                                existingPtLvNodeInfo.setSubNodePort1(ptLvNodeInfo.getSubNodePort1());
                            }
                            if (ptLvNodeInfo.getSubNodePort2() != null) {
                                existingPtLvNodeInfo.setSubNodePort2(ptLvNodeInfo.getSubNodePort2());
                            }
                            if (ptLvNodeInfo.getStbPlayIp3() != null) {
                                existingPtLvNodeInfo.setStbPlayIp3(ptLvNodeInfo.getStbPlayIp3());
                            }
                            if (ptLvNodeInfo.getNodePrior() != null) {
                                existingPtLvNodeInfo.setNodePrior(ptLvNodeInfo.getNodePrior());
                            }
                            if (ptLvNodeInfo.getNodeLevel() != null) {
                                existingPtLvNodeInfo.setNodeLevel(ptLvNodeInfo.getNodeLevel());
                            }
                            if (ptLvNodeInfo.getInitialYn() != null) {
                                existingPtLvNodeInfo.setInitialYn(ptLvNodeInfo.getInitialYn());
                            }
                            if (ptLvNodeInfo.getCdnPolicy() != null) {
                                existingPtLvNodeInfo.setCdnPolicy(ptLvNodeInfo.getCdnPolicy());
                            }
                            if (ptLvNodeInfo.getCdnLocalTyp() != null) {
                                existingPtLvNodeInfo.setCdnLocalTyp(ptLvNodeInfo.getCdnLocalTyp());
                            }
                            if (ptLvNodeInfo.getCdnNearTyp() != null) {
                                existingPtLvNodeInfo.setCdnNearTyp(ptLvNodeInfo.getCdnNearTyp());
                            }
                            if (ptLvNodeInfo.getCdnCenterTyp() != null) {
                                existingPtLvNodeInfo.setCdnCenterTyp(ptLvNodeInfo.getCdnCenterTyp());
                            }
                            if (ptLvNodeInfo.getExceptionFlag() != null) {
                                existingPtLvNodeInfo.setExceptionFlag(ptLvNodeInfo.getExceptionFlag());
                            }
                            if (ptLvNodeInfo.getNodeTransYn() != null) {
                                existingPtLvNodeInfo.setNodeTransYn(ptLvNodeInfo.getNodeTransYn());
                            }
                            if (ptLvNodeInfo.getCmNodeYn() != null) {
                                existingPtLvNodeInfo.setCmNodeYn(ptLvNodeInfo.getCmNodeYn());
                            }
                            if (ptLvNodeInfo.getTimeNodeYn() != null) {
                                existingPtLvNodeInfo.setTimeNodeYn(ptLvNodeInfo.getTimeNodeYn());
                            }
                            if (ptLvNodeInfo.getNodeGroup() != null) {
                                existingPtLvNodeInfo.setNodeGroup(ptLvNodeInfo.getNodeGroup());
                            }
                            if (ptLvNodeInfo.getIpv6Flag1() != null) {
                                existingPtLvNodeInfo.setIpv6Flag1(ptLvNodeInfo.getIpv6Flag1());
                            }
                            if (ptLvNodeInfo.getIpv6Flag2() != null) {
                                existingPtLvNodeInfo.setIpv6Flag2(ptLvNodeInfo.getIpv6Flag2());
                            }
                            if (ptLvNodeInfo.getIpv6Flag3() != null) {
                                existingPtLvNodeInfo.setIpv6Flag3(ptLvNodeInfo.getIpv6Flag3());
                            }
                            if (ptLvNodeInfo.getIpv6PlayIp1() != null) {
                                existingPtLvNodeInfo.setIpv6PlayIp1(ptLvNodeInfo.getIpv6PlayIp1());
                            }
                            if (ptLvNodeInfo.getIpv6PlayIp2() != null) {
                                existingPtLvNodeInfo.setIpv6PlayIp2(ptLvNodeInfo.getIpv6PlayIp2());
                            }
                            if (ptLvNodeInfo.getIpv6PlayIp3() != null) {
                                existingPtLvNodeInfo.setIpv6PlayIp3(ptLvNodeInfo.getIpv6PlayIp3());
                            }
                            if (ptLvNodeInfo.getChnlServiceYn() != null) {
                                existingPtLvNodeInfo.setChnlServiceYn(ptLvNodeInfo.getChnlServiceYn());
                            }

                            return existingPtLvNodeInfo;
                        }
                )
                .map(ptLvNodeInfoRepository::save);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.get().getId().toString()))
                .body(result.get());
    }

    /**
     * {@code GET  /pt-lv-node-infos} : get all the ptLvNodeInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptLvNodeInfos in body.
     */
    @GetMapping("/pt-lv-node-infos")
    public List<PtLvNodeInfo> getAllPtLvNodeInfos() {
        log.debug("REST request to get all PtLvNodeInfos");
        return ptLvNodeInfoRepository.findAll();
    }

    /**
     * {@code GET  /pt-lv-node-infos} : get all the ptLvNodeInfos as a stream.
     *
     * @return the {@link List} of ptLvNodeInfos.
     */
    @GetMapping(value = "/pt-lv-node-infos", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public List<PtLvNodeInfo> getAllPtLvNodeInfosAsStream() {
        log.debug("REST request to get all PtLvNodeInfos as a stream");
        return ptLvNodeInfoRepository.findAll();
    }

    /**
     * {@code GET  /pt-lv-node-infos/:id} : get the "id" ptLvNodeInfo.
     *
     * @param id the id of the ptLvNodeInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ptLvNodeInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pt-lv-node-infos/{id}")
    public ResponseEntity<PtLvNodeInfo> getPtLvNodeInfo(@PathVariable Long id) {
        log.debug("REST request to get PtLvNodeInfo : {}", id);
        Optional<PtLvNodeInfo> ptLvNodeInfo = ptLvNodeInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ptLvNodeInfo);
    }

    /**
     * {@code DELETE  /pt-lv-node-infos/:id} : delete the "id" ptLvNodeInfo.
     *
     * @param id the id of the ptLvNodeInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pt-lv-node-infos/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePtLvNodeInfo(@PathVariable Long id) {
        log.debug("REST request to delete PtLvNodeInfo : {}", id);
        ptLvNodeInfoRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
