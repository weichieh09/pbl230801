package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.EventPlayer;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventPlayer} and its DTO {@link EventPlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventPlayerMapper extends EntityMapper<EventPlayerDTO, EventPlayer> {}
