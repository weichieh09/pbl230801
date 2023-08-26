package com.wcc.pbl230801.pblService;

import com.wcc.pbl230801.domain.Authority;
import com.wcc.pbl230801.domain.User;
import com.wcc.pbl230801.domain.UserTeam;
import com.wcc.pbl230801.pblService.utils.LongFilterUtils;
import com.wcc.pbl230801.pblService.utils.ZonedDateTimeUtils;
import com.wcc.pbl230801.repository.UserRepository;
import com.wcc.pbl230801.repository.UserTeamRepository;
import com.wcc.pbl230801.service.TeamService;
import com.wcc.pbl230801.service.UserService;
import com.wcc.pbl230801.service.UserTeamQueryService;
import com.wcc.pbl230801.service.UserTeamService;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.AdminUserDTO;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import com.wcc.pbl230801.web.rest.vm.KeyAndPasswordVM;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    private void finishPasswordReset(User newUser, String wPassword) {
        KeyAndPasswordVM keyAndPasswordVM = new KeyAndPasswordVM();
        keyAndPasswordVM.setKey(newUser.getResetKey());
        keyAndPasswordVM.setNewPassword(wPassword);
        Optional<User> user = userService.completePasswordReset(keyAndPasswordVM.getNewPassword(), keyAndPasswordVM.getKey());
    }

    @Transactional
    public User createUser(AdminUserDTO userDTO) {
        User newUser = userService.createUser(userDTO);
        // mail驗證註解掉
        // mailService.sendCreationEmail(newUser);
        // 原始的AccountResource方法，直接開帳號用
        this.finishPasswordReset(newUser, userDTO.getwPassword());
        if (!isRoleAdmin(newUser)) {
            UserTeamDTO userTeamDTO = new UserTeamDTO();
            userTeamDTO.setuId(newUser.getId());
            userTeamDTO.settId(Long.valueOf(userDTO.getwTeamId()));
            userTeamDTO.setLstMtnUsr("MGDsn");
            userTeamDTO.setLstMtnDt(ZonedDateTimeUtils.getTaiwanTime());
            userTeamService.save(userTeamDTO);
        }
        return newUser;
    }

    @Transactional
    public Optional<AdminUserDTO> getUser(String login) {
        Optional<AdminUserDTO> adminUserDTO = userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new);
        if (!isRoleAdmin(adminUserDTO.get())) {
            UserTeamCriteria userTeamCriteria = new UserTeamCriteria();
            userTeamCriteria.setuId(LongFilterUtils.toEqualLongFilter(adminUserDTO.get().getId()));
            UserTeamDTO userTeamDTO = userTeamQueryService.findByCriteria(userTeamCriteria).get(0);
            adminUserDTO.get().setwTeamId(userTeamDTO.gettId().toString());
        }
        return adminUserDTO;
    }

    public Boolean isRoleAdmin(AdminUserDTO user) {
        Set<String> authorities = user.getAuthorities();

        if (authorities.contains("ROLE_ADMIN")) return true;
        return false;
    }

    public Boolean isRoleAdmin(User user) {
        Set<Authority> authorities = user.getAuthorities();

        Authority roleAdmin = new Authority();
        roleAdmin.setName("ROLE_ADMIN");

        if (authorities.contains(roleAdmin)) return true;
        return false;
    }

    @Transactional
    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userTeamRepository.deleteByuId(user.getId());
                userRepository.delete(user);
                log.debug("Deleted User: {}", user);
            });
    }

    @Transactional
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        Page<AdminUserDTO> result = userRepository.findAll(pageable).map(AdminUserDTO::new);
        List<Long> uIdList = new ArrayList<>();
        for (AdminUserDTO adminUserDTO : result) uIdList.add(adminUserDTO.getId());
        List<UserTeam> userTeamList = userTeamRepository.findByuIdIn(uIdList);
        // 將 userTeamList 轉換成 Map，以 uId 作為鍵
        Map<Long, UserTeam> userTeamMap = userTeamList.stream().collect(Collectors.toMap(UserTeam::getuId, Function.identity()));
        // 遍歷 result 列表並進行處理
        for (AdminUserDTO adminUserDTO : result) {
            UserTeam userTeam = userTeamMap.get(adminUserDTO.getId());
            if (userTeam != null) {
                adminUserDTO.setwTeamId(userTeam.gettId().toString());
                teamService
                    .findOne(userTeam.gettId())
                    .ifPresent(teamDTO -> {
                        adminUserDTO.setwTeamName(teamDTO.getTeamNm());
                    });
            }
        }
        return result;
    }
}
