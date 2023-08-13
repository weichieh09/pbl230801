package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc101Service;
import com.wcc.pbl230801.pblService.dto.EventDTOC;
import com.wcc.pbl230801.pblService.dto.RtsDTOC;
import com.wcc.pbl230801.pblService.dto.TeamDTOC;
import com.wcc.pbl230801.pblService.dto.VenueDTOC;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/wcc101")
public class Wcc101Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc101Resource.class);

    private static final String ENTITY_NAME = "wcc101";

    @Autowired
    private Wcc101Service wcc101Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private EventZQueryService eventZQueryService;

    @Autowired
    private VwEventResultRepository vwEventResultRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTOC>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        List<TeamDTOC> result = wcc101Service.getTeam(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTOC>> venues(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<VenueDTOC> result = wcc101Service.getDistinctVenue(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTOC>> events(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<EventDTOC> result = wcc101Service.getEvent(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/realTimeScore")
    public ResponseEntity<List<RtsDTOC>> realTimeScore(VwEventResultCriteria criteria, Pageable pageable) {
        Long eventId = criteria.geteId().getEquals();
        Long teamId = criteria.gettId().getEquals();
        Page<Map<String, Object>> page = vwEventResultRepository.findMaxStatsByEventId(eventId, teamId, pageable);
        List<RtsDTOC> result = wcc101Service.getRealTimeScore(page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }
}
