package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.VwWcc701ResultRepository;
import com.wcc.pbl230801.service.VwWcc701ResultQueryService;
import com.wcc.pbl230801.service.VwWcc701ResultService;
import com.wcc.pbl230801.service.criteria.VwWcc701ResultCriteria;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import com.wcc.pbl230801.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.wcc.pbl230801.domain.VwWcc701Result}.
 */
@RestController
@RequestMapping("/api")
public class VwWcc701ResultResource {

    private final Logger log = LoggerFactory.getLogger(VwWcc701ResultResource.class);

    private final VwWcc701ResultService vwWcc701ResultService;

    private final VwWcc701ResultRepository vwWcc701ResultRepository;

    private final VwWcc701ResultQueryService vwWcc701ResultQueryService;

    public VwWcc701ResultResource(
        VwWcc701ResultService vwWcc701ResultService,
        VwWcc701ResultRepository vwWcc701ResultRepository,
        VwWcc701ResultQueryService vwWcc701ResultQueryService
    ) {
        this.vwWcc701ResultService = vwWcc701ResultService;
        this.vwWcc701ResultRepository = vwWcc701ResultRepository;
        this.vwWcc701ResultQueryService = vwWcc701ResultQueryService;
    }

    /**
     * {@code GET  /vw-wcc-701-results} : get all the vwWcc701Results.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vwWcc701Results in body.
     */
    @GetMapping("/vw-wcc-701-results")
    public ResponseEntity<List<VwWcc701ResultDTO>> getAllVwWcc701Results(
        VwWcc701ResultCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VwWcc701Results by criteria: {}", criteria);
        Page<VwWcc701ResultDTO> page = vwWcc701ResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vw-wcc-701-results/count} : count all the vwWcc701Results.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vw-wcc-701-results/count")
    public ResponseEntity<Long> countVwWcc701Results(VwWcc701ResultCriteria criteria) {
        log.debug("REST request to count VwWcc701Results by criteria: {}", criteria);
        return ResponseEntity.ok().body(vwWcc701ResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vw-wcc-701-results/:id} : get the "id" vwWcc701Result.
     *
     * @param id the id of the vwWcc701ResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vwWcc701ResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vw-wcc-701-results/{id}")
    public ResponseEntity<VwWcc701ResultDTO> getVwWcc701Result(@PathVariable Long id) {
        log.debug("REST request to get VwWcc701Result : {}", id);
        Optional<VwWcc701ResultDTO> vwWcc701ResultDTO = vwWcc701ResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vwWcc701ResultDTO);
    }
}
