package com.wcc.pbl230801.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchZMapperTest {

    private MatchZMapper matchZMapper;

    @BeforeEach
    public void setUp() {
        matchZMapper = new MatchZMapperImpl();
    }
}
