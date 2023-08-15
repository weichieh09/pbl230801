package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.VwWcc701Result;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VwWcc701Result} and its DTO {@link VwWcc701ResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface VwWcc701ResultMapper extends EntityMapper<VwWcc701ResultDTO, VwWcc701Result> {}
