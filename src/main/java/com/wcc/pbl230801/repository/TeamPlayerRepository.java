package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.TeamPlayer;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamPlayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long>, JpaSpecificationExecutor<TeamPlayer> {
    @Query(
        value = "" +
        "SELECT p.id       AS id,\n" +
        "       p.plyr_lvl AS plyrLvl,\n" +
        "       p.plyr_nm  AS plyrNm\n" +
        "FROM   pbl230801.team_player AS tp\n" +
        "       INNER JOIN pbl230801.player AS p\n" +
        "               ON tp.p_id = p.id\n" +
        "WHERE  tp.t_id = :teamId " +
        "",
        countQuery = "" +
        "SELECT Count(*) AS total_count\n" +
        "FROM   (" +
        "       SELECT p.id       AS id,\n" +
        "              p.plyr_lvl AS plyrLvl,\n" +
        "              p.plyr_nm  AS plyrNm\n" +
        "       FROM   pbl230801.team_player AS tp\n" +
        "              INNER JOIN pbl230801.player AS p\n" +
        "                      ON tp.p_id = p.id\n" +
        "WHERE  tp.t_id = :teamId\n" +
        ")AS total_count " +
        "",
        nativeQuery = true
    )
    Page<Map<String, Object>> findPlayerByTeamId(@Param("teamId") Long teamId, Pageable pageable);
}
