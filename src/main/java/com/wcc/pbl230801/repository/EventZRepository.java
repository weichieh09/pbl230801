package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.EventZ;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventZ entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventZRepository extends JpaRepository<EventZ, Long>, JpaSpecificationExecutor<EventZ> {}
