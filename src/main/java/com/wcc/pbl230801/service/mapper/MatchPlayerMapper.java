package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.MatchPlayer;
import com.wcc.pbl230801.service.dto.MatchPlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchPlayer} and its DTO {@link MatchPlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchPlayerMapper extends EntityMapper<MatchPlayerDTO, MatchPlayer> {}
