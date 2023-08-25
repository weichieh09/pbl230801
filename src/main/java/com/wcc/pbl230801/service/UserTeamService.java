package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.UserTeam;
import com.wcc.pbl230801.repository.UserTeamRepository;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import com.wcc.pbl230801.service.mapper.UserTeamMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserTeam}.
 */
@Service
@Transactional
public class UserTeamService {

    private final Logger log = LoggerFactory.getLogger(UserTeamService.class);

    private final UserTeamRepository userTeamRepository;

    private final UserTeamMapper userTeamMapper;

    public UserTeamService(UserTeamRepository userTeamRepository, UserTeamMapper userTeamMapper) {
        this.userTeamRepository = userTeamRepository;
        this.userTeamMapper = userTeamMapper;
    }

    /**
     * Save a userTeam.
     *
     * @param userTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public UserTeamDTO save(UserTeamDTO userTeamDTO) {
        log.debug("Request to save UserTeam : {}", userTeamDTO);
        UserTeam userTeam = userTeamMapper.toEntity(userTeamDTO);
        userTeam = userTeamRepository.save(userTeam);
        return userTeamMapper.toDto(userTeam);
    }

    /**
     * Update a userTeam.
     *
     * @param userTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public UserTeamDTO update(UserTeamDTO userTeamDTO) {
        log.debug("Request to update UserTeam : {}", userTeamDTO);
        UserTeam userTeam = userTeamMapper.toEntity(userTeamDTO);
        userTeam = userTeamRepository.save(userTeam);
        return userTeamMapper.toDto(userTeam);
    }

    /**
     * Partially update a userTeam.
     *
     * @param userTeamDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserTeamDTO> partialUpdate(UserTeamDTO userTeamDTO) {
        log.debug("Request to partially update UserTeam : {}", userTeamDTO);

        return userTeamRepository
            .findById(userTeamDTO.getId())
            .map(existingUserTeam -> {
                userTeamMapper.partialUpdate(existingUserTeam, userTeamDTO);

                return existingUserTeam;
            })
            .map(userTeamRepository::save)
            .map(userTeamMapper::toDto);
    }

    /**
     * Get all the userTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserTeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTeams");
        return userTeamRepository.findAll(pageable).map(userTeamMapper::toDto);
    }

    /**
     * Get one userTeam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserTeamDTO> findOne(Long id) {
        log.debug("Request to get UserTeam : {}", id);
        return userTeamRepository.findById(id).map(userTeamMapper::toDto);
    }

    /**
     * Delete the userTeam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserTeam : {}", id);
        userTeamRepository.deleteById(id);
    }
}
