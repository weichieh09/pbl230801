package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.TeamPlayer;
import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
import com.wcc.pbl230801.service.mapper.TeamPlayerMapper;
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
 * Service for executing complex queries for {@link TeamPlayer} entities in the database.
 * The main input is a {@link TeamPlayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamPlayerDTO} or a {@link Page} of {@link TeamPlayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamPlayerQueryService extends QueryService<TeamPlayer> {

    private final Logger log = LoggerFactory.getLogger(TeamPlayerQueryService.class);

    private final TeamPlayerRepository teamPlayerRepository;

    private final TeamPlayerMapper teamPlayerMapper;

    public TeamPlayerQueryService(TeamPlayerRepository teamPlayerRepository, TeamPlayerMapper teamPlayerMapper) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamPlayerMapper = teamPlayerMapper;
    }

    /**
     * Return a {@link List} of {@link TeamPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamPlayerDTO> findByCriteria(TeamPlayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamPlayer> specification = createSpecification(criteria);
        return teamPlayerMapper.toDto(teamPlayerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamPlayerDTO> findByCriteria(TeamPlayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamPlayer> specification = createSpecification(criteria);
        return teamPlayerRepository.findAll(specification, page).map(teamPlayerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeamPlayerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TeamPlayer> specification = createSpecification(criteria);
        return teamPlayerRepository.count(specification);
    }

    /**
     * Function to convert {@link TeamPlayerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TeamPlayer> createSpecification(TeamPlayerCriteria criteria) {
        Specification<TeamPlayer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TeamPlayer_.id));
            }
            if (criteria.gettId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettId(), TeamPlayer_.tId));
            }
            if (criteria.getpId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getpId(), TeamPlayer_.pId));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), TeamPlayer_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), TeamPlayer_.lstMtnDt));
            }
        }
        return specification;
    }
}
