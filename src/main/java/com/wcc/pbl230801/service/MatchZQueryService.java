package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.MatchZ;
import com.wcc.pbl230801.repository.MatchZRepository;
import com.wcc.pbl230801.service.criteria.MatchZCriteria;
import com.wcc.pbl230801.service.dto.MatchZDTO;
import com.wcc.pbl230801.service.mapper.MatchZMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MatchZ} entities in the database.
 * The main input is a {@link MatchZCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchZDTO} or a {@link Page} of {@link MatchZDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchZQueryService extends QueryService<MatchZ> {

    private final Logger log = LoggerFactory.getLogger(MatchZQueryService.class);

    private final MatchZRepository matchZRepository;

    private final MatchZMapper matchZMapper;

    public MatchZQueryService(MatchZRepository matchZRepository, MatchZMapper matchZMapper) {
        this.matchZRepository = matchZRepository;
        this.matchZMapper = matchZMapper;
    }

    /**
     * Return a {@link List} of {@link MatchZDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchZDTO> findByCriteria(MatchZCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MatchZ> specification = createSpecification(criteria);
        return matchZMapper.toDto(matchZRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchZDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchZDTO> findByCriteria(MatchZCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MatchZ> specification = createSpecification(criteria);
        return matchZRepository.findAll(specification, page).map(matchZMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MatchZCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MatchZ> specification = createSpecification(criteria);
        return matchZRepository.count(specification);
    }

    /**
     * Function to convert {@link MatchZCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MatchZ> createSpecification(MatchZCriteria criteria) {
        Specification<MatchZ> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MatchZ_.id));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), MatchZ_.eId));
            }
            if (criteria.getMtchEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMtchEndTime(), MatchZ_.mtchEndTime));
            }
            if (criteria.getwPlyr1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr1(), MatchZ_.wPlyr1));
            }
            if (criteria.getwPlyr2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr2(), MatchZ_.wPlyr2));
            }
            if (criteria.getwScr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwScr(), MatchZ_.wScr));
            }
            if (criteria.getlPlyr1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr1(), MatchZ_.lPlyr1));
            }
            if (criteria.getlPlyr2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr2(), MatchZ_.lPlyr2));
            }
            if (criteria.getlScr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlScr(), MatchZ_.lScr));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), MatchZ_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), MatchZ_.lstMtnDt));
            }
        }
        return specification;
    }
}
