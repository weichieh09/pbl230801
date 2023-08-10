package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Player} and its DTO {@link PlayerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {}
