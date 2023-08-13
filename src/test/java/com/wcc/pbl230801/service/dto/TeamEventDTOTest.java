package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamEventDTO.class);
        TeamEventDTO teamEventDTO1 = new TeamEventDTO();
        teamEventDTO1.setId(1L);
        TeamEventDTO teamEventDTO2 = new TeamEventDTO();
        assertThat(teamEventDTO1).isNotEqualTo(teamEventDTO2);
        teamEventDTO2.setId(teamEventDTO1.getId());
        assertThat(teamEventDTO1).isEqualTo(teamEventDTO2);
        teamEventDTO2.setId(2L);
        assertThat(teamEventDTO1).isNotEqualTo(teamEventDTO2);
        teamEventDTO1.setId(null);
        assertThat(teamEventDTO1).isNotEqualTo(teamEventDTO2);
    }
}
