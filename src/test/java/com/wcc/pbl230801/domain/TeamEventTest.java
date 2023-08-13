package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamEvent.class);
        TeamEvent teamEvent1 = new TeamEvent();
        teamEvent1.setId(1L);
        TeamEvent teamEvent2 = new TeamEvent();
        teamEvent2.setId(teamEvent1.getId());
        assertThat(teamEvent1).isEqualTo(teamEvent2);
        teamEvent2.setId(2L);
        assertThat(teamEvent1).isNotEqualTo(teamEvent2);
        teamEvent1.setId(null);
        assertThat(teamEvent1).isNotEqualTo(teamEvent2);
    }
}
