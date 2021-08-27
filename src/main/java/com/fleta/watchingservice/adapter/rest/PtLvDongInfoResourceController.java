package com.fleta.watchingservice.adapter.rest;

import com.fleta.watchingservice.adapter.rest.errors.BadRequestAlertException;
import com.fleta.watchingservice.domain.model.PtLvDongInfo;
import com.fleta.watchingservice.port.PtLvDongInfoRepository;
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
import reactor.core.publisher.Flux;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link PtLvDongInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PtLvDongInfoResourceController {

    private final Logger log = LoggerFactory.getLogger(PtLvDongInfoResourceController.class);

    private static final String ENTITY_NAME = "watchingServicePtLvDongInfo";

    @Value("${mega.clientApp.name}")
    private String applicationName;

    private final PtLvDongInfoRepository ptLvDongInfoRepository;

    public PtLvDongInfoResourceController(PtLvDongInfoRepository ptLvDongInfoRepository) {
        this.ptLvDongInfoRepository = ptLvDongInfoRepository;
    }

    /**
     * {@code POST  /pt-lv-dong-infos} : Create a new ptLvDongInfo.
     *
     * @param ptLvDongInfo the ptLvDongInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ptLvDongInfo, or with status {@code 400 (Bad Request)} if the ptLvDongInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pt-lv-dong-infos")
    public ResponseEntity<PtLvDongInfo> createPtLvDongInfo(@Valid @RequestBody PtLvDongInfo ptLvDongInfo) throws URISyntaxException {
        log.debug("REST request to save PtLvDongInfo : {}", ptLvDongInfo);
        if (ptLvDongInfo.getId() != null) {
            throw new BadRequestAlertException("A new ptLvDongInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PtLvDongInfo result = ptLvDongInfoRepository.save(ptLvDongInfo);
        try {
            return ResponseEntity
                    .created(new URI("/api/pt-lv-dong-infos/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code PUT  /pt-lv-dong-infos/:id} : Updates an existing ptLvDongInfo.
     *
     * @param id           the id of the ptLvDongInfo to save.
     * @param ptLvDongInfo the ptLvDongInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptLvDongInfo,
     * or with status {@code 400 (Bad Request)} if the ptLvDongInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ptLvDongInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pt-lv-dong-infos/{id}")
    public ResponseEntity<PtLvDongInfo> updatePtLvDongInfo(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody PtLvDongInfo ptLvDongInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PtLvDongInfo : {}, {}", id, ptLvDongInfo);
        if (ptLvDongInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ptLvDongInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        boolean exists = ptLvDongInfoRepository.existsById(id);
        if (!exists) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PtLvDongInfo result = ptLvDongInfoRepository.save(ptLvDongInfo);
        return ResponseEntity
                .ok()
                .headers(
                        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString())
                )
                .body(result);

    }

    /**
     * {@code PATCH  /pt-lv-dong-infos/:id} : Partial updates given fields of an existing ptLvDongInfo, field will ignore if it is null
     *
     * @param id           the id of the ptLvDongInfo to save.
     * @param ptLvDongInfo the ptLvDongInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptLvDongInfo,
     * or with status {@code 400 (Bad Request)} if the ptLvDongInfo is not valid,
     * or with status {@code 404 (Not Found)} if the ptLvDongInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the ptLvDongInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pt-lv-dong-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PtLvDongInfo> partialUpdatePtLvDongInfo(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PtLvDongInfo ptLvDongInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PtLvDongInfo partially : {}, {}", id, ptLvDongInfo);
        if (ptLvDongInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ptLvDongInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }


        boolean exists = ptLvDongInfoRepository.existsById(id);
        if (!exists) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<PtLvDongInfo> result = ptLvDongInfoRepository
                .findById(ptLvDongInfo.getId())
                .map(
                        existingPtLvDongInfo -> {
                            if (ptLvDongInfo.getDongCd() != null) {
                                existingPtLvDongInfo.setDongCd(ptLvDongInfo.getDongCd());
                            }
                            if (ptLvDongInfo.getZipNo() != null) {
                                existingPtLvDongInfo.setZipNo(ptLvDongInfo.getZipNo());
                            }
                            if (ptLvDongInfo.getAddr() != null) {
                                existingPtLvDongInfo.setAddr(ptLvDongInfo.getAddr());
                            }
                            if (ptLvDongInfo.getDetailAddr() != null) {
                                existingPtLvDongInfo.setDetailAddr(ptLvDongInfo.getDetailAddr());
                            }
                            if (ptLvDongInfo.getRegionKey() != null) {
                                existingPtLvDongInfo.setRegionKey(ptLvDongInfo.getRegionKey());
                            }
                            if (ptLvDongInfo.getSubNodeCd() != null) {
                                existingPtLvDongInfo.setSubNodeCd(ptLvDongInfo.getSubNodeCd());
                            }
                            if (ptLvDongInfo.getNscSubNodeCd() != null) {
                                existingPtLvDongInfo.setNscSubNodeCd(ptLvDongInfo.getNscSubNodeCd());
                            }

                            return existingPtLvDongInfo;
                        }
                )
                .map(ptLvDongInfoRepository::save);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.get().getId().toString()))
                .body(result.get());
    }

    /**
     * {@code GET  /pt-lv-dong-infos} : get all the ptLvDongInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptLvDongInfos in body.
     */
    @GetMapping("/pt-lv-dong-infos")
    public List<PtLvDongInfo> getAllPtLvDongInfos() {
        log.debug("REST request to get all PtLvDongInfos");

        return ptLvDongInfoRepository.findAll();
    }

    /**
     * {@code GET  /pt-lv-dong-infos} : get all the ptLvDongInfos as a stream.
     *
     * @return the {@link Flux} of ptLvDongInfos.
     */
    @GetMapping(value = "/pt-lv-dong-infos", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public List<PtLvDongInfo> getAllPtLvDongInfosAsStream() {
        log.debug("REST request to get all PtLvDongInfos as a stream");
        return ptLvDongInfoRepository.findAll();
    }

    /**
     * {@code GET  /pt-lv-dong-infos/:id} : get the "id" ptLvDongInfo.
     *
     * @param id the id of the ptLvDongInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ptLvDongInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pt-lv-dong-infos/{id}")
    public ResponseEntity<PtLvDongInfo> getPtLvDongInfo(@PathVariable Long id) {
        log.debug("REST request to get PtLvDongInfo : {}", id);
        Optional<PtLvDongInfo> ptLvDongInfo = ptLvDongInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ptLvDongInfo);
    }

    /**
     * {@code DELETE  /pt-lv-dong-infos/:id} : delete the "id" ptLvDongInfo.
     *
     * @param id the id of the ptLvDongInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pt-lv-dong-infos/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePtLvDongInfo(@PathVariable Long id) {
        log.debug("REST request to delete PtLvDongInfo : {}", id);
        ptLvDongInfoRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
