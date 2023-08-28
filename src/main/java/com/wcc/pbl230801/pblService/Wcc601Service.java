package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.Authority;
import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.domain.Team;
import com.wcc.pbl230801.domain.User;
import com.wcc.pbl230801.pblService.dto.*;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.repository.TeamRepository;
import com.wcc.pbl230801.service.*;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserTeamQueryService userTeamQueryService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EventZRepository eRepository;

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

    public Boolean isRoleAdmin() {
        User user = userService.getUserWithAuthorities().get();
        Set<Authority> authorities = user.getAuthorities();

        Authority roleAdmin = new Authority();
        roleAdmin.setName("ROLE_ADMIN");

        if (authorities.contains(roleAdmin)) return true;
        return false;
    }

    public Long getUserId() {
        User user = userService.getUserWithAuthorities().get();
        return user.getId();
    }

    public boolean hasDeleteRole(Long eId) {
        Long userId = this.getUserId();
        UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
        userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(userId));
        List<UserTeamDTO> UserTeamDTOList = userTeamQueryService.findByCriteria(userTeamCriteria);

        TeamEventCriteria teamEventCriteria = new TeamEventCriteria();
        teamEventCriteria.seteId(LongFilterUtils.toEqualLongFilter(eId));
        teamEventCriteria.settId(LongFilterUtils.toEqualLongFilter(UserTeamDTOList.get(0).gettId()));
        List<TeamEventDTO> TeamEventDTOList = teamEventQueryService.findByCriteria(teamEventCriteria);
        if (TeamEventDTOList.size() > 0) return true;
        return false;
    }

    public boolean hasRole(Long tId) {
        UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
        userTeamCriteria.settId(LongFilterUtils.toEqualLongFilter(tId));
        userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(this.getUserId()));
        List<UserTeamDTO> byCriteria = userTeamQueryService.findByCriteria(userTeamCriteria);
        if (byCriteria.size() == 0) return false;
        return true;
    }

    public List<UserTeamDTO> getTeamList(Long userId) {
        UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
        userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(userId));
        List<UserTeamDTO> userTeamDTOS = userTeamQueryService.findByCriteria(userTeamCriteria);
        return userTeamDTOS;
    }

    public List<TeamDTO> getTeamDTOList(List<UserTeamDTO> list) {
        List<Long> teamIds = new LinkedList<>();
        for (UserTeamDTO userTeamDTO : list) teamIds.add(userTeamDTO.gettId());
        List<Team> teamList = teamRepository.findAllById(teamIds);
        List<TeamDTO> teamDTOList = new LinkedList<>();
        for (Team team : teamList) {
            TeamDTO teamDTO = new TeamDTO();
            BeanUtils.copyProperties(team, teamDTO);
            teamDTOList.add(teamDTO);
        }
        return teamDTOList;
    }

    public Page<TeamEventDTO> findTeamEvent(List<UserTeamDTO> userTeamDTOList, Pageable pageable) {
        TeamEventCriteria teamEventCriteria = new TeamEventCriteria();
        teamEventCriteria.settId(LongFilterUtils.toEqualLongFilter(userTeamDTOList.get(0).gettId()));
        Page<TeamEventDTO> page = teamEventQueryService.findByCriteria(teamEventCriteria, pageable);
        return page;
    }

    public List<EventZDTO> findEventZ(Page<TeamEventDTO> teamEvent) {
        List<Long> eventIds = new LinkedList<>();
        for (TeamEventDTO teamEventDTO : teamEvent.getContent()) eventIds.add(teamEventDTO.geteId());
        List<EventZ> allById = eRepository.findAllById(eventIds);
        List<EventZDTO> eventZDTOList = new LinkedList<>();
        for (EventZ eventZ : allById) {
            EventZDTO eventZDTO = new EventZDTO();
            BeanUtils.copyProperties(eventZ, eventZDTO);
            eventZDTOList.add(eventZDTO);
        }
        return eventZDTOList;
    }
}
