package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventPlayerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventPlayerDTO.class);
        EventPlayerDTO eventPlayerDTO1 = new EventPlayerDTO();
        eventPlayerDTO1.setId(1L);
        EventPlayerDTO eventPlayerDTO2 = new EventPlayerDTO();
        assertThat(eventPlayerDTO1).isNotEqualTo(eventPlayerDTO2);
        eventPlayerDTO2.setId(eventPlayerDTO1.getId());
        assertThat(eventPlayerDTO1).isEqualTo(eventPlayerDTO2);
        eventPlayerDTO2.setId(2L);
        assertThat(eventPlayerDTO1).isNotEqualTo(eventPlayerDTO2);
        eventPlayerDTO1.setId(null);
        assertThat(eventPlayerDTO1).isNotEqualTo(eventPlayerDTO2);
    }
}
