package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventZDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventZDTO.class);
        EventZDTO eventZDTO1 = new EventZDTO();
        eventZDTO1.setId(1L);
        EventZDTO eventZDTO2 = new EventZDTO();
        assertThat(eventZDTO1).isNotEqualTo(eventZDTO2);
        eventZDTO2.setId(eventZDTO1.getId());
        assertThat(eventZDTO1).isEqualTo(eventZDTO2);
        eventZDTO2.setId(2L);
        assertThat(eventZDTO1).isNotEqualTo(eventZDTO2);
        eventZDTO1.setId(null);
        assertThat(eventZDTO1).isNotEqualTo(eventZDTO2);
    }
}
