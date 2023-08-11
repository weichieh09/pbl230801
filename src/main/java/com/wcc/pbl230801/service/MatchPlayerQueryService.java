package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.MatchPlayer;
import com.wcc.pbl230801.repository.MatchPlayerRepository;
import com.wcc.pbl230801.service.criteria.MatchPlayerCriteria;
import com.wcc.pbl230801.service.dto.MatchPlayerDTO;
import com.wcc.pbl230801.service.mapper.MatchPlayerMapper;
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
 * Service for executing complex queries for {@link MatchPlayer} entities in the database.
 * The main input is a {@link MatchPlayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchPlayerDTO} or a {@link Page} of {@link MatchPlayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchPlayerQueryService extends QueryService<MatchPlayer> {

    private final Logger log = LoggerFactory.getLogger(MatchPlayerQueryService.class);

    private final MatchPlayerRepository matchPlayerRepository;

    private final MatchPlayerMapper matchPlayerMapper;

    public MatchPlayerQueryService(MatchPlayerRepository matchPlayerRepository, MatchPlayerMapper matchPlayerMapper) {
        this.matchPlayerRepository = matchPlayerRepository;
        this.matchPlayerMapper = matchPlayerMapper;
    }

    /**
     * Return a {@link List} of {@link MatchPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchPlayerDTO> findByCriteria(MatchPlayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MatchPlayer> specification = createSpecification(criteria);
        return matchPlayerMapper.toDto(matchPlayerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchPlayerDTO> findByCriteria(MatchPlayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MatchPlayer> specification = createSpecification(criteria);
        return matchPlayerRepository.findAll(specification, page).map(matchPlayerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MatchPlayerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MatchPlayer> specification = createSpecification(criteria);
        return matchPlayerRepository.count(specification);
    }

    /**
     * Function to convert {@link MatchPlayerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MatchPlayer> createSpecification(MatchPlayerCriteria criteria) {
        Specification<MatchPlayer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MatchPlayer_.id));
            }
            if (criteria.getmId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getmId(), MatchPlayer_.mId));
            }
            if (criteria.getpId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getpId(), MatchPlayer_.pId));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), MatchPlayer_.eId));
            }
            if (criteria.getMtchEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMtchEndTime(), MatchPlayer_.mtchEndTime));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScore(), MatchPlayer_.score));
            }
            if (criteria.getWinFg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWinFg(), MatchPlayer_.winFg));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), MatchPlayer_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), MatchPlayer_.lstMtnDt));
            }
        }
        return specification;
    }
}
