package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.TeamPlayer;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamPlayer} and its DTO {@link TeamPlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamPlayerMapper extends EntityMapper<TeamPlayerDTO, TeamPlayer> {}
