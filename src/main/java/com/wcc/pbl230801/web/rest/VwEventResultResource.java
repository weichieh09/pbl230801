package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.VwEventResultQueryService;
import com.wcc.pbl230801.service.VwEventResultService;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.dto.VwEventResultDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.VwEventResult}.
 */
@RestController
@RequestMapping("/api")
public class VwEventResultResource {

    private final Logger log = LoggerFactory.getLogger(VwEventResultResource.class);

    private final VwEventResultService vwEventResultService;

    private final VwEventResultRepository vwEventResultRepository;

    private final VwEventResultQueryService vwEventResultQueryService;

    public VwEventResultResource(
        VwEventResultService vwEventResultService,
        VwEventResultRepository vwEventResultRepository,
        VwEventResultQueryService vwEventResultQueryService
    ) {
        this.vwEventResultService = vwEventResultService;
        this.vwEventResultRepository = vwEventResultRepository;
        this.vwEventResultQueryService = vwEventResultQueryService;
    }

    /**
     * {@code GET  /vw-event-results} : get all the vwEventResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vwEventResults in body.
     */
    @GetMapping("/vw-event-results")
    public ResponseEntity<List<VwEventResultDTO>> getAllVwEventResults(
        VwEventResultCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VwEventResults by criteria: {}", criteria);
        Page<VwEventResultDTO> page = vwEventResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vw-event-results/count} : count all the vwEventResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vw-event-results/count")
    public ResponseEntity<Long> countVwEventResults(VwEventResultCriteria criteria) {
        log.debug("REST request to count VwEventResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(vwEventResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vw-event-results/:id} : get the "id" vwEventResult.
     *
     * @param id the id of the vwEventResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vwEventResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vw-event-results/{id}")
    public ResponseEntity<VwEventResultDTO> getVwEventResult(@PathVariable Long id) {
        log.debug("REST request to get VwEventResult : {}", id);
        Optional<VwEventResultDTO> vwEventResultDTO = vwEventResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vwEventResultDTO);
    }
}
