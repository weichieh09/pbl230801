package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.TeamEvent;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
import com.wcc.pbl230801.service.mapper.TeamEventMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamEvent}.
 */
@Service
@Transactional
public class TeamEventService {

    private final Logger log = LoggerFactory.getLogger(TeamEventService.class);

    private final TeamEventRepository teamEventRepository;

    private final TeamEventMapper teamEventMapper;

    public TeamEventService(TeamEventRepository teamEventRepository, TeamEventMapper teamEventMapper) {
        this.teamEventRepository = teamEventRepository;
        this.teamEventMapper = teamEventMapper;
    }

    /**
     * Save a teamEvent.
     *
     * @param teamEventDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamEventDTO save(TeamEventDTO teamEventDTO) {
        log.debug("Request to save TeamEvent : {}", teamEventDTO);
        TeamEvent teamEvent = teamEventMapper.toEntity(teamEventDTO);
        teamEvent = teamEventRepository.save(teamEvent);
        return teamEventMapper.toDto(teamEvent);
    }

    /**
     * Update a teamEvent.
     *
     * @param teamEventDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamEventDTO update(TeamEventDTO teamEventDTO) {
        log.debug("Request to update TeamEvent : {}", teamEventDTO);
        TeamEvent teamEvent = teamEventMapper.toEntity(teamEventDTO);
        teamEvent = teamEventRepository.save(teamEvent);
        return teamEventMapper.toDto(teamEvent);
    }

    /**
     * Partially update a teamEvent.
     *
     * @param teamEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeamEventDTO> partialUpdate(TeamEventDTO teamEventDTO) {
        log.debug("Request to partially update TeamEvent : {}", teamEventDTO);

        return teamEventRepository
            .findById(teamEventDTO.getId())
            .map(existingTeamEvent -> {
                teamEventMapper.partialUpdate(existingTeamEvent, teamEventDTO);

                return existingTeamEvent;
            })
            .map(teamEventRepository::save)
            .map(teamEventMapper::toDto);
    }

    /**
     * Get all the teamEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamEvents");
        return teamEventRepository.findAll(pageable).map(teamEventMapper::toDto);
    }

    /**
     * Get one teamEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeamEventDTO> findOne(Long id) {
        log.debug("Request to get TeamEvent : {}", id);
        return teamEventRepository.findById(id).map(teamEventMapper::toDto);
    }

    /**
     * Delete the teamEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeamEvent : {}", id);
        teamEventRepository.deleteById(id);
    }
}
