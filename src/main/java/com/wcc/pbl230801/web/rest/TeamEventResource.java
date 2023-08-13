package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.service.TeamEventQueryService;
import com.wcc.pbl230801.service.TeamEventService;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.TeamEvent}.
 */
@RestController
@RequestMapping("/api")
public class TeamEventResource {

    private final Logger log = LoggerFactory.getLogger(TeamEventResource.class);

    private static final String ENTITY_NAME = "teamEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamEventService teamEventService;

    private final TeamEventRepository teamEventRepository;

    private final TeamEventQueryService teamEventQueryService;

    public TeamEventResource(
        TeamEventService teamEventService,
        TeamEventRepository teamEventRepository,
        TeamEventQueryService teamEventQueryService
    ) {
        this.teamEventService = teamEventService;
        this.teamEventRepository = teamEventRepository;
        this.teamEventQueryService = teamEventQueryService;
    }

    /**
     * {@code POST  /team-events} : Create a new teamEvent.
     *
     * @param teamEventDTO the teamEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamEventDTO, or with status {@code 400 (Bad Request)} if the teamEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-events")
    public ResponseEntity<TeamEventDTO> createTeamEvent(@RequestBody TeamEventDTO teamEventDTO) throws URISyntaxException {
        log.debug("REST request to save TeamEvent : {}", teamEventDTO);
        if (teamEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new teamEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamEventDTO result = teamEventService.save(teamEventDTO);
        return ResponseEntity
            .created(new URI("/api/team-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-events/:id} : Updates an existing teamEvent.
     *
     * @param id the id of the teamEventDTO to save.
     * @param teamEventDTO the teamEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamEventDTO,
     * or with status {@code 400 (Bad Request)} if the teamEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-events/{id}")
    public ResponseEntity<TeamEventDTO> updateTeamEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamEventDTO teamEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeamEvent : {}, {}", id, teamEventDTO);
        if (teamEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamEventDTO result = teamEventService.update(teamEventDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teamEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-events/:id} : Partial updates given fields of an existing teamEvent, field will ignore if it is null
     *
     * @param id the id of the teamEventDTO to save.
     * @param teamEventDTO the teamEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamEventDTO,
     * or with status {@code 400 (Bad Request)} if the teamEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teamEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamEventDTO> partialUpdateTeamEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamEventDTO teamEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamEvent partially : {}, {}", id, teamEventDTO);
        if (teamEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamEventDTO> result = teamEventService.partialUpdate(teamEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teamEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /team-events} : get all the teamEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamEvents in body.
     */
    @GetMapping("/team-events")
    public ResponseEntity<List<TeamEventDTO>> getAllTeamEvents(
        TeamEventCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TeamEvents by criteria: {}", criteria);
        Page<TeamEventDTO> page = teamEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-events/count} : count all the teamEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/team-events/count")
    public ResponseEntity<Long> countTeamEvents(TeamEventCriteria criteria) {
        log.debug("REST request to count TeamEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /team-events/:id} : get the "id" teamEvent.
     *
     * @param id the id of the teamEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-events/{id}")
    public ResponseEntity<TeamEventDTO> getTeamEvent(@PathVariable Long id) {
        log.debug("REST request to get TeamEvent : {}", id);
        Optional<TeamEventDTO> teamEventDTO = teamEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamEventDTO);
    }

    /**
     * {@code DELETE  /team-events/:id} : delete the "id" teamEvent.
     *
     * @param id the id of the teamEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-events/{id}")
    public ResponseEntity<Void> deleteTeamEvent(@PathVariable Long id) {
        log.debug("REST request to delete TeamEvent : {}", id);
        teamEventService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
