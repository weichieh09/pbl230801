package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.pblService.utils.StringFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.service.TeamQueryService;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.dto.TeamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Wcc401Service {

    private final Logger log = LoggerFactory.getLogger(Wcc401Service.class);

    @Autowired
    private TeamQueryService teamQueryService;

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
}
