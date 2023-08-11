package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.TeamPlayer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long>, JpaSpecificationExecutor<TeamPlayer> {}
