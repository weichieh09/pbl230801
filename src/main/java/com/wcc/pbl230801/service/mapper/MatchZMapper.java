package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.MatchZ;
import com.wcc.pbl230801.service.dto.MatchZDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchZ} and its DTO {@link MatchZDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchZMapper extends EntityMapper<MatchZDTO, MatchZ> {}
