package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.VwEventResult;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VwEventResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VwEventResultRepository extends JpaRepository<VwEventResult, Long>, JpaSpecificationExecutor<VwEventResult> {
    @Query(
        value = "" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)      AS tot_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm " +
        "",
        countQuery = "" +
        "SELECT Count(*) AS total_count\n" +
        "FROM   (" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)      AS tot_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm\n" +
        ")AS total_count " +
        "",
        nativeQuery = true
    )
    Page<Map<String, Object>> findMaxStatsByEventId(@Param("eventId") Long eventId, @Param("teamId") Long teamId, Pageable pageable);

    @Query(
        value = "" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)     AS tot_wins,\n" +
        "       Max(acml_wins)     AS acml_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "       AND acml_wins = :acmlWins\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm " +
        "",
        countQuery = "" +
        "SELECT Count(*) AS total_count\n" +
        "FROM   (" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)      AS tot_wins,\n" +
        "       Max(acml_wins)      AS acml_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "       AND acml_wins = :acmlWins\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm\n" +
        ")AS total_count " +
        "",
        nativeQuery = true
    )
    Page<Map<String, Object>> findMaxStatsByEventIdNine(
        @Param("eventId") Long eventId,
        @Param("teamId") Long teamId,
        @Param("acmlWins") String acmlWins,
        Pageable pageable
    );

    @Query(
        value = "" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)      AS tot_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm\n" +
        "ORDER BY tot_wins DESC " +
        "",
        nativeQuery = true
    )
    List<Map<String, Object>> findMaxStatsByEventId(@Param("eventId") Long eventId, @Param("teamId") Long teamId);

    @Query(
        value = "" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)     AS tot_wins,\n" +
        "       Max(acml_wins)     AS acml_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "       AND acml_wins > 9\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm " +
        "",
        countQuery = "" +
        "SELECT Count(*) AS total_count\n" +
        "FROM   (" +
        "SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "       p_id,\n" +
        "       plyr_nm,\n" +
        "       Max(tot_wins)      AS tot_wins,\n" +
        "       Max(acml_wins)      AS acml_wins,\n" +
        "       Max(mtch_end_time) AS mtch_end_time\n" +
        "FROM   vw_event_result\n" +
        "WHERE  e_id = :eventId\n" +
        "       AND t_id = :teamId\n" +
        "       AND acml_wins > 9\n" +
        "GROUP  BY p_id,\n" +
        "          plyr_nm\n" +
        ")AS total_count " +
        "",
        nativeQuery = true
    )
    Page<Map<String, Object>> findMaxStatsByEventIdTen(@Param("eventId") Long eventId, @Param("teamId") Long teamId, Pageable pageable);
}
