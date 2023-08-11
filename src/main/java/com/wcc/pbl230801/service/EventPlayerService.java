package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.EventPlayer;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import com.wcc.pbl230801.service.mapper.EventPlayerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventPlayer}.
 */
@Service
@Transactional
public class EventPlayerService {

    private final Logger log = LoggerFactory.getLogger(EventPlayerService.class);

    private final EventPlayerRepository eventPlayerRepository;

    private final EventPlayerMapper eventPlayerMapper;

    public EventPlayerService(EventPlayerRepository eventPlayerRepository, EventPlayerMapper eventPlayerMapper) {
        this.eventPlayerRepository = eventPlayerRepository;
        this.eventPlayerMapper = eventPlayerMapper;
    }

    /**
     * Save a eventPlayer.
     *
     * @param eventPlayerDTO the entity to save.
     * @return the persisted entity.
     */
    public EventPlayerDTO save(EventPlayerDTO eventPlayerDTO) {
        log.debug("Request to save EventPlayer : {}", eventPlayerDTO);
        EventPlayer eventPlayer = eventPlayerMapper.toEntity(eventPlayerDTO);
        eventPlayer = eventPlayerRepository.save(eventPlayer);
        return eventPlayerMapper.toDto(eventPlayer);
    }

    /**
     * Update a eventPlayer.
     *
     * @param eventPlayerDTO the entity to save.
     * @return the persisted entity.
     */
    public EventPlayerDTO update(EventPlayerDTO eventPlayerDTO) {
        log.debug("Request to update EventPlayer : {}", eventPlayerDTO);
        EventPlayer eventPlayer = eventPlayerMapper.toEntity(eventPlayerDTO);
        eventPlayer = eventPlayerRepository.save(eventPlayer);
        return eventPlayerMapper.toDto(eventPlayer);
    }

    /**
     * Partially update a eventPlayer.
     *
     * @param eventPlayerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventPlayerDTO> partialUpdate(EventPlayerDTO eventPlayerDTO) {
        log.debug("Request to partially update EventPlayer : {}", eventPlayerDTO);

        return eventPlayerRepository
            .findById(eventPlayerDTO.getId())
            .map(existingEventPlayer -> {
                eventPlayerMapper.partialUpdate(existingEventPlayer, eventPlayerDTO);

                return existingEventPlayer;
            })
            .map(eventPlayerRepository::save)
            .map(eventPlayerMapper::toDto);
    }

    /**
     * Get all the eventPlayers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventPlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventPlayers");
        return eventPlayerRepository.findAll(pageable).map(eventPlayerMapper::toDto);
    }

    /**
     * Get one eventPlayer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventPlayerDTO> findOne(Long id) {
        log.debug("Request to get EventPlayer : {}", id);
        return eventPlayerRepository.findById(id).map(eventPlayerMapper::toDto);
    }

    /**
     * Delete the eventPlayer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventPlayer : {}", id);
        eventPlayerRepository.deleteById(id);
    }
}
