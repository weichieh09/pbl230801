package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.pblService.dto.EventDTOC;
import com.wcc.pbl230801.pblService.dto.RtsDTOC;
import com.wcc.pbl230801.pblService.dto.TeamDTOC;
import com.wcc.pbl230801.pblService.dto.VenueDTOC;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Wcc101Service {

    private final Logger log = LoggerFactory.getLogger(Wcc101Service.class);

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

    public List<RtsDTOC> getRealTimeScore(List<Map<String, Object>> content) {
        List<RtsDTOC> result = new ArrayList<>();
        for (Map<String, Object> map : content) {
            RtsDTOC rtsDTOC = new RtsDTOC();
            rtsDTOC.setPlyrNm((String) map.get("plyr_nm"));
            rtsDTOC.setTotWins(((BigDecimal) map.get("tot_wins")).toString());
            rtsDTOC.setPlyrLvl((String) map.get("plyr_lvl"));
            rtsDTOC.setpId(((BigInteger) map.get("p_id")).toString());
            rtsDTOC.setMtchEndTime(this.getSimpleTime(((Timestamp) map.get("mtch_end_time")).toString()));
            result.add(rtsDTOC);
        }
        return result;
    }

    private String getSimpleTime(String inputTime) {
        // 定義時間字串格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // 解析字串為 LocalTime 物件
        LocalTime time = LocalTime.parse(inputTime, formatter);
        // 加上八小時
        LocalTime newTime = time.plusHours(8);
        // 格式化新時間為字串
        String result = newTime.format(formatter);
        return result;
    }
}
