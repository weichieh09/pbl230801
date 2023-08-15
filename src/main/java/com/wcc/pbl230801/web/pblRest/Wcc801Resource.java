package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc801Service;
import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
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
@RequestMapping("/api/wcc801")
public class Wcc801Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc801Resource.class);

    private static final String ENTITY_NAME = "wcc801";

    @Autowired
    private Wcc801Service wcc801Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private EventZQueryService eventZQueryService;

    @Autowired
    private VwEventResultRepository vwEventResultRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTOC>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        List<TeamDTOC> result = wcc801Service.getTeam(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTOC>> venues(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<VenueDTOC> result = wcc801Service.getDistinctVenue(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTOC>> events(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<EventDTOC> result = wcc801Service.getEvent(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/realTimeScore")
    public ResponseEntity<List<RtsDTOC>> realTimeScore(VwEventResultCriteria criteria, Pageable pageable) {
        Long eventId = criteria.geteId().getEquals();
        Long teamId = criteria.gettId().getEquals();
        Page<Map<String, Object>> page = vwEventResultRepository.findMaxStatsByEventId(eventId, teamId, pageable);
        List<String> chkFgList = wcc801Service.getChkFgList(eventId);
        List<RtsDTOC> result = wcc801Service.getRealTimeScore(page.getContent(), chkFgList);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/checkFlag")
    public ResponseEntity<RespDTOC> checkFlag(@RequestBody Map<String, String> reqMap) {
        try {
            log.info("checkFlag reqMap: {}", reqMap);
            EventPlayerDTO result = wcc801Service.setChkFg(reqMap);
            if (result == null) throw new Exception("EventPlayer save failed");
            return ResponseEntity.ok().body(wcc801Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc801Service.getErrorResp());
        }
    }
}
