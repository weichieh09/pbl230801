package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.pblService.Wcc501Service;
import com.wcc.pbl230801.pblService.Wcc601Service;
import com.wcc.pbl230801.pblService.dto.PlayersReqDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.EventZQueryService;
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
    private EventZQueryService eventZQueryService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/event-zs")
    public ResponseEntity<List<EventZDTO>> getAllTeamPlayers(EventZCriteria criteria, Pageable pageable) {
        Page<EventZDTO> page = eventZQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/players")
    public ResponseEntity<RespDTOC> createPlayers(@RequestBody PlayersReqDTOC reqDTOC) {
        try {
            if (!wcc601Service.checkPlayer(reqDTOC)) throw new Exception("Player save failed");
            PlayerDTO result = wcc601Service.savePlayer(reqDTOC);
            if (result == null) throw new Exception("Player save failed");
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> getTeam(@PathVariable Long id) {
        Optional<PlayerDTO> playerDTO = playerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerDTO);
    }

    @PutMapping("/players/{tId}/{pId}")
    public ResponseEntity<RespDTOC> updateTeam(
        @PathVariable(value = "tId") final Long tId,
        @PathVariable(value = "pId") final Long pId,
        @RequestBody PlayerDTO playerDTO
    ) {
        try {
            if (playerDTO.getId() == null) throw new Exception("Player update failed");
            if (!Objects.equals(pId, playerDTO.getId())) throw new Exception("Player update failed");
            if (!playerRepository.existsById(pId)) throw new Exception("Player update failed");
            if (!wcc601Service.checkPlayer(playerDTO, tId)) throw new Exception("Player save failed");
            wcc601Service.addInfo(playerDTO);
            PlayerDTO result = playerService.update(playerDTO);
            if (result == null) throw new Exception("Player update failed");
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<RespDTOC> deleteTeam(@PathVariable Long id) {
        try {
            playerService.delete(id);
            return ResponseEntity.ok().body(wcc601Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc601Service.getErrorResp());
        }
    }
}
