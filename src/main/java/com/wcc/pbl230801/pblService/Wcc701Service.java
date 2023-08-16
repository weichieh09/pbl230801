package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.pblService.dto.EventDTOC;
import com.wcc.pbl230801.pblService.dto.RtsDTOC;
import com.wcc.pbl230801.pblService.dto.TeamDTOC;
import com.wcc.pbl230801.pblService.dto.VenueDTOC;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Wcc701Service {

    private final Logger log = LoggerFactory.getLogger(Wcc701Service.class);

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

    public List<Map<String, String>> getRealTimeScore(List<Map<String, Object>> content) {
        List<Map<String, String>> result = new LinkedList<>();
        for (Map<String, Object> obj : content) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("級數", obj.get("plyr_lvl").toString());
            map.put("姓名", (String) obj.get("plyr_nm"));
            map.put("勝場", obj.get("tot_wins").toString());
            result.add(map);
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

    public List<Map<String, String>> getVwWcc701Result(List<VwWcc701ResultDTO> byCriteria) {
        List<Map<String, String>> result = new LinkedList<>();
        for (VwWcc701ResultDTO vwWcc701ResultDTO : byCriteria) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("時間", this.getHHmm(vwWcc701ResultDTO.getMtchEndTime()));
            map.put("勝方級數1", vwWcc701ResultDTO.getwPlyr1Lvl());
            map.put("勝方姓名1", vwWcc701ResultDTO.getwPlyr1Nm());
            map.put("勝方級數2", vwWcc701ResultDTO.getwPlyr2Lvl());
            map.put("勝方姓名2", vwWcc701ResultDTO.getwPlyr2Nm());
            map.put("vs", "vs");
            map.put("敗方級數1", vwWcc701ResultDTO.getlPlyr1Lvl());
            map.put("敗方姓名1", vwWcc701ResultDTO.getlPlyr1Nm());
            map.put("敗方級數2", vwWcc701ResultDTO.getlPlyr2Lvl());
            map.put("敗方姓名2", vwWcc701ResultDTO.getlPlyr2Nm());
            map.put("勝方分數", vwWcc701ResultDTO.getwScr());
            map.put("敗方分數", vwWcc701ResultDTO.getlScr());
            result.add(map);
        }
        return result;
    }

    private String getHHmm(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = zonedDateTime.plusHours(8).format(formatter);
        return formattedTime;
    }
}
