package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.mapper.EventZMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventZ}.
 */
@Service
@Transactional
public class EventZService {

    private final Logger log = LoggerFactory.getLogger(EventZService.class);

    private final EventZRepository eventZRepository;

    private final EventZMapper eventZMapper;

    public EventZService(EventZRepository eventZRepository, EventZMapper eventZMapper) {
        this.eventZRepository = eventZRepository;
        this.eventZMapper = eventZMapper;
    }

    /**
     * Save a eventZ.
     *
     * @param eventZDTO the entity to save.
     * @return the persisted entity.
     */
    public EventZDTO save(EventZDTO eventZDTO) {
        log.debug("Request to save EventZ : {}", eventZDTO);
        EventZ eventZ = eventZMapper.toEntity(eventZDTO);
        eventZ = eventZRepository.save(eventZ);
        return eventZMapper.toDto(eventZ);
    }

    /**
     * Update a eventZ.
     *
     * @param eventZDTO the entity to save.
     * @return the persisted entity.
     */
    public EventZDTO update(EventZDTO eventZDTO) {
        log.debug("Request to update EventZ : {}", eventZDTO);
        EventZ eventZ = eventZMapper.toEntity(eventZDTO);
        eventZ = eventZRepository.save(eventZ);
        return eventZMapper.toDto(eventZ);
    }

    /**
     * Partially update a eventZ.
     *
     * @param eventZDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventZDTO> partialUpdate(EventZDTO eventZDTO) {
        log.debug("Request to partially update EventZ : {}", eventZDTO);

        return eventZRepository
            .findById(eventZDTO.getId())
            .map(existingEventZ -> {
                eventZMapper.partialUpdate(existingEventZ, eventZDTO);

                return existingEventZ;
            })
            .map(eventZRepository::save)
            .map(eventZMapper::toDto);
    }

    /**
     * Get all the eventZS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventZDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventZS");
        return eventZRepository.findAll(pageable).map(eventZMapper::toDto);
    }

    /**
     * Get one eventZ by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventZDTO> findOne(Long id) {
        log.debug("Request to get EventZ : {}", id);
        return eventZRepository.findById(id).map(eventZMapper::toDto);
    }

    /**
     * Delete the eventZ by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventZ : {}", id);
        eventZRepository.deleteById(id);
    }
}
