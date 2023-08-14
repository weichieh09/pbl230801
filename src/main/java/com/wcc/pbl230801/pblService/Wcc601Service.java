package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.pblService.dto.PlayersReqDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.StringFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.PlayerQueryService;
import com.wcc.pbl230801.service.PlayerService;
import com.wcc.pbl230801.service.TeamPlayerQueryService;
import com.wcc.pbl230801.service.TeamPlayerService;
import com.wcc.pbl230801.service.criteria.PlayerCriteria;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.PlayerDTO;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Wcc601Service {

    private final Logger log = LoggerFactory.getLogger(Wcc601Service.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerQueryService playerQueryService;

    @Autowired
    private TeamPlayerQueryService teamPlayerQueryService;

    @Autowired
    private TeamPlayerService teamPlayerService;

    @Autowired
    private PlayerService playerService;

    public List<Player> getPlyrs(List<TeamPlayerDTO> content) {
        List<Long> pIds = content.stream().map(TeamPlayerDTO::getpId).collect(Collectors.toList());
        List<Player> allById = playerRepository.findAllById(pIds);
        return allById;
    }

    public RespDTOC getSuccessResp() {
        RespDTOC respDTOC = new RespDTOC();
        respDTOC.setStatus("0");
        respDTOC.setMessage("success");
        return respDTOC;
    }

    public RespDTOC getErrorResp() {
        RespDTOC respDTOC = new RespDTOC();
        respDTOC.setStatus("1");
        respDTOC.setMessage("fail");
        return respDTOC;
    }

    public boolean checkPlayer(PlayersReqDTOC reqDTOC) {
        if (reqDTOC.gettId().isBlank()) return false;

        PlayerCriteria playerCriteria = new PlayerCriteria();
        playerCriteria.setPlyrNm(StringFilterUtils.toEqualStringFilter(reqDTOC.getPlyrNm()));
        List<PlayerDTO> playerDTOList = playerQueryService.findByCriteria(playerCriteria);

        List<Long> list = playerDTOList.stream().map(PlayerDTO::getId).collect(Collectors.toList());

        TeamPlayerCriteria teamPlayerCriteria = new TeamPlayerCriteria();
        teamPlayerCriteria.settId(LongFilterUtils.toEqualLongFilter(Long.parseLong(reqDTOC.gettId())));
        teamPlayerCriteria.setpId(LongFilterUtils.toInLongFilter(list));
        List<TeamPlayerDTO> teamPlayerDTOList = teamPlayerQueryService.findByCriteria(teamPlayerCriteria);

        return teamPlayerDTOList.isEmpty();
    }

    public boolean checkPlayer(PlayerDTO playerDTO, Long tId) {
        PlayersReqDTOC reqDTOC = new PlayersReqDTOC();
        reqDTOC.setpId(playerDTO.getId().toString());
        reqDTOC.settId(tId.toString());
        reqDTOC.setPlyrLvl(playerDTO.getPlyrLvl());
        reqDTOC.setPlyrNm(playerDTO.getPlyrNm());
        return this.checkPlayer(reqDTOC);
    }

    @Transactional
    public PlayerDTO savePlayer(PlayersReqDTOC reqDTOC) {
        // player
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlyrNm(reqDTOC.getPlyrNm());
        playerDTO.setPlyrLvl(reqDTOC.getPlyrLvl());
        playerDTO.setLstMtnUsr("MGDsn");
        playerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        PlayerDTO reulst = playerService.save(playerDTO);
        // team player
        TeamPlayerDTO teamPlayerDTO = new TeamPlayerDTO();
        teamPlayerDTO.settId(Long.parseLong(reqDTOC.gettId()));
        teamPlayerDTO.setpId(reulst.getId());
        teamPlayerDTO.setLstMtnUsr("MGDsn");
        teamPlayerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        teamPlayerService.save(teamPlayerDTO);
        return reulst;
    }

    public void addInfo(PlayerDTO playerDTO) {
        playerDTO.setLstMtnUsr("MGDsn");
        playerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
    }
}
