package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.Prod;
import com.wcc.pbl230801.repository.ProdRepository;
import com.wcc.pbl230801.service.dto.ProdDTO;
import com.wcc.pbl230801.service.mapper.ProdMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Prod}.
 */
@Service
@Transactional
public class ProdService {

    private final Logger log = LoggerFactory.getLogger(ProdService.class);

    private final ProdRepository prodRepository;

    private final ProdMapper prodMapper;

    public ProdService(ProdRepository prodRepository, ProdMapper prodMapper) {
        this.prodRepository = prodRepository;
        this.prodMapper = prodMapper;
    }

    /**
     * Save a prod.
     *
     * @param prodDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdDTO save(ProdDTO prodDTO) {
        log.debug("Request to save Prod : {}", prodDTO);
        Prod prod = prodMapper.toEntity(prodDTO);
        prod = prodRepository.save(prod);
        return prodMapper.toDto(prod);
    }

    /**
     * Update a prod.
     *
     * @param prodDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdDTO update(ProdDTO prodDTO) {
        log.debug("Request to update Prod : {}", prodDTO);
        Prod prod = prodMapper.toEntity(prodDTO);
        prod = prodRepository.save(prod);
        return prodMapper.toDto(prod);
    }

    /**
     * Partially update a prod.
     *
     * @param prodDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProdDTO> partialUpdate(ProdDTO prodDTO) {
        log.debug("Request to partially update Prod : {}", prodDTO);

        return prodRepository
            .findById(prodDTO.getId())
            .map(existingProd -> {
                prodMapper.partialUpdate(existingProd, prodDTO);

                return existingProd;
            })
            .map(prodRepository::save)
            .map(prodMapper::toDto);
    }

    /**
     * Get all the prods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prods");
        return prodRepository.findAll(pageable).map(prodMapper::toDto);
    }

    /**
     * Get one prod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdDTO> findOne(Long id) {
        log.debug("Request to get Prod : {}", id);
        return prodRepository.findById(id).map(prodMapper::toDto);
    }

    /**
     * Delete the prod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prod : {}", id);
        prodRepository.deleteById(id);
    }
}
