package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.User;
import com.wcc.pbl230801.pblService.dto.EventDTOC;
import com.wcc.pbl230801.pblService.dto.RtsDTOC;
import com.wcc.pbl230801.pblService.dto.TeamDTOC;
import com.wcc.pbl230801.pblService.dto.VenueDTOC;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.service.UserService;
import com.wcc.pbl230801.service.UserTeamQueryService;
import com.wcc.pbl230801.service.UserTeamService;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.AdminUserDTO;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import com.wcc.pbl230801.web.rest.vm.KeyAndPasswordVM;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class WuserService {

    private final Logger log = LoggerFactory.getLogger(WuserService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private UserTeamQueryService userTeamQueryService;

    private void finishPasswordReset(User newUser) {
        KeyAndPasswordVM keyAndPasswordVM = new KeyAndPasswordVM();
        keyAndPasswordVM.setKey(newUser.getResetKey());
        keyAndPasswordVM.setNewPassword(newUser.getLogin());
        Optional<User> user = userService.completePasswordReset(keyAndPasswordVM.getNewPassword(), keyAndPasswordVM.getKey());
    }

    @Transactional
    public User createUser(AdminUserDTO userDTO) {
        User newUser = userService.createUser(userDTO);
        // mail驗證註解掉
        // mailService.sendCreationEmail(newUser);
        // 原始的AccountResource方法，直接開帳號用
        newUser.setPassword(userDTO.getwPassword());
        this.finishPasswordReset(newUser);
        // TODO:一般管理員的球隊綁定
        UserTeamDTO userTeamDTO = new UserTeamDTO();
        userTeamDTO.setuId(newUser.getId());
        userTeamDTO.settId(Long.valueOf(userDTO.getwTeamId()));
        userTeamDTO.setLstMtnUsr("MGDsn");
        userTeamDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
        userTeamService.save(userTeamDTO);
        newUser.setPassword(null);
        return newUser;
    }

    @Transactional
    public Optional<AdminUserDTO> getUser(String login) {
        Optional<AdminUserDTO> adminUserDTO = userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new);
        Long userId = adminUserDTO.get().getId();
        UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
        userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(userId));
        UserTeamDTO userTeamDTO = userTeamQueryService.findByCriteria(userTeamCriteria).get(0);
        adminUserDTO.get().setwTeamId(userTeamDTO.gettId().toString());
        return adminUserDTO;
    }
}
