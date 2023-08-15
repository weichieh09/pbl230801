package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc701Service;
import com.wcc.pbl230801.pblService.dto.EventDTOC;
import com.wcc.pbl230801.pblService.dto.RtsDTOC;
import com.wcc.pbl230801.pblService.dto.TeamDTOC;
import com.wcc.pbl230801.pblService.dto.VenueDTOC;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.VwWcc701ResultQueryService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.criteria.VwWcc701ResultCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wcc701")
public class Wcc701Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc701Resource.class);

    private static final String ENTITY_NAME = "wcc701";

    @Autowired
    private Wcc701Service wcc701Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private EventZQueryService eventZQueryService;

    @Autowired
    private VwEventResultRepository vwEventResultRepository;

    @Autowired
    private VwWcc701ResultQueryService vwWcc701ResultQueryService;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTOC>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        List<TeamDTOC> result = wcc701Service.getTeam(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTOC>> venues(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<VenueDTOC> result = wcc701Service.getDistinctVenue(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTOC>> events(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<EventDTOC> result = wcc701Service.getEvent(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/realTimeScore")
    public ResponseEntity<List<RtsDTOC>> realTimeScore(VwEventResultCriteria criteria) {
        Long eventId = criteria.geteId().getEquals();
        Long teamId = criteria.gettId().getEquals();
        List<Map<String, Object>> list = vwEventResultRepository.findMaxStatsByEventId(eventId, teamId);
        List<RtsDTOC> result = wcc701Service.getRealTimeScore(list);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/vw-wcc-701-results")
    public ResponseEntity<List<VwWcc701ResultDTO>> getAllVwWcc701Results(VwWcc701ResultCriteria criteria) {
        List<VwWcc701ResultDTO> result = vwWcc701ResultQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(result);
    }
}
