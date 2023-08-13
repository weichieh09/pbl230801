package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc201Service;
import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.MatchZService;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.criteria.*;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.MatchZDTO;
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
@RequestMapping("/api/wcc201")
public class Wcc201Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc201Resource.class);

    private static final String ENTITY_NAME = "wcc201";

    @Autowired
    private Wcc201Service wcc201Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private EventZQueryService eventZQueryService;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private MatchZService matchZService;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTOC>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        List<TeamDTOC> result = wcc201Service.getTeam(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTOC>> venues(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<VenueDTOC> result = wcc201Service.getDistinctVenue(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTOC>> events(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        List<EventDTOC> result = wcc201Service.getEvent(page.getContent());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTOC>> players(TeamPlayerCriteria criteria, Pageable pageable) {
        Long teamId = criteria.gettId().getEquals();
        Page<Map<String, Object>> page = teamPlayerRepository.findPlayerByTeamId(teamId, pageable);
        List<PlayerDTOC> result = wcc201Service.getPlayer(page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/match-zs")
    public ResponseEntity<RespDTOC> matchZs(@RequestBody MatchZDTO matchZDTO) {
        try {
            if (wcc201Service.checkMatchZ(matchZDTO) == null) throw new Exception("MatchZ is not valid");
            MatchZDTO result = wcc201Service.saveMatchZ(matchZDTO);
            //            MatchZDTO result = matchZService.save(matchZDTO);
            return ResponseEntity.ok().body(wcc201Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc201Service.getErrorResp());
        }
    }
}
