package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventZTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventZ.class);
        EventZ eventZ1 = new EventZ();
        eventZ1.setId(1L);
        EventZ eventZ2 = new EventZ();
        eventZ2.setId(eventZ1.getId());
        assertThat(eventZ1).isEqualTo(eventZ2);
        eventZ2.setId(2L);
        assertThat(eventZ1).isNotEqualTo(eventZ2);
        eventZ1.setId(null);
        assertThat(eventZ1).isNotEqualTo(eventZ2);
    }
}
