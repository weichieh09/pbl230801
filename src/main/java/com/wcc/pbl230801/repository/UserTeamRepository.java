package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.UserTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long>, JpaSpecificationExecutor<UserTeam> {}
