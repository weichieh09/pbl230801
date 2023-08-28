package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.pblService.Wcc501Service;
import com.wcc.pbl230801.pblService.dto.PlayersReqDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.*;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
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
@RequestMapping("/api/wcc501")
public class Wcc501Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc501Resource.class);

    private static final String ENTITY_NAME = "wcc501";

    @Autowired
    private Wcc501Service wcc501Service;

    @Autowired
    private TeamPlayerQueryService teamPlayerQueryService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/team-players")
    public ResponseEntity<List<Player>> getAllTeamPlayers(TeamPlayerCriteria criteria, Pageable pageable) {
        Page<TeamPlayerDTO> page = teamPlayerQueryService.findByCriteria(criteria, pageable);
        List<Player> result = wcc501Service.getPlyrs(page.getContent());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/players")
    public ResponseEntity<RespDTOC> createPlayers(@RequestBody PlayersReqDTOC reqDTOC) {
        try {
            if (!wcc501Service.isRoleAdmin() && !wcc501Service.hasRole(Long.parseLong(reqDTOC.gettId()))) throw new Exception(
                "Player save failed"
            );
            if (!wcc501Service.checkPlayer(reqDTOC)) throw new Exception("Player save failed");
            PlayerDTO result = wcc501Service.savePlayer(reqDTOC);
            if (result == null) throw new Exception("Player save failed");
            return ResponseEntity.ok().body(wcc501Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc501Service.getErrorResp());
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDTO> getPlayers(@PathVariable Long id) {
        Optional<PlayerDTO> playerDTO = playerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerDTO);
    }

    @PutMapping("/players/{tId}/{pId}")
    public ResponseEntity<RespDTOC> updatePlayers(
        @PathVariable(value = "tId") final Long tId,
        @PathVariable(value = "pId") final Long pId,
        @RequestBody PlayerDTO playerDTO
    ) {
        try {
            if (!wcc501Service.isRoleAdmin() && !wcc501Service.hasRole(tId)) throw new Exception("Player update failed");
            if (playerDTO.getId() == null) throw new Exception("Player update failed");
            if (!Objects.equals(pId, playerDTO.getId())) throw new Exception("Player update failed");
            if (!playerRepository.existsById(pId)) throw new Exception("Player update failed");
            if (!wcc501Service.checkPlayer(playerDTO, tId)) throw new Exception("Player save failed");
            wcc501Service.addInfo(playerDTO);
            PlayerDTO result = playerService.update(playerDTO);
            if (result == null) throw new Exception("Player update failed");
            return ResponseEntity.ok().body(wcc501Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc501Service.getErrorResp());
        }
    }

    @DeleteMapping("/players/{tId}/{pId}")
    public ResponseEntity<RespDTOC> deletePlayers(
        @PathVariable(value = "tId") final Long tId,
        @PathVariable(value = "pId") final Long pId
    ) {
        try {
            if (!wcc501Service.isRoleAdmin() && !wcc501Service.hasRole(tId)) throw new Exception("Player update failed");
            wcc501Service.deletePlayer(pId);
            return ResponseEntity.ok().body(wcc501Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc501Service.getErrorResp());
        }
    }
}
