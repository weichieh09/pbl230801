package com.wcc.pbl230801.repository;

import com.wcc.pbl230801.domain.VwEventResult;
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
        "GROUP  BY p_id,\n" +
        "          plyr_nm " +
        "",
        countQuery = "" +
        "SELECT Count(*) AS total_count\n" +
        "FROM   (" +
        "        SELECT Max(plyr_lvl)      AS plyr_lvl,\n" +
        "               p_id,\n" +
        "               plyr_nm,\n" +
        "               Max(tot_wins)      AS tot_wins,\n" +
        "               Max(mtch_end_time) AS mtch_end_time\n" +
        "        FROM   vw_event_result\n" +
        "        WHERE  e_id = :eventId\n" +
        "        GROUP  BY p_id,\n" +
        "                  plyr_nm\n" +
        ")AS total_count " +
        "",
        nativeQuery = true
    )
    Page<Map<String, Object>> findMaxStatsByEventId(@Param("eventId") Long eventId, Pageable pageable);
}
