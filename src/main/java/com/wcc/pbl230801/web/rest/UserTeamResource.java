package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.UserTeamRepository;
import com.wcc.pbl230801.service.UserTeamQueryService;
import com.wcc.pbl230801.service.UserTeamService;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.UserTeam}.
 */
@RestController
@RequestMapping("/api")
public class UserTeamResource {

    private final Logger log = LoggerFactory.getLogger(UserTeamResource.class);

    private static final String ENTITY_NAME = "userTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTeamService userTeamService;

    private final UserTeamRepository userTeamRepository;

    private final UserTeamQueryService userTeamQueryService;

    public UserTeamResource(
        UserTeamService userTeamService,
        UserTeamRepository userTeamRepository,
        UserTeamQueryService userTeamQueryService
    ) {
        this.userTeamService = userTeamService;
        this.userTeamRepository = userTeamRepository;
        this.userTeamQueryService = userTeamQueryService;
    }

    /**
     * {@code POST  /user-teams} : Create a new userTeam.
     *
     * @param userTeamDTO the userTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTeamDTO, or with status {@code 400 (Bad Request)} if the userTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-teams")
    public ResponseEntity<UserTeamDTO> createUserTeam(@RequestBody UserTeamDTO userTeamDTO) throws URISyntaxException {
        log.debug("REST request to save UserTeam : {}", userTeamDTO);
        if (userTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTeamDTO result = userTeamService.save(userTeamDTO);
        return ResponseEntity
            .created(new URI("/api/user-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-teams/:id} : Updates an existing userTeam.
     *
     * @param id the id of the userTeamDTO to save.
     * @param userTeamDTO the userTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTeamDTO,
     * or with status {@code 400 (Bad Request)} if the userTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-teams/{id}")
    public ResponseEntity<UserTeamDTO> updateUserTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTeamDTO userTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserTeam : {}, {}", id, userTeamDTO);
        if (userTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTeamDTO result = userTeamService.update(userTeamDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-teams/:id} : Partial updates given fields of an existing userTeam, field will ignore if it is null
     *
     * @param id the id of the userTeamDTO to save.
     * @param userTeamDTO the userTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTeamDTO,
     * or with status {@code 400 (Bad Request)} if the userTeamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTeamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-teams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTeamDTO> partialUpdateUserTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTeamDTO userTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserTeam partially : {}, {}", id, userTeamDTO);
        if (userTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTeamDTO> result = userTeamService.partialUpdate(userTeamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTeamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-teams} : get all the userTeams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTeams in body.
     */
    @GetMapping("/user-teams")
    public ResponseEntity<List<UserTeamDTO>> getAllUserTeams(
        UserTeamCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserTeams by criteria: {}", criteria);
        Page<UserTeamDTO> page = userTeamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-teams/count} : count all the userTeams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-teams/count")
    public ResponseEntity<Long> countUserTeams(UserTeamCriteria criteria) {
        log.debug("REST request to count UserTeams by criteria: {}", criteria);
        return ResponseEntity.ok().body(userTeamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-teams/:id} : get the "id" userTeam.
     *
     * @param id the id of the userTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-teams/{id}")
    public ResponseEntity<UserTeamDTO> getUserTeam(@PathVariable Long id) {
        log.debug("REST request to get UserTeam : {}", id);
        Optional<UserTeamDTO> userTeamDTO = userTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTeamDTO);
    }

    /**
     * {@code DELETE  /user-teams/:id} : delete the "id" userTeam.
     *
     * @param id the id of the userTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-teams/{id}")
    public ResponseEntity<Void> deleteUserTeam(@PathVariable Long id) {
        log.debug("REST request to delete UserTeam : {}", id);
        userTeamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
