package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc601Service;
import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.*;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import java.util.List;
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

@RestController
@RequestMapping("/api/wcc601")
public class Wcc601Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc601Resource.class);

    private static final String ENTITY_NAME = "wcc601";

    @Autowired
    private Wcc601Service wcc601Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private EventZService eventZService;

    @Autowired
    private EventZRepository eventZRepository;

    @Autowired
    private EventZQueryService eventZQueryService;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTOC>> teams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        List<TeamDTOC> result = wcc601Service.getTeam(page.getContent());
        return ResponseEntity.ok().body(result);
    }

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
            EventZDTO result = wcc601Service.saveEventZ(eventZDTO, reqDTOC.gettId());
            if (result == null) throw new Exception("Event save failed");
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }

    @GetMapping("/event-zs/{id}")
    public ResponseEntity<EventZRespDTOC> getEventZs(@PathVariable Long id) {
        Optional<EventZDTO> eventZDTO = eventZService.findOne(id);
        EventZRespDTOC result = wcc601Service.getEventZRespDTOC(eventZDTO.get());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/event-zs/{id}")
    public ResponseEntity<RespDTOC> updateEventZs(@PathVariable(value = "id") final Long id, @RequestBody EventZReqDTOC reqDTOC) {
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
    public ResponseEntity<RespDTOC> deleteEventZs(@PathVariable Long id) {
        try {
            wcc601Service.deleteEventZ(id);
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }
}
