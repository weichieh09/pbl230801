package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.VwWcc701Result;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VwWcc701Result entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VwWcc701ResultRepository extends JpaRepository<VwWcc701Result, Long>, JpaSpecificationExecutor<VwWcc701Result> {}
