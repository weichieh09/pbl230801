package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.MatchPlayer;
import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.MatchPlayerRepository;
import com.wcc.pbl230801.service.MatchPlayerService;
import com.wcc.pbl230801.service.MatchZService;
import com.wcc.pbl230801.service.TeamEventQueryService;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.MatchZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Wcc201Service {

    private final Logger log = LoggerFactory.getLogger(Wcc201Service.class);

    @Autowired
    private MatchZService matchZService;

    @Autowired
    private MatchPlayerService matchPlayerService;

    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    @Autowired
    private TeamEventQueryService teamEventQueryService;

    public List<VenueDTOC> getDistinctVenue(List<EventZDTO> content) {
        Set<VenueDTOC> venueSet = new HashSet<>();
        for (EventZDTO eventZDTO : content) {
            VenueDTOC venueDTOC = new VenueDTOC();
            venueDTOC.setName(eventZDTO.getVenue());
            venueSet.add(venueDTOC);
        }
        return List.copyOf(venueSet);
    }

    public List<TeamDTOC> getTeam(List<TeamDTO> content) {
        List<TeamDTOC> result = new ArrayList<>();
        for (TeamDTO teamDTO : content) {
            TeamDTOC teamDTOC = new TeamDTOC();
            teamDTOC.setId(String.valueOf(teamDTO.getId()));
            teamDTOC.setName(teamDTO.getTeamNm());
            result.add(teamDTOC);
        }
        return result;
    }

    public List<EventDTOC> getEvent(List<EventZDTO> content) {
        List<EventDTOC> result = new ArrayList<>();
        for (EventZDTO eventZDTO : content) {
            EventDTOC eventDTOC = new EventDTOC();
            eventDTOC.setId(String.valueOf(eventZDTO.getId()));
            eventDTOC.setName(eventZDTO.getEvntNm());
            result.add(eventDTOC);
        }
        return result;
    }

    public List<PlayerDTOC> getPlayer(List<Map<String, Object>> content) {
        List<PlayerDTOC> result = new ArrayList<>();
        for (Map<String, Object> map : content) {
            PlayerDTOC playerDTOC = new PlayerDTOC();
            playerDTOC.setId(((BigInteger) map.get("id")).toString());
            playerDTOC.setPlyrLvl((String) map.get("plyrLvl"));
            playerDTOC.setPlyrNm((String) map.get("plyrNm"));
            result.add(playerDTOC);
        }
        return result;
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

    public MatchZDTO checkMatchZ(MatchZsReqDTOC matchZsReqDTOC) {
        MatchZDTO matchZDTO = new MatchZDTO();
        BeanUtils.copyProperties(matchZsReqDTOC, matchZDTO);
        matchZDTO.seteId(Long.parseLong(matchZsReqDTOC.geteId()));

        if (matchZDTO.geteId() == null) return null;
        if (matchZDTO.getwPlyr1() == null && matchZDTO.getwPlyr2() == null) return null;
        if (matchZDTO.getlPlyr1() == null && matchZDTO.getlPlyr2() == null) return null;
        if (matchZDTO.getwScr() == null && matchZDTO.getlScr() == null) return null;
        if (Long.parseLong(matchZDTO.getwScr()) < Long.parseLong(matchZDTO.getlScr())) return null;

        long tId = Long.parseLong(matchZsReqDTOC.gettId());
        long eId = Long.parseLong(matchZsReqDTOC.geteId());

        TeamEventCriteria criteria = new TeamEventCriteria();
        criteria.seteId(LongFilterUtils.toEqualLongFilter(eId));
        criteria.settId(LongFilterUtils.toEqualLongFilter(tId));
        List<TeamEventDTO> byCriteria = teamEventQueryService.findByCriteria(criteria);
        if (byCriteria.size() == 0) return null;

        matchZDTO.setMtchEndTime(ZonedDateTimeUtils.getTaiwanTime());
        matchZDTO.setLstMtnUsr("MGDsn");
        matchZDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        return matchZDTO;
    }

    @Transactional
    public MatchZDTO saveMatchZ(MatchZDTO matchZDTO) {
        MatchZDTO result = matchZService.save(matchZDTO);

        Long mId = result.getId();
        Long eId = result.geteId();
        String wScr = result.getwScr();
        String wPlyr1 = result.getwPlyr1();
        String wPlyr2 = result.getwPlyr2();
        String lScr = result.getlScr();
        String lPlyr1 = result.getlPlyr1();
        String lPlyr2 = result.getlPlyr2();
        ZonedDateTime mtchEndTime = result.getMtchEndTime();

        List<MatchPlayer> matchPlayerDTOList = new ArrayList<>();
        if (wPlyr1 != null) matchPlayerDTOList.add(this.getMatchPlayer(mId, Long.parseLong(wPlyr1), eId, mtchEndTime, wScr, "Y"));
        if (wPlyr2 != null) matchPlayerDTOList.add(this.getMatchPlayer(mId, Long.parseLong(wPlyr2), eId, mtchEndTime, wScr, "Y"));
        if (lPlyr1 != null) matchPlayerDTOList.add(this.getMatchPlayer(mId, Long.parseLong(lPlyr1), eId, mtchEndTime, lScr, "N"));
        if (lPlyr2 != null) matchPlayerDTOList.add(this.getMatchPlayer(mId, Long.parseLong(lPlyr2), eId, mtchEndTime, lScr, "N"));

        matchPlayerRepository.saveAll(matchPlayerDTOList);

        return result;
    }

    private MatchPlayer getMatchPlayer(Long mId, Long pId, Long eId, ZonedDateTime mtchEndTime, String scr, String winFg) {
        MatchPlayer matchPlayer = new MatchPlayer();
        matchPlayer.setmId(mId);
        matchPlayer.setpId(pId);
        matchPlayer.seteId(eId);
        matchPlayer.setMtchEndTime(mtchEndTime);
        matchPlayer.setScore(scr);
        matchPlayer.setWinFg(winFg);
        matchPlayer.setLstMtnUsr("MGDsn");
        matchPlayer.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        return matchPlayer;
    }
}
