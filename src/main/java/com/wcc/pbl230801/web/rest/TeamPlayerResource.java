package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.service.TeamPlayerQueryService;
import com.wcc.pbl230801.service.TeamPlayerService;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.TeamPlayer}.
 */
@RestController
@RequestMapping("/api")
public class TeamPlayerResource {

    private final Logger log = LoggerFactory.getLogger(TeamPlayerResource.class);

    private static final String ENTITY_NAME = "teamPlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamPlayerService teamPlayerService;

    private final TeamPlayerRepository teamPlayerRepository;

    private final TeamPlayerQueryService teamPlayerQueryService;

    public TeamPlayerResource(
        TeamPlayerService teamPlayerService,
        TeamPlayerRepository teamPlayerRepository,
        TeamPlayerQueryService teamPlayerQueryService
    ) {
        this.teamPlayerService = teamPlayerService;
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamPlayerQueryService = teamPlayerQueryService;
    }

    /**
     * {@code POST  /team-players} : Create a new teamPlayer.
     *
     * @param teamPlayerDTO the teamPlayerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamPlayerDTO, or with status {@code 400 (Bad Request)} if the teamPlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-players")
    public ResponseEntity<TeamPlayerDTO> createTeamPlayer(@RequestBody TeamPlayerDTO teamPlayerDTO) throws URISyntaxException {
        log.debug("REST request to save TeamPlayer : {}", teamPlayerDTO);
        if (teamPlayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new teamPlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamPlayerDTO result = teamPlayerService.save(teamPlayerDTO);
        return ResponseEntity
            .created(new URI("/api/team-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-players/:id} : Updates an existing teamPlayer.
     *
     * @param id the id of the teamPlayerDTO to save.
     * @param teamPlayerDTO the teamPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the teamPlayerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-players/{id}")
    public ResponseEntity<TeamPlayerDTO> updateTeamPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamPlayerDTO teamPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeamPlayer : {}, {}", id, teamPlayerDTO);
        if (teamPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeamPlayerDTO result = teamPlayerService.update(teamPlayerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teamPlayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /team-players/:id} : Partial updates given fields of an existing teamPlayer, field will ignore if it is null
     *
     * @param id the id of the teamPlayerDTO to save.
     * @param teamPlayerDTO the teamPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the teamPlayerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teamPlayerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teamPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/team-players/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamPlayerDTO> partialUpdateTeamPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeamPlayerDTO teamPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeamPlayer partially : {}, {}", id, teamPlayerDTO);
        if (teamPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teamPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teamPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeamPlayerDTO> result = teamPlayerService.partialUpdate(teamPlayerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teamPlayerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /team-players} : get all the teamPlayers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamPlayers in body.
     */
    @GetMapping("/team-players")
    public ResponseEntity<List<TeamPlayerDTO>> getAllTeamPlayers(
        TeamPlayerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TeamPlayers by criteria: {}", criteria);
        Page<TeamPlayerDTO> page = teamPlayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /team-players/count} : count all the teamPlayers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/team-players/count")
    public ResponseEntity<Long> countTeamPlayers(TeamPlayerCriteria criteria) {
        log.debug("REST request to count TeamPlayers by criteria: {}", criteria);
        return ResponseEntity.ok().body(teamPlayerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /team-players/:id} : get the "id" teamPlayer.
     *
     * @param id the id of the teamPlayerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamPlayerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-players/{id}")
    public ResponseEntity<TeamPlayerDTO> getTeamPlayer(@PathVariable Long id) {
        log.debug("REST request to get TeamPlayer : {}", id);
        Optional<TeamPlayerDTO> teamPlayerDTO = teamPlayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamPlayerDTO);
    }

    /**
     * {@code DELETE  /team-players/:id} : delete the "id" teamPlayer.
     *
     * @param id the id of the teamPlayerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-players/{id}")
    public ResponseEntity<Void> deleteTeamPlayer(@PathVariable Long id) {
        log.debug("REST request to delete TeamPlayer : {}", id);
        teamPlayerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
