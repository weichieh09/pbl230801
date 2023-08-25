package com.wcc.pbl230801.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTeamMapperTest {

    private UserTeamMapper userTeamMapper;

    @BeforeEach
    public void setUp() {
        userTeamMapper = new UserTeamMapperImpl();
    }
}
