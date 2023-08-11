package com.wcc.pbl230801.service;

import com.wcc.pbl230801.domain.*; // for static metamodels
import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.mapper.EventZMapper;
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
 * Service for executing complex queries for {@link EventZ} entities in the database.
 * The main input is a {@link EventZCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EventZDTO} or a {@link Page} of {@link EventZDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventZQueryService extends QueryService<EventZ> {

    private final Logger log = LoggerFactory.getLogger(EventZQueryService.class);

    private final EventZRepository eventZRepository;

    private final EventZMapper eventZMapper;

    public EventZQueryService(EventZRepository eventZRepository, EventZMapper eventZMapper) {
        this.eventZRepository = eventZRepository;
        this.eventZMapper = eventZMapper;
    }

    /**
     * Return a {@link List} of {@link EventZDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EventZDTO> findByCriteria(EventZCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EventZ> specification = createSpecification(criteria);
        return eventZMapper.toDto(eventZRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EventZDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventZDTO> findByCriteria(EventZCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventZ> specification = createSpecification(criteria);
        return eventZRepository.findAll(specification, page).map(eventZMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventZCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EventZ> specification = createSpecification(criteria);
        return eventZRepository.count(specification);
    }

    /**
     * Function to convert {@link EventZCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventZ> createSpecification(EventZCriteria criteria) {
        Specification<EventZ> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EventZ_.id));
            }
            if (criteria.getEvntNm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEvntNm(), EventZ_.evntNm));
            }
            if (criteria.getEvntDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEvntDt(), EventZ_.evntDt));
            }
            if (criteria.getVenue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVenue(), EventZ_.venue));
            }
            if (criteria.getEventBegTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventBegTime(), EventZ_.eventBegTime));
            }
            if (criteria.getEventEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventEndTime(), EventZ_.eventEndTime));
            }
            if (criteria.getLstMtnUsr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLstMtnUsr(), EventZ_.lstMtnUsr));
            }
            if (criteria.getLstMtnDt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLstMtnDt(), EventZ_.lstMtnDt));
            }
        }
        return specification;
    }
}
