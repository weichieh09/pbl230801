package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.StringFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.service.EventPlayerQueryService;
import com.wcc.pbl230801.service.EventPlayerService;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Wcc801Service {

    private final Logger log = LoggerFactory.getLogger(Wcc801Service.class);

    @Autowired
    private EventPlayerQueryService eventPlayerQueryService;

    @Autowired
    private EventPlayerService eventPlayerService;

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

    public List<RtsDTOC> getRealTimeScoreTOT(List<Map<String, Object>> content, List<String> chkFgList) {
        List<RtsDTOC> result = new ArrayList<>();
        for (Map<String, Object> map : content) {
            RtsDTOC rtsDTOC = new RtsDTOC();
            rtsDTOC.setPlyrNm((String) map.get("plyr_nm"));
            rtsDTOC.setTotWins(((BigDecimal) map.get("tot_wins")).toString());
            rtsDTOC.setPlyrLvl(map.get("plyr_lvl").toString());
            rtsDTOC.setpId(((BigInteger) map.get("p_id")).toString());
            rtsDTOC.setMtchEndTime(this.getSimpleTime(((Timestamp) map.get("mtch_end_time")).toString()));
            result.add(rtsDTOC);
            if (chkFgList.contains(rtsDTOC.getpId())) rtsDTOC.setChkFg("Y"); else rtsDTOC.setChkFg("N");
        }
        return result;
    }

    private String getSimpleTime(String inputTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(inputTime.substring(0, 19), inputFormatter);
        LocalDateTime newDateTime = dateTime.plusHours(8);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String result = newDateTime.format(outputFormatter);
        return result;
    }

    public List<String> getChkFgList(Long eventId) {
        EventPlayerCriteria eventPlayerCriteria = new EventPlayerCriteria();
        eventPlayerCriteria.seteId(LongFilterUtils.toEqualLongFilter(eventId));
        eventPlayerCriteria.setChkFg(StringFilterUtils.toEqualStringFilter("Y"));
        List<EventPlayerDTO> eventPlayerDTOList = eventPlayerQueryService.findByCriteria(eventPlayerCriteria);
        List<String> chkFgList = new ArrayList<>();
        for (EventPlayerDTO eventPlayerDTO : eventPlayerDTOList) chkFgList.add(eventPlayerDTO.getpId().toString());
        return chkFgList;
    }

    public EventPlayerDTO setChkFg(Map<String, String> reqMap) {
        EventPlayerCriteria eventPlayerCriteria = new EventPlayerCriteria();
        eventPlayerCriteria.seteId(LongFilterUtils.toEqualLongFilter(Long.valueOf(reqMap.get("eId"))));
        eventPlayerCriteria.setpId(LongFilterUtils.toEqualLongFilter(Long.valueOf(reqMap.get("pId"))));
        List<EventPlayerDTO> byCriteria = eventPlayerQueryService.findByCriteria(eventPlayerCriteria);
        EventPlayerDTO result;
        if (byCriteria.size() == 0) {
            EventPlayerDTO eventPlayerDTO = new EventPlayerDTO();
            eventPlayerDTO.seteId(Long.valueOf(reqMap.get("eId")));
            eventPlayerDTO.setpId(Long.valueOf(reqMap.get("pId")));
            eventPlayerDTO.setChkFg(reqMap.get("chkFg"));
            eventPlayerDTO.setLstMtnUsr("MGDsn");
            eventPlayerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
            result = eventPlayerService.save(eventPlayerDTO);
        } else {
            EventPlayerDTO eventPlayerDTO = byCriteria.get(0);
            eventPlayerDTO.setChkFg(reqMap.get("chkFg"));
            eventPlayerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
            eventPlayerDTO.setLstMtnUsr("MGDsn");
            result = eventPlayerService.update(eventPlayerDTO);
        }
        return result;
    }

    public List<RtsDTOC> getRealTimeScoreACML(List<Map<String, Object>> content, List<String> chkFgList) {
        List<RtsDTOC> result = new ArrayList<>();
        for (Map<String, Object> map : content) {
            RtsDTOC rtsDTOC = new RtsDTOC();
            rtsDTOC.setPlyrNm((String) map.get("plyr_nm"));
            rtsDTOC.setTotWins(((BigInteger) map.get("acml_wins")).toString());
            rtsDTOC.setPlyrLvl(map.get("plyr_lvl").toString());
            rtsDTOC.setpId(((BigInteger) map.get("p_id")).toString());
            rtsDTOC.setMtchEndTime(this.getSimpleTime(((Timestamp) map.get("mtch_end_time")).toString()));
            result.add(rtsDTOC);
            if (chkFgList.contains(rtsDTOC.getpId())) rtsDTOC.setChkFg("Y"); else rtsDTOC.setChkFg("N");
        }
        return result;
    }
}
