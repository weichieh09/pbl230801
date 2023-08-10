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

    private static final String ENTITY_NAME = "vwEventResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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
     * {@code POST  /vw-event-results} : Create a new vwEventResult.
     *
     * @param vwEventResultDTO the vwEventResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vwEventResultDTO, or with status {@code 400 (Bad Request)} if the vwEventResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vw-event-results")
    public ResponseEntity<VwEventResultDTO> createVwEventResult(@RequestBody VwEventResultDTO vwEventResultDTO) throws URISyntaxException {
        log.debug("REST request to save VwEventResult : {}", vwEventResultDTO);
        if (vwEventResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new vwEventResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VwEventResultDTO result = vwEventResultService.save(vwEventResultDTO);
        return ResponseEntity
            .created(new URI("/api/vw-event-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vw-event-results/:id} : Updates an existing vwEventResult.
     *
     * @param id the id of the vwEventResultDTO to save.
     * @param vwEventResultDTO the vwEventResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vwEventResultDTO,
     * or with status {@code 400 (Bad Request)} if the vwEventResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vwEventResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vw-event-results/{id}")
    public ResponseEntity<VwEventResultDTO> updateVwEventResult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VwEventResultDTO vwEventResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VwEventResult : {}, {}", id, vwEventResultDTO);
        if (vwEventResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vwEventResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vwEventResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VwEventResultDTO result = vwEventResultService.update(vwEventResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vwEventResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vw-event-results/:id} : Partial updates given fields of an existing vwEventResult, field will ignore if it is null
     *
     * @param id the id of the vwEventResultDTO to save.
     * @param vwEventResultDTO the vwEventResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vwEventResultDTO,
     * or with status {@code 400 (Bad Request)} if the vwEventResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vwEventResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vwEventResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vw-event-results/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VwEventResultDTO> partialUpdateVwEventResult(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VwEventResultDTO vwEventResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VwEventResult partially : {}, {}", id, vwEventResultDTO);
        if (vwEventResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vwEventResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vwEventResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VwEventResultDTO> result = vwEventResultService.partialUpdate(vwEventResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vwEventResultDTO.getId().toString())
        );
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

    /**
     * {@code DELETE  /vw-event-results/:id} : delete the "id" vwEventResult.
     *
     * @param id the id of the vwEventResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vw-event-results/{id}")
    public ResponseEntity<Void> deleteVwEventResult(@PathVariable Long id) {
        log.debug("REST request to delete VwEventResult : {}", id);
        vwEventResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
