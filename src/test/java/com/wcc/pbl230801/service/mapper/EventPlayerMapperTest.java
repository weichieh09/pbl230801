package com.wcc.pbl230801.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventPlayerMapperTest {

    private EventPlayerMapper eventPlayerMapper;

    @BeforeEach
    public void setUp() {
        eventPlayerMapper = new EventPlayerMapperImpl();
    }
}
