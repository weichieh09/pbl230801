package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.MatchPlayerRepository;
import com.wcc.pbl230801.service.MatchPlayerQueryService;
import com.wcc.pbl230801.service.MatchPlayerService;
import com.wcc.pbl230801.service.criteria.MatchPlayerCriteria;
import com.wcc.pbl230801.service.dto.MatchPlayerDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.MatchPlayer}.
 */
@RestController
@RequestMapping("/api")
public class MatchPlayerResource {

    private final Logger log = LoggerFactory.getLogger(MatchPlayerResource.class);

    private static final String ENTITY_NAME = "matchPlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchPlayerService matchPlayerService;

    private final MatchPlayerRepository matchPlayerRepository;

    private final MatchPlayerQueryService matchPlayerQueryService;

    public MatchPlayerResource(
        MatchPlayerService matchPlayerService,
        MatchPlayerRepository matchPlayerRepository,
        MatchPlayerQueryService matchPlayerQueryService
    ) {
        this.matchPlayerService = matchPlayerService;
        this.matchPlayerRepository = matchPlayerRepository;
        this.matchPlayerQueryService = matchPlayerQueryService;
    }

    /**
     * {@code POST  /match-players} : Create a new matchPlayer.
     *
     * @param matchPlayerDTO the matchPlayerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchPlayerDTO, or with status {@code 400 (Bad Request)} if the matchPlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-players")
    public ResponseEntity<MatchPlayerDTO> createMatchPlayer(@RequestBody MatchPlayerDTO matchPlayerDTO) throws URISyntaxException {
        log.debug("REST request to save MatchPlayer : {}", matchPlayerDTO);
        if (matchPlayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchPlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchPlayerDTO result = matchPlayerService.save(matchPlayerDTO);
        return ResponseEntity
            .created(new URI("/api/match-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-players/:id} : Updates an existing matchPlayer.
     *
     * @param id the id of the matchPlayerDTO to save.
     * @param matchPlayerDTO the matchPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the matchPlayerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-players/{id}")
    public ResponseEntity<MatchPlayerDTO> updateMatchPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatchPlayerDTO matchPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MatchPlayer : {}, {}", id, matchPlayerDTO);
        if (matchPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MatchPlayerDTO result = matchPlayerService.update(matchPlayerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchPlayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /match-players/:id} : Partial updates given fields of an existing matchPlayer, field will ignore if it is null
     *
     * @param id the id of the matchPlayerDTO to save.
     * @param matchPlayerDTO the matchPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the matchPlayerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matchPlayerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matchPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/match-players/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MatchPlayerDTO> partialUpdateMatchPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MatchPlayerDTO matchPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MatchPlayer partially : {}, {}", id, matchPlayerDTO);
        if (matchPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matchPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matchPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatchPlayerDTO> result = matchPlayerService.partialUpdate(matchPlayerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matchPlayerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /match-players} : get all the matchPlayers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchPlayers in body.
     */
    @GetMapping("/match-players")
    public ResponseEntity<List<MatchPlayerDTO>> getAllMatchPlayers(
        MatchPlayerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MatchPlayers by criteria: {}", criteria);
        Page<MatchPlayerDTO> page = matchPlayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /match-players/count} : count all the matchPlayers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/match-players/count")
    public ResponseEntity<Long> countMatchPlayers(MatchPlayerCriteria criteria) {
        log.debug("REST request to count MatchPlayers by criteria: {}", criteria);
        return ResponseEntity.ok().body(matchPlayerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /match-players/:id} : get the "id" matchPlayer.
     *
     * @param id the id of the matchPlayerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchPlayerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-players/{id}")
    public ResponseEntity<MatchPlayerDTO> getMatchPlayer(@PathVariable Long id) {
        log.debug("REST request to get MatchPlayer : {}", id);
        Optional<MatchPlayerDTO> matchPlayerDTO = matchPlayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchPlayerDTO);
    }

    /**
     * {@code DELETE  /match-players/:id} : delete the "id" matchPlayer.
     *
     * @param id the id of the matchPlayerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-players/{id}")
    public ResponseEntity<Void> deleteMatchPlayer(@PathVariable Long id) {
        log.debug("REST request to delete MatchPlayer : {}", id);
        matchPlayerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
