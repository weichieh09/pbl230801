package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.TeamEvent;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeamEvent} and its DTO {@link TeamEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamEventMapper extends EntityMapper<TeamEventDTO, TeamEvent> {}
