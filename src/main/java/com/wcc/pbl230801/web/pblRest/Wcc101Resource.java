package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.pblService.Wcc101Service;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.dto.TeamDTO;
import java.util.List;
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
    private EventZRepository eventZRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/venues")
    public ResponseEntity<List<EventZ>> venues(EventZCriteria criteria, Pageable pageable) {
        List<EventZ> distinctVenue = eventZRepository.findDistinctVenue();
        return ResponseEntity.ok().body(distinctVenue);
    }
}
