package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.VwWcc701Result;
import com.wcc.pbl230801.repository.VwWcc701ResultRepository;
import com.wcc.pbl230801.service.criteria.VwWcc701ResultCriteria;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import com.wcc.pbl230801.service.mapper.VwWcc701ResultMapper;
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
 * Service for executing complex queries for {@link VwWcc701Result} entities in the database.
 * The main input is a {@link VwWcc701ResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VwWcc701ResultDTO} or a {@link Page} of {@link VwWcc701ResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VwWcc701ResultQueryService extends QueryService<VwWcc701Result> {

    private final Logger log = LoggerFactory.getLogger(VwWcc701ResultQueryService.class);

    private final VwWcc701ResultRepository vwWcc701ResultRepository;

    private final VwWcc701ResultMapper vwWcc701ResultMapper;

    public VwWcc701ResultQueryService(VwWcc701ResultRepository vwWcc701ResultRepository, VwWcc701ResultMapper vwWcc701ResultMapper) {
        this.vwWcc701ResultRepository = vwWcc701ResultRepository;
        this.vwWcc701ResultMapper = vwWcc701ResultMapper;
    }

    /**
     * Return a {@link List} of {@link VwWcc701ResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VwWcc701ResultDTO> findByCriteria(VwWcc701ResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VwWcc701Result> specification = createSpecification(criteria);
        return vwWcc701ResultMapper.toDto(vwWcc701ResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VwWcc701ResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VwWcc701ResultDTO> findByCriteria(VwWcc701ResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VwWcc701Result> specification = createSpecification(criteria);
        return vwWcc701ResultRepository.findAll(specification, page).map(vwWcc701ResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VwWcc701ResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VwWcc701Result> specification = createSpecification(criteria);
        return vwWcc701ResultRepository.count(specification);
    }

    /**
     * Function to convert {@link VwWcc701ResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VwWcc701Result> createSpecification(VwWcc701ResultCriteria criteria) {
        Specification<VwWcc701Result> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VwWcc701Result_.id));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), VwWcc701Result_.eId));
            }
            if (criteria.getEvntNm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvntNm(), VwWcc701Result_.evntNm));
            }
            if (criteria.getEvntDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEvntDt(), VwWcc701Result_.evntDt));
            }
            if (criteria.getVenue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVenue(), VwWcc701Result_.venue));
            }
            if (criteria.getmId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getmId(), VwWcc701Result_.mId));
            }
            if (criteria.getMtchEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMtchEndTime(), VwWcc701Result_.mtchEndTime));
            }
            if (criteria.getwPlyr1Id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getwPlyr1Id(), VwWcc701Result_.wPlyr1Id));
            }
            if (criteria.getwPlyr1Lvl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr1Lvl(), VwWcc701Result_.wPlyr1Lvl));
            }
            if (criteria.getwPlyr1Nm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr1Nm(), VwWcc701Result_.wPlyr1Nm));
            }
            if (criteria.getwPlyr2Id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getwPlyr2Id(), VwWcc701Result_.wPlyr2Id));
            }
            if (criteria.getwPlyr2Lvl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr2Lvl(), VwWcc701Result_.wPlyr2Lvl));
            }
            if (criteria.getwPlyr2Nm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwPlyr2Nm(), VwWcc701Result_.wPlyr2Nm));
            }
            if (criteria.getVs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVs(), VwWcc701Result_.vs));
            }
            if (criteria.getlPlyr1Id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getlPlyr1Id(), VwWcc701Result_.lPlyr1Id));
            }
            if (criteria.getlPlyr1Lvl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr1Lvl(), VwWcc701Result_.lPlyr1Lvl));
            }
            if (criteria.getlPlyr1Nm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr1Nm(), VwWcc701Result_.lPlyr1Nm));
            }
            if (criteria.getlPlyr2Id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getlPlyr2Id(), VwWcc701Result_.lPlyr2Id));
            }
            if (criteria.getlPlyr2Lvl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr2Lvl(), VwWcc701Result_.lPlyr2Lvl));
            }
            if (criteria.getlPlyr2Nm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlPlyr2Nm(), VwWcc701Result_.lPlyr2Nm));
            }
            if (criteria.getwScr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getwScr(), VwWcc701Result_.wScr));
            }
            if (criteria.getlScr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getlScr(), VwWcc701Result_.lScr));
            }
        }
        return specification;
    }
}
