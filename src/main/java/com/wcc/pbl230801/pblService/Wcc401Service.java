package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.Authority;
import com.wcc.pbl230801.domain.Team;
import com.wcc.pbl230801.domain.User;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.StringFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.repository.TeamRepository;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.TeamService;
import com.wcc.pbl230801.service.UserService;
import com.wcc.pbl230801.service.UserTeamQueryService;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
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
public class Wcc401Service {

    private final Logger log = LoggerFactory.getLogger(Wcc401Service.class);

    @Autowired
    private TeamQueryService teamQueryService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamEventRepository teamEventRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTeamQueryService userTeamQueryService;

    @Autowired
    private TeamRepository teamRepository;

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

    public void addInfo(TeamDTO teamDTO) {
        teamDTO.setLstMtnUsr("MGDsn");
        teamDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
    }

    public boolean checkTeam(TeamDTO teamDTO) {
        TeamCriteria criteria = new TeamCriteria();
        criteria.setTeamNm(StringFilterUtils.toEqualStringFilter(teamDTO.getTeamNm()));
        return teamQueryService.findByCriteria(criteria).size() > 0;
    }

    @Transactional
    public void deleteTeam(Long id) {
        teamEventRepository.deleteBytId(id);
        teamPlayerRepository.deleteBytId(id);
        teamService.delete(id);
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

    public Page<UserTeamDTO> getTeamList(Long userId, Pageable pageable) {
        UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
        userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(userId));
        Page<UserTeamDTO> userTeamDTOS = userTeamQueryService.findByCriteria(userTeamCriteria, pageable);
        return userTeamDTOS;
    }

    public List<TeamDTO> getTeamDTOList(Page<UserTeamDTO> page) {
        List<Long> teamIds = new LinkedList<>();
        for (UserTeamDTO userTeamDTO : page.getContent()) teamIds.add(userTeamDTO.gettId());
        List<Team> teamList = teamRepository.findAllById(teamIds);
        List<TeamDTO> teamDTOList = new LinkedList<>();
        for (Team team : teamList) {
            TeamDTO teamDTO = new TeamDTO();
            BeanUtils.copyProperties(team, teamDTO);
            teamDTOList.add(teamDTO);
        }
        return teamDTOList;
    }
}
