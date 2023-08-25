package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTeam.class);
        UserTeam userTeam1 = new UserTeam();
        userTeam1.setId(1L);
        UserTeam userTeam2 = new UserTeam();
        userTeam2.setId(userTeam1.getId());
        assertThat(userTeam1).isEqualTo(userTeam2);
        userTeam2.setId(2L);
        assertThat(userTeam1).isNotEqualTo(userTeam2);
        userTeam1.setId(null);
        assertThat(userTeam1).isNotEqualTo(userTeam2);
    }
}
