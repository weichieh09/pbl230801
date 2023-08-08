package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.Prod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdRepository extends JpaRepository<Prod, Long>, JpaSpecificationExecutor<Prod> {}
