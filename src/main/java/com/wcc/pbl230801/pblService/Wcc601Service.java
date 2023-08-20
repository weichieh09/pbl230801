package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.service.*;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.dto.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Wcc601Service {

    private final Logger log = LoggerFactory.getLogger(Wcc601Service.class);

    @Autowired
    private EventZService eventZService;

    @Autowired
    private TeamEventService teamEventService;

    @Autowired
    private TeamEventQueryService teamEventQueryService;

    @Autowired
    private TeamEventRepository teamEventRepository;

    @Autowired
    private EventPlayerRepository eventPlayerRepository;

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

    public EventZDTO getEventZDTO(EventZReqDTOC reqDTOC) {
        EventZDTO eventZDTO = new EventZDTO();
        eventZDTO.setId(Long.parseLong(reqDTOC.geteId()));
        eventZDTO.setEvntNm(reqDTOC.getEvntNm());
        eventZDTO.setVenue(reqDTOC.getVenue());
        eventZDTO.setEvntDt(ZonedDateTimeUtils.getZonedDateTime(reqDTOC.getEvntDt(), "00:00"));
        eventZDTO.setEventBegTime(ZonedDateTimeUtils.getZonedDateTime(reqDTOC.getEvntDt(), reqDTOC.getEventBegTime()));
        eventZDTO.setEventEndTime(ZonedDateTimeUtils.getZonedDateTime(reqDTOC.getEvntDt(), reqDTOC.getEventEndTime()));
        eventZDTO.setLstMtnUsr("MGDsn");
        eventZDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());

        return eventZDTO;
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

    @Transactional
    public EventZDTO saveEventZ(EventZDTO eventZDTO, String tId) {
        // 1. save eventZ
        EventZDTO result = eventZService.save(eventZDTO);
        // 2. save teamEvent
        TeamEventDTO teamEventDTO = new TeamEventDTO();
        teamEventDTO.settId(Long.parseLong(tId));
        teamEventDTO.seteId(result.getId());
        teamEventDTO.setLstMtnUsr("MGDsn");
        teamEventDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        teamEventService.save(teamEventDTO);
        return result;
    }

    public EventZRespDTOC getEventZRespDTOC(EventZDTO eventZDTO) {
        if (eventZDTO == null) return null;
        TeamEventCriteria teamEventCriteria = new TeamEventCriteria();
        teamEventCriteria.seteId(LongFilterUtils.toEqualLongFilter(eventZDTO.getId()));
        List<TeamEventDTO> byCriteria = teamEventQueryService.findByCriteria(teamEventCriteria);
        if (byCriteria.size() != 1) return null;
        EventZRespDTOC result = new EventZRespDTOC();
        BeanUtils.copyProperties(eventZDTO, result);
        result.settId(String.valueOf(byCriteria.get(0).gettId()));
        return result;
    }

    @Transactional
    public void deleteEventZ(Long id) {
        teamEventRepository.deleteByeId(id);
        eventPlayerRepository.deleteByeId(id);
        eventZService.delete(id);
    }
}
