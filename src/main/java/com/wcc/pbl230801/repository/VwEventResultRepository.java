package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.VwEventResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VwEventResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VwEventResultRepository extends JpaRepository<VwEventResult, Long>, JpaSpecificationExecutor<VwEventResult> {}
