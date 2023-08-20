package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.EventPlayer;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long>, JpaSpecificationExecutor<EventPlayer> {
    List<EventPlayer> findAllBypIdIn(List<Long> pIdList);

    void deleteBypId(Long id);

    void deleteByeId(Long id);
}
