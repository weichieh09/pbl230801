package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc901Service;
import com.wcc.pbl230801.pblService.dto.PlayerDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.service.EventPlayerQueryService;
import com.wcc.pbl230801.service.EventPlayerService;
import com.wcc.pbl230801.service.TeamEventQueryService;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
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
@RequestMapping("/api/wcc901")
public class Wcc901Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc901Resource.class);

    private static final String ENTITY_NAME = "wcc901";

    @Autowired
    private Wcc901Service wcc901Service;

    @Autowired
    private EventPlayerQueryService eventPlayerQueryService;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private TeamEventQueryService teamEventQueryService;

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTOC>> players(TeamEventCriteria criteria, Pageable pageable) {
        List<TeamEventDTO> teamEventDTOList = teamEventQueryService.findByCriteria(criteria);
        if (teamEventDTOList.size() != 1) return ResponseEntity.badRequest().build();
        Long teamId = teamEventDTOList.get(0).gettId();
        Page<Map<String, Object>> page = teamPlayerRepository.findPlayerByTeamId(teamId, pageable);
        List<PlayerDTOC> result = wcc901Service.getPlayer(page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/joinEvent")
    public ResponseEntity<RespDTOC> joinEvent(@RequestBody Map<String, String> reqMap) {
        try {
            EventPlayerDTO result = wcc901Service.setJoinEvent(reqMap);
            if (result == null) throw new Exception("EventPlayer update failed");
            return ResponseEntity.ok().body(wcc901Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc901Service.getErrorResp());
        }
    }

    @GetMapping("/countJoined")
    public ResponseEntity<Long> countJoined(EventPlayerCriteria criteria) {
        return ResponseEntity.ok().body(eventPlayerQueryService.countByCriteria(criteria));
    }
}
