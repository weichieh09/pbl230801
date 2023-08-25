package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.UserTeam;
import com.wcc.pbl230801.repository.UserTeamRepository;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import com.wcc.pbl230801.service.mapper.UserTeamMapper;
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
 * Service for executing complex queries for {@link UserTeam} entities in the database.
 * The main input is a {@link UserTeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserTeamDTO} or a {@link Page} of {@link UserTeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserTeamQueryService extends QueryService<UserTeam> {

    private final Logger log = LoggerFactory.getLogger(UserTeamQueryService.class);

    private final UserTeamRepository userTeamRepository;

    private final UserTeamMapper userTeamMapper;

    public UserTeamQueryService(UserTeamRepository userTeamRepository, UserTeamMapper userTeamMapper) {
        this.userTeamRepository = userTeamRepository;
        this.userTeamMapper = userTeamMapper;
    }

    /**
     * Return a {@link List} of {@link UserTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserTeamDTO> findByCriteria(UserTeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserTeam> specification = createSpecification(criteria);
        return userTeamMapper.toDto(userTeamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserTeamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserTeamDTO> findByCriteria(UserTeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserTeam> specification = createSpecification(criteria);
        return userTeamRepository.findAll(specification, page).map(userTeamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserTeamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserTeam> specification = createSpecification(criteria);
        return userTeamRepository.count(specification);
    }

    /**
     * Function to convert {@link UserTeamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserTeam> createSpecification(UserTeamCriteria criteria) {
        Specification<UserTeam> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserTeam_.id));
            }
            if (criteria.getuId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getuId(), UserTeam_.uId));
            }
            if (criteria.gettId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettId(), UserTeam_.tId));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), UserTeam_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), UserTeam_.lstMtnDt));
            }
        }
        return specification;
    }
}
