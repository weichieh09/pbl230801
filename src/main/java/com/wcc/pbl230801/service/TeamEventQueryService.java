package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.TeamEvent;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
import com.wcc.pbl230801.service.mapper.TeamEventMapper;
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
 * Service for executing complex queries for {@link TeamEvent} entities in the database.
 * The main input is a {@link TeamEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamEventDTO} or a {@link Page} of {@link TeamEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamEventQueryService extends QueryService<TeamEvent> {

    private final Logger log = LoggerFactory.getLogger(TeamEventQueryService.class);

    private final TeamEventRepository teamEventRepository;

    private final TeamEventMapper teamEventMapper;

    public TeamEventQueryService(TeamEventRepository teamEventRepository, TeamEventMapper teamEventMapper) {
        this.teamEventRepository = teamEventRepository;
        this.teamEventMapper = teamEventMapper;
    }

    /**
     * Return a {@link List} of {@link TeamEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamEventDTO> findByCriteria(TeamEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamEvent> specification = createSpecification(criteria);
        return teamEventMapper.toDto(teamEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamEventDTO> findByCriteria(TeamEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamEvent> specification = createSpecification(criteria);
        return teamEventRepository.findAll(specification, page).map(teamEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TeamEvent> specification = createSpecification(criteria);
        return teamEventRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TeamEvent> createSpecification(TeamEventCriteria criteria) {
        Specification<TeamEvent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TeamEvent_.id));
            }
            if (criteria.gettId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettId(), TeamEvent_.tId));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), TeamEvent_.eId));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), TeamEvent_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), TeamEvent_.lstMtnDt));
            }
        }
        return specification;
    }
}
