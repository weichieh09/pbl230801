package com.wcc.pbl230801.web.rest;

import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.EventZService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
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
 * REST controller for managing {@link com.wcc.pbl230801.domain.EventZ}.
 */
@RestController
@RequestMapping("/api")
public class EventZResource {

    private final Logger log = LoggerFactory.getLogger(EventZResource.class);

    private static final String ENTITY_NAME = "eventZ";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventZService eventZService;

    private final EventZRepository eventZRepository;

    private final EventZQueryService eventZQueryService;

    public EventZResource(EventZService eventZService, EventZRepository eventZRepository, EventZQueryService eventZQueryService) {
        this.eventZService = eventZService;
        this.eventZRepository = eventZRepository;
        this.eventZQueryService = eventZQueryService;
    }

    /**
     * {@code POST  /event-zs} : Create a new eventZ.
     *
     * @param eventZDTO the eventZDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventZDTO, or with status {@code 400 (Bad Request)} if the eventZ has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-zs")
    public ResponseEntity<EventZDTO> createEventZ(@RequestBody EventZDTO eventZDTO) throws URISyntaxException {
        log.debug("REST request to save EventZ : {}", eventZDTO);
        if (eventZDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventZ cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventZDTO result = eventZService.save(eventZDTO);
        return ResponseEntity
            .created(new URI("/api/event-zs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-zs/:id} : Updates an existing eventZ.
     *
     * @param id the id of the eventZDTO to save.
     * @param eventZDTO the eventZDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventZDTO,
     * or with status {@code 400 (Bad Request)} if the eventZDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventZDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-zs/{id}")
    public ResponseEntity<EventZDTO> updateEventZ(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventZDTO eventZDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventZ : {}, {}", id, eventZDTO);
        if (eventZDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventZDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventZRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventZDTO result = eventZService.update(eventZDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventZDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-zs/:id} : Partial updates given fields of an existing eventZ, field will ignore if it is null
     *
     * @param id the id of the eventZDTO to save.
     * @param eventZDTO the eventZDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventZDTO,
     * or with status {@code 400 (Bad Request)} if the eventZDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventZDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventZDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-zs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventZDTO> partialUpdateEventZ(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventZDTO eventZDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventZ partially : {}, {}", id, eventZDTO);
        if (eventZDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventZDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventZRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventZDTO> result = eventZService.partialUpdate(eventZDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventZDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-zs} : get all the eventZS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventZS in body.
     */
    @GetMapping("/event-zs")
    public ResponseEntity<List<EventZDTO>> getAllEventZS(
        EventZCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EventZS by criteria: {}", criteria);
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-zs/count} : count all the eventZS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/event-zs/count")
    public ResponseEntity<Long> countEventZS(EventZCriteria criteria) {
        log.debug("REST request to count EventZS by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventZQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-zs/:id} : get the "id" eventZ.
     *
     * @param id the id of the eventZDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventZDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-zs/{id}")
    public ResponseEntity<EventZDTO> getEventZ(@PathVariable Long id) {
        log.debug("REST request to get EventZ : {}", id);
        Optional<EventZDTO> eventZDTO = eventZService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventZDTO);
    }

    /**
     * {@code DELETE  /event-zs/:id} : delete the "id" eventZ.
     *
     * @param id the id of the eventZDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-zs/{id}")
    public ResponseEntity<Void> deleteEventZ(@PathVariable Long id) {
        log.debug("REST request to delete EventZ : {}", id);
        eventZService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
