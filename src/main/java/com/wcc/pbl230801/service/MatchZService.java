package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.MatchZ;
import com.wcc.pbl230801.repository.MatchZRepository;
import com.wcc.pbl230801.service.dto.MatchZDTO;
import com.wcc.pbl230801.service.mapper.MatchZMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MatchZ}.
 */
@Service
@Transactional
public class MatchZService {

    private final Logger log = LoggerFactory.getLogger(MatchZService.class);

    private final MatchZRepository matchZRepository;

    private final MatchZMapper matchZMapper;

    public MatchZService(MatchZRepository matchZRepository, MatchZMapper matchZMapper) {
        this.matchZRepository = matchZRepository;
        this.matchZMapper = matchZMapper;
    }

    /**
     * Save a matchZ.
     *
     * @param matchZDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchZDTO save(MatchZDTO matchZDTO) {
        log.debug("Request to save MatchZ : {}", matchZDTO);
        MatchZ matchZ = matchZMapper.toEntity(matchZDTO);
        matchZ = matchZRepository.save(matchZ);
        return matchZMapper.toDto(matchZ);
    }

    /**
     * Update a matchZ.
     *
     * @param matchZDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchZDTO update(MatchZDTO matchZDTO) {
        log.debug("Request to update MatchZ : {}", matchZDTO);
        MatchZ matchZ = matchZMapper.toEntity(matchZDTO);
        matchZ = matchZRepository.save(matchZ);
        return matchZMapper.toDto(matchZ);
    }

    /**
     * Partially update a matchZ.
     *
     * @param matchZDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MatchZDTO> partialUpdate(MatchZDTO matchZDTO) {
        log.debug("Request to partially update MatchZ : {}", matchZDTO);

        return matchZRepository
            .findById(matchZDTO.getId())
            .map(existingMatchZ -> {
                matchZMapper.partialUpdate(existingMatchZ, matchZDTO);

                return existingMatchZ;
            })
            .map(matchZRepository::save)
            .map(matchZMapper::toDto);
    }

    /**
     * Get all the matchZS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchZDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchZS");
        return matchZRepository.findAll(pageable).map(matchZMapper::toDto);
    }

    /**
     * Get one matchZ by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatchZDTO> findOne(Long id) {
        log.debug("Request to get MatchZ : {}", id);
        return matchZRepository.findById(id).map(matchZMapper::toDto);
    }

    /**
     * Delete the matchZ by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MatchZ : {}", id);
        matchZRepository.deleteById(id);
    }
}
