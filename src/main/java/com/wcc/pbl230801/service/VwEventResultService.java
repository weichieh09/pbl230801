package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.VwEventResult;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.dto.VwEventResultDTO;
import com.wcc.pbl230801.service.mapper.VwEventResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VwEventResult}.
 */
@Service
@Transactional
public class VwEventResultService {

    private final Logger log = LoggerFactory.getLogger(VwEventResultService.class);

    private final VwEventResultRepository vwEventResultRepository;

    private final VwEventResultMapper vwEventResultMapper;

    public VwEventResultService(VwEventResultRepository vwEventResultRepository, VwEventResultMapper vwEventResultMapper) {
        this.vwEventResultRepository = vwEventResultRepository;
        this.vwEventResultMapper = vwEventResultMapper;
    }

    /**
     * Save a vwEventResult.
     *
     * @param vwEventResultDTO the entity to save.
     * @return the persisted entity.
     */
    public VwEventResultDTO save(VwEventResultDTO vwEventResultDTO) {
        log.debug("Request to save VwEventResult : {}", vwEventResultDTO);
        VwEventResult vwEventResult = vwEventResultMapper.toEntity(vwEventResultDTO);
        vwEventResult = vwEventResultRepository.save(vwEventResult);
        return vwEventResultMapper.toDto(vwEventResult);
    }

    /**
     * Update a vwEventResult.
     *
     * @param vwEventResultDTO the entity to save.
     * @return the persisted entity.
     */
    public VwEventResultDTO update(VwEventResultDTO vwEventResultDTO) {
        log.debug("Request to update VwEventResult : {}", vwEventResultDTO);
        VwEventResult vwEventResult = vwEventResultMapper.toEntity(vwEventResultDTO);
        vwEventResult = vwEventResultRepository.save(vwEventResult);
        return vwEventResultMapper.toDto(vwEventResult);
    }

    /**
     * Partially update a vwEventResult.
     *
     * @param vwEventResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VwEventResultDTO> partialUpdate(VwEventResultDTO vwEventResultDTO) {
        log.debug("Request to partially update VwEventResult : {}", vwEventResultDTO);

        return vwEventResultRepository
            .findById(vwEventResultDTO.getId())
            .map(existingVwEventResult -> {
                vwEventResultMapper.partialUpdate(existingVwEventResult, vwEventResultDTO);

                return existingVwEventResult;
            })
            .map(vwEventResultRepository::save)
            .map(vwEventResultMapper::toDto);
    }

    /**
     * Get all the vwEventResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VwEventResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VwEventResults");
        return vwEventResultRepository.findAll(pageable).map(vwEventResultMapper::toDto);
    }

    /**
     * Get one vwEventResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VwEventResultDTO> findOne(Long id) {
        log.debug("Request to get VwEventResult : {}", id);
        return vwEventResultRepository.findById(id).map(vwEventResultMapper::toDto);
    }

    /**
     * Delete the vwEventResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VwEventResult : {}", id);
        vwEventResultRepository.deleteById(id);
    }
}
