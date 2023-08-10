package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.service.dto.EventZDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventZ} and its DTO {@link EventZDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventZMapper extends EntityMapper<EventZDTO, EventZ> {}
