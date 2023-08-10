package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.MatchZRepository;
import com.wcc.pbl230801.service.MatchZQueryService;
import com.wcc.pbl230801.service.MatchZService;
import com.wcc.pbl230801.service.criteria.MatchZCriteria;
import com.wcc.pbl230801.service.dto.MatchZDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.MatchZ}.
 */
@RestController
@RequestMapping("/api")
public class MatchZResource {

    private final Logger log = LoggerFactory.getLogger(MatchZResource.class);

    private static final String ENTITY_NAME = "matchZ";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchZService matchZService;

    private final MatchZRepository matchZRepository;

    private final MatchZQueryService matchZQueryService;

    public MatchZResource(MatchZService matchZService, MatchZRepository matchZRepository, MatchZQueryService matchZQueryService) {
        this.matchZService = matchZService;
        this.matchZRepository = matchZRepository;
        this.matchZQueryService = matchZQueryService;
    }

    /**
     * {@code POST  /match-zs} : Create a new matchZ.
     *
     * @param matchZDTO the matchZDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchZDTO, or with status {@code 400 (Bad Request)} if the matchZ has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-zs")
    public ResponseEntity<MatchZDTO> createMatchZ(@RequestBody MatchZDTO matchZDTO) throws URISyntaxException {
        log.debug("REST request to save MatchZ : {}", matchZDTO);
        if (matchZDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchZ cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchZDTO result = matchZService.save(matchZDTO);
        return ResponseEntity
            .created(new URI("/api/match-zs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-zs/:id} : Updates an existing matchZ.
     *
     * @param id the id of the matchZDTO to save.
     * @param matchZDTO the matchZDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchZDTO,
     * or with status {@code 400 (Bad Request)} if the matchZDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchZDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-zs/{id}")
    public ResponseEntity<MatchZDTO> updateMatchZ(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatchZDTO matchZDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchZ : {}, {}", id, matchZDTO);
        if (matchZDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchZDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchZRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatchZDTO result = matchZService.update(matchZDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchZDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /match-zs/:id} : Partial updates given fields of an existing matchZ, field will ignore if it is null
     *
     * @param id the id of the matchZDTO to save.
     * @param matchZDTO the matchZDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchZDTO,
     * or with status {@code 400 (Bad Request)} if the matchZDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchZDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchZDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/match-zs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchZDTO> partialUpdateMatchZ(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatchZDTO matchZDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchZ partially : {}, {}", id, matchZDTO);
        if (matchZDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchZDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchZRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchZDTO> result = matchZService.partialUpdate(matchZDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchZDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /match-zs} : get all the matchZS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchZS in body.
     */
    @GetMapping("/match-zs")
    public ResponseEntity<List<MatchZDTO>> getAllMatchZS(
        MatchZCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MatchZS by criteria: {}", criteria);
        Page<MatchZDTO> page = matchZQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-zs/count} : count all the matchZS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/match-zs/count")
    public ResponseEntity<Long> countMatchZS(MatchZCriteria criteria) {
        log.debug("REST request to count MatchZS by criteria: {}", criteria);
        return ResponseEntity.ok().body(matchZQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /match-zs/:id} : get the "id" matchZ.
     *
     * @param id the id of the matchZDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchZDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-zs/{id}")
    public ResponseEntity<MatchZDTO> getMatchZ(@PathVariable Long id) {
        log.debug("REST request to get MatchZ : {}", id);
        Optional<MatchZDTO> matchZDTO = matchZService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchZDTO);
    }

    /**
     * {@code DELETE  /match-zs/:id} : delete the "id" matchZ.
     *
     * @param id the id of the matchZDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-zs/{id}")
    public ResponseEntity<Void> deleteMatchZ(@PathVariable Long id) {
        log.debug("REST request to delete MatchZ : {}", id);
        matchZService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
