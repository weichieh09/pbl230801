package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.MatchPlayer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long>, JpaSpecificationExecutor<MatchPlayer> {
    void deleteBypId(Long id);
}
