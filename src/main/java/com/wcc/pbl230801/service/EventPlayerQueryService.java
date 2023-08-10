package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.EventPlayer;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import com.wcc.pbl230801.service.mapper.EventPlayerMapper;
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
 * Service for executing complex queries for {@link EventPlayer} entities in the database.
 * The main input is a {@link EventPlayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventPlayerDTO} or a {@link Page} of {@link EventPlayerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventPlayerQueryService extends QueryService<EventPlayer> {

    private final Logger log = LoggerFactory.getLogger(EventPlayerQueryService.class);

    private final EventPlayerRepository eventPlayerRepository;

    private final EventPlayerMapper eventPlayerMapper;

    public EventPlayerQueryService(EventPlayerRepository eventPlayerRepository, EventPlayerMapper eventPlayerMapper) {
        this.eventPlayerRepository = eventPlayerRepository;
        this.eventPlayerMapper = eventPlayerMapper;
    }

    /**
     * Return a {@link List} of {@link EventPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventPlayerDTO> findByCriteria(EventPlayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventPlayer> specification = createSpecification(criteria);
        return eventPlayerMapper.toDto(eventPlayerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventPlayerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventPlayerDTO> findByCriteria(EventPlayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventPlayer> specification = createSpecification(criteria);
        return eventPlayerRepository.findAll(specification, page).map(eventPlayerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventPlayerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventPlayer> specification = createSpecification(criteria);
        return eventPlayerRepository.count(specification);
    }

    /**
     * Function to convert {@link EventPlayerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventPlayer> createSpecification(EventPlayerCriteria criteria) {
        Specification<EventPlayer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventPlayer_.id));
            }
            if (criteria.geteId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geteId(), EventPlayer_.eId));
            }
            if (criteria.getpId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getpId(), EventPlayer_.pId));
            }
            if (criteria.getChkFg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChkFg(), EventPlayer_.chkFg));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), EventPlayer_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), EventPlayer_.lstMtnDt));
            }
        }
        return specification;
    }
}
