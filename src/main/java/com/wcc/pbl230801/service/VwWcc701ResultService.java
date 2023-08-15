package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.VwWcc701Result;
import com.wcc.pbl230801.repository.VwWcc701ResultRepository;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import com.wcc.pbl230801.service.mapper.VwWcc701ResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VwWcc701Result}.
 */
@Service
@Transactional
public class VwWcc701ResultService {

    private final Logger log = LoggerFactory.getLogger(VwWcc701ResultService.class);

    private final VwWcc701ResultRepository vwWcc701ResultRepository;

    private final VwWcc701ResultMapper vwWcc701ResultMapper;

    public VwWcc701ResultService(VwWcc701ResultRepository vwWcc701ResultRepository, VwWcc701ResultMapper vwWcc701ResultMapper) {
        this.vwWcc701ResultRepository = vwWcc701ResultRepository;
        this.vwWcc701ResultMapper = vwWcc701ResultMapper;
    }

    /**
     * Save a vwWcc701Result.
     *
     * @param vwWcc701ResultDTO the entity to save.
     * @return the persisted entity.
     */
    public VwWcc701ResultDTO save(VwWcc701ResultDTO vwWcc701ResultDTO) {
        log.debug("Request to save VwWcc701Result : {}", vwWcc701ResultDTO);
        VwWcc701Result vwWcc701Result = vwWcc701ResultMapper.toEntity(vwWcc701ResultDTO);
        vwWcc701Result = vwWcc701ResultRepository.save(vwWcc701Result);
        return vwWcc701ResultMapper.toDto(vwWcc701Result);
    }

    /**
     * Update a vwWcc701Result.
     *
     * @param vwWcc701ResultDTO the entity to save.
     * @return the persisted entity.
     */
    public VwWcc701ResultDTO update(VwWcc701ResultDTO vwWcc701ResultDTO) {
        log.debug("Request to update VwWcc701Result : {}", vwWcc701ResultDTO);
        VwWcc701Result vwWcc701Result = vwWcc701ResultMapper.toEntity(vwWcc701ResultDTO);
        vwWcc701Result = vwWcc701ResultRepository.save(vwWcc701Result);
        return vwWcc701ResultMapper.toDto(vwWcc701Result);
    }

    /**
     * Partially update a vwWcc701Result.
     *
     * @param vwWcc701ResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VwWcc701ResultDTO> partialUpdate(VwWcc701ResultDTO vwWcc701ResultDTO) {
        log.debug("Request to partially update VwWcc701Result : {}", vwWcc701ResultDTO);

        return vwWcc701ResultRepository
            .findById(vwWcc701ResultDTO.getId())
            .map(existingVwWcc701Result -> {
                vwWcc701ResultMapper.partialUpdate(existingVwWcc701Result, vwWcc701ResultDTO);

                return existingVwWcc701Result;
            })
            .map(vwWcc701ResultRepository::save)
            .map(vwWcc701ResultMapper::toDto);
    }

    /**
     * Get all the vwWcc701Results.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VwWcc701ResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VwWcc701Results");
        return vwWcc701ResultRepository.findAll(pageable).map(vwWcc701ResultMapper::toDto);
    }

    /**
     * Get one vwWcc701Result by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VwWcc701ResultDTO> findOne(Long id) {
        log.debug("Request to get VwWcc701Result : {}", id);
        return vwWcc701ResultRepository.findById(id).map(vwWcc701ResultMapper::toDto);
    }

    /**
     * Delete the vwWcc701Result by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VwWcc701Result : {}", id);
        vwWcc701ResultRepository.deleteById(id);
    }
}
