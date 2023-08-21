package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.MatchPlayer;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatchPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long>, JpaSpecificationExecutor<MatchPlayer> {
    void deleteBypId(Long id);

    @Query(
        value = "" +
        "SELECT id,\n" +
        "       m_id,\n" +
        "       p_id,\n" +
        "       e_id,\n" +
        "       mtch_end_time,\n" +
        "       score,\n" +
        "       win_fg,\n" +
        "       lst_mtn_usr,\n" +
        "       lst_mtn_dt\n" +
        "FROM   match_player\n" +
        "WHERE  e_id = :eId\n" +
        "       AND p_id = :pId\n" +
        "ORDER  BY mtch_end_time DESC\n" +
        "LIMIT  1; " +
        "",
        nativeQuery = true
    )
    List<MatchPlayer> findMaxByeIdAndpId(@Param("eId") long eId, @Param("pId") long pId);
}
