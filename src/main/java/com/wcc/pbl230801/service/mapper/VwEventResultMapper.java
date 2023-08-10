package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.VwEventResult;
import com.wcc.pbl230801.service.dto.VwEventResultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VwEventResult} and its DTO {@link VwEventResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface VwEventResultMapper extends EntityMapper<VwEventResultDTO, VwEventResult> {}
