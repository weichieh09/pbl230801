package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.TeamEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamEventRepository extends JpaRepository<TeamEvent, Long>, JpaSpecificationExecutor<TeamEvent> {
    void deleteBytId(Long id);

    void deleteByeId(Long id);
}
