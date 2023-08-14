package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.pblService.Wcc501Service;
import com.wcc.pbl230801.pblService.Wcc601Service;
import com.wcc.pbl230801.pblService.dto.EventZReqDTOC;
import com.wcc.pbl230801.pblService.dto.PlayersReqDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.EventZQueryService;
import com.wcc.pbl230801.service.EventZService;
import com.wcc.pbl230801.service.PlayerService;
import com.wcc.pbl230801.service.TeamPlayerQueryService;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.PlayerDTO;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/wcc601")
public class Wcc601Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc601Resource.class);

    private static final String ENTITY_NAME = "wcc601";

    @Autowired
    private Wcc601Service wcc601Service;

    @Autowired
    private EventZService eventZService;

    @Autowired
    private EventZRepository eventZRepository;

    @Autowired
    private EventZQueryService eventZQueryService;

    @GetMapping("/event-zs")
    public ResponseEntity<List<EventZDTO>> getAllEventZs(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/event-zs")
    public ResponseEntity<RespDTOC> createEventZs(@RequestBody EventZReqDTOC reqDTOC) {
        try {
            EventZDTO eventZDTO = wcc601Service.getEventZDTO(reqDTOC);
            EventZDTO result = eventZService.save(eventZDTO);
            if (result == null) throw new Exception("Event save failed");
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }

    @GetMapping("/event-zs/{id}")
    public ResponseEntity<EventZDTO> getEventZs(@PathVariable Long id) {
        Optional<EventZDTO> eventZDTO = eventZService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventZDTO);
    }

    @PutMapping("/event-zs/{id}")
    public ResponseEntity<RespDTOC> updateTeam(@PathVariable(value = "id") final Long id, @RequestBody EventZReqDTOC reqDTOC) {
        try {
            if (reqDTOC.geteId() == null) throw new Exception("Event update failed");
            if (!reqDTOC.geteId().equals(String.valueOf(id))) throw new Exception("Event update failed");
            if (!eventZRepository.existsById(id)) throw new Exception("Event update failed");
            EventZDTO result = eventZService.update(wcc601Service.getEventZDTO(reqDTOC));
            if (result == null) throw new Exception("Event update failed");
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }

    @DeleteMapping("/event-zs/{id}")
    public ResponseEntity<RespDTOC> deleteTeam(@PathVariable Long id) {
        try {
            eventZService.delete(id);
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }
}
