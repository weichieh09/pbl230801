package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.EventPlayer;
import com.wcc.pbl230801.pblService.dto.PlayerDTOC;
import com.wcc.pbl230801.pblService.dto.RespDTOC;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.EventPlayerQueryService;
import com.wcc.pbl230801.service.EventPlayerService;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Wcc901Service {

    private final Logger log = LoggerFactory.getLogger(Wcc901Service.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventPlayerRepository eventPlayerRepository;

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

    public List<PlayerDTOC> getPlayer(List<Map<String, Object>> content) {
        List<Long> pIdList = new LinkedList<>();
        for (Map<String, Object> map : content) pIdList.add(((BigInteger) map.get("id")).longValue());
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAllBypIdIn(pIdList);

        List<PlayerDTOC> result = new LinkedList<>();
        for (Map<String, Object> map : content) {
            PlayerDTOC playerDTOC = new PlayerDTOC();
            playerDTOC.setId(((BigInteger) map.get("id")).toString());
            playerDTOC.setPlyrLvl(map.get("plyrLvl").toString());
            playerDTOC.setPlyrNm((String) map.get("plyrNm"));
            if (
                eventPlayerList.stream().anyMatch(eventPlayer -> eventPlayer.getpId().equals(((BigInteger) map.get("id")).longValue()))
            ) playerDTOC.setJoinEv("Y"); else playerDTOC.setJoinEv("N");
            result.add(playerDTOC);
        }
        return result;
    }

    public EventPlayerDTO setJoinEvent(Map<String, String> reqMap) {
        Long pId = Long.valueOf(reqMap.get("pId"));
        Long eId = Long.valueOf(reqMap.get("eId"));
        EventPlayerCriteria eventPlayerCriteria = new EventPlayerCriteria();
        eventPlayerCriteria.setpId(LongFilterUtils.toEqualLongFilter(pId));
        eventPlayerCriteria.seteId(LongFilterUtils.toEqualLongFilter(eId));
        List<EventPlayerDTO> byCriteria = eventPlayerQueryService.findByCriteria(eventPlayerCriteria);
        if (byCriteria.size() == 0) {
            EventPlayerDTO eventPlayerDTO = new EventPlayerDTO();
            eventPlayerDTO.setpId(pId);
            eventPlayerDTO.seteId(eId);
            eventPlayerDTO.setChkFg("N");
            eventPlayerDTO.setLstMtnUsr("MGDsn");
            eventPlayerDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
            EventPlayerDTO result = eventPlayerService.save(eventPlayerDTO);
            return result;
        } else {
            EventPlayerDTO eventPlayerDTO = byCriteria.get(0);
            eventPlayerService.delete(eventPlayerDTO.getId());
            return eventPlayerDTO;
        }
    }
}
