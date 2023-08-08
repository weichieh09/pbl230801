package com.wcc.pbl230801.service.mapper;

import com.wcc.pbl230801.domain.Prod;
import com.wcc.pbl230801.service.dto.ProdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prod} and its DTO {@link ProdDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdMapper extends EntityMapper<ProdDTO, Prod> {}
