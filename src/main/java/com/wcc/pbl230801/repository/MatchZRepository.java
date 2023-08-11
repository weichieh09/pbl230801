package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.MatchZ;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchZ entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchZRepository extends JpaRepository<MatchZ, Long>, JpaSpecificationExecutor<MatchZ> {}
