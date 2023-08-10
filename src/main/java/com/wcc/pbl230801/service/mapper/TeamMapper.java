package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.Team;
import com.wcc.pbl230801.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {}
