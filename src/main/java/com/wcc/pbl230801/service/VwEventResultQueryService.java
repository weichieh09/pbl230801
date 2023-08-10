package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.VwEventResult;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.dto.VwEventResultDTO;
import com.wcc.pbl230801.service.mapper.VwEventResultMapper;
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
 * Service for executing complex queries for {@link VwEventResult} entities in the database.
 * The main input is a {@link VwEventResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VwEventResultDTO} or a {@link Page} of {@link VwEventResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VwEventResultQueryService extends QueryService<VwEventResult> {

    private final Logger log = LoggerFactory.getLogger(VwEventResultQueryService.class);

    private final VwEventResultRepository vwEventResultRepository;

    private final VwEventResultMapper vwEventResultMapper;

    public VwEventResultQueryService(VwEventResultRepository vwEventResultRepository, VwEventResultMapper vwEventResultMapper) {
        this.vwEventResultRepository = vwEventResultRepository;
        this.vwEventResultMapper = vwEventResultMapper;
    }

    /**
     * Return a {@link List} of {@link VwEventResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VwEventResultDTO> findByCriteria(VwEventResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VwEventResult> specification = createSpecification(criteria);
        return vwEventResultMapper.toDto(vwEventResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VwEventResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VwEventResultDTO> findByCriteria(VwEventResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VwEventResult> specification = createSpecification(criteria);
        return vwEventResultRepository.findAll(specification, page).map(vwEventResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VwEventResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VwEventResult> specification = createSpecification(criteria);
        return vwEventResultRepository.count(specification);
    }

    /**
     * Function to convert {@link VwEventResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VwEventResult> createSpecification(VwEventResultCriteria criteria) {
        Specification<VwEventResult> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VwEventResult_.id));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), VwEventResult_.eId));
            }
            if (criteria.getpId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getpId(), VwEventResult_.pId));
            }
            if (criteria.getmId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getmId(), VwEventResult_.mId));
            }
            if (criteria.getWinFg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWinFg(), VwEventResult_.winFg));
            }
            if (criteria.getPlyrNm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlyrNm(), VwEventResult_.plyrNm));
            }
            if (criteria.getPlyrLvl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlyrLvl(), VwEventResult_.plyrLvl));
            }
            if (criteria.getMtchEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMtchEndTime(), VwEventResult_.mtchEndTime));
            }
            if (criteria.getTotMatchs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotMatchs(), VwEventResult_.totMatchs));
            }
            if (criteria.getTotWins() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotWins(), VwEventResult_.totWins));
            }
            if (criteria.getAcmlWins() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcmlWins(), VwEventResult_.acmlWins));
            }
            if (criteria.getChkFg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChkFg(), VwEventResult_.chkFg));
            }
        }
        return specification;
    }
}
