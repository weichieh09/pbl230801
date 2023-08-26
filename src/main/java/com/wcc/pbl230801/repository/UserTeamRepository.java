package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.UserTeam;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long>, JpaSpecificationExecutor<UserTeam> {
    void deleteByuId(Long id);

    List<UserTeam> findByuIdIn(List<Long> uId);
}
