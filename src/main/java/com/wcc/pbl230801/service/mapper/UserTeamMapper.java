package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.UserTeam;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserTeam} and its DTO {@link UserTeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTeamMapper extends EntityMapper<UserTeamDTO, UserTeam> {}
