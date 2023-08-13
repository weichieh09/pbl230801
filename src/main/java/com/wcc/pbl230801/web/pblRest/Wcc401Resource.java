package com.wcc.pbl230801.web.pblRest;

import com.wcc.pbl230801.pblService.Wcc401Service;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.repository.TeamRepository;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.TeamService;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.dto.TeamDTO;
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
@RequestMapping("/api/wcc401")
public class Wcc401Resource {

    private final Logger log = LoggerFactory.getLogger(Wcc401Resource.class);

    private static final String ENTITY_NAME = "wcc401";

    @Autowired
    private Wcc401Service wcc401Service;

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> getAllTeams(TeamCriteria criteria, Pageable pageable) {
        Page<TeamDTO> page = teamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/teams")
    public ResponseEntity<RespDTOC> createTeam(@RequestBody TeamDTO teamDTO) {
        try {
            if (wcc401Service.checkTeam(teamDTO)) throw new Exception("Team save failed");
            wcc401Service.addInfo(teamDTO);
            TeamDTO result = teamService.save(teamDTO);
            if (result == null) throw new Exception("Team save failed");
            return ResponseEntity.ok().body(wcc401Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc401Service.getErrorResp());
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long id) {
        Optional<TeamDTO> teamDTO = teamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamDTO);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<RespDTOC> updateTeam(@PathVariable(value = "id", required = false) final Long id, @RequestBody TeamDTO teamDTO) {
        try {
            if (teamDTO.getId() == null) throw new Exception("Team update failed");
            if (!Objects.equals(id, teamDTO.getId())) throw new Exception("Team update failed");
            if (!teamRepository.existsById(id)) throw new Exception("Team update failed");
            if (wcc401Service.checkTeam(teamDTO)) throw new Exception("Team save failed");
            wcc401Service.addInfo(teamDTO);
            TeamDTO result = teamService.update(teamDTO);
            if (result == null) throw new Exception("Team update failed");
            return ResponseEntity.ok().body(wcc401Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc401Service.getErrorResp());
        }
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<RespDTOC> deleteTeam(@PathVariable Long id) {
        try {
            teamService.delete(id);
            return ResponseEntity.ok().body(wcc401Service.getSuccessResp());
        } catch (Exception e) {
            return ResponseEntity.ok().body(wcc401Service.getErrorResp());
        }
    }
}
