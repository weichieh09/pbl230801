package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.service.EventPlayerQueryService;
import com.wcc.pbl230801.service.EventPlayerService;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.EventPlayer}.
 */
@RestController
@RequestMapping("/api")
public class EventPlayerResource {

    private final Logger log = LoggerFactory.getLogger(EventPlayerResource.class);

    private static final String ENTITY_NAME = "eventPlayer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventPlayerService eventPlayerService;

    private final EventPlayerRepository eventPlayerRepository;

    private final EventPlayerQueryService eventPlayerQueryService;

    public EventPlayerResource(
        EventPlayerService eventPlayerService,
        EventPlayerRepository eventPlayerRepository,
        EventPlayerQueryService eventPlayerQueryService
    ) {
        this.eventPlayerService = eventPlayerService;
        this.eventPlayerRepository = eventPlayerRepository;
        this.eventPlayerQueryService = eventPlayerQueryService;
    }

    /**
     * {@code POST  /event-players} : Create a new eventPlayer.
     *
     * @param eventPlayerDTO the eventPlayerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventPlayerDTO, or with status {@code 400 (Bad Request)} if the eventPlayer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-players")
    public ResponseEntity<EventPlayerDTO> createEventPlayer(@RequestBody EventPlayerDTO eventPlayerDTO) throws URISyntaxException {
        log.debug("REST request to save EventPlayer : {}", eventPlayerDTO);
        if (eventPlayerDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventPlayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventPlayerDTO result = eventPlayerService.save(eventPlayerDTO);
        return ResponseEntity
            .created(new URI("/api/event-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-players/:id} : Updates an existing eventPlayer.
     *
     * @param id the id of the eventPlayerDTO to save.
     * @param eventPlayerDTO the eventPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the eventPlayerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-players/{id}")
    public ResponseEntity<EventPlayerDTO> updateEventPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventPlayerDTO eventPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventPlayer : {}, {}", id, eventPlayerDTO);
        if (eventPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventPlayerDTO result = eventPlayerService.update(eventPlayerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventPlayerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-players/:id} : Partial updates given fields of an existing eventPlayer, field will ignore if it is null
     *
     * @param id the id of the eventPlayerDTO to save.
     * @param eventPlayerDTO the eventPlayerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventPlayerDTO,
     * or with status {@code 400 (Bad Request)} if the eventPlayerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventPlayerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventPlayerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-players/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventPlayerDTO> partialUpdateEventPlayer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventPlayerDTO eventPlayerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventPlayer partially : {}, {}", id, eventPlayerDTO);
        if (eventPlayerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventPlayerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventPlayerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventPlayerDTO> result = eventPlayerService.partialUpdate(eventPlayerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventPlayerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-players} : get all the eventPlayers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventPlayers in body.
     */
    @GetMapping("/event-players")
    public ResponseEntity<List<EventPlayerDTO>> getAllEventPlayers(
        EventPlayerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventPlayers by criteria: {}", criteria);
        Page<EventPlayerDTO> page = eventPlayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-players/count} : count all the eventPlayers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-players/count")
    public ResponseEntity<Long> countEventPlayers(EventPlayerCriteria criteria) {
        log.debug("REST request to count EventPlayers by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventPlayerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-players/:id} : get the "id" eventPlayer.
     *
     * @param id the id of the eventPlayerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventPlayerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-players/{id}")
    public ResponseEntity<EventPlayerDTO> getEventPlayer(@PathVariable Long id) {
        log.debug("REST request to get EventPlayer : {}", id);
        Optional<EventPlayerDTO> eventPlayerDTO = eventPlayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventPlayerDTO);
    }

    /**
     * {@code DELETE  /event-players/:id} : delete the "id" eventPlayer.
     *
     * @param id the id of the eventPlayerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-players/{id}")
    public ResponseEntity<Void> deleteEventPlayer(@PathVariable Long id) {
        log.debug("REST request to delete EventPlayer : {}", id);
        eventPlayerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
