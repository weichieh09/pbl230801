package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTeamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTeamDTO.class);
        UserTeamDTO userTeamDTO1 = new UserTeamDTO();
        userTeamDTO1.setId(1L);
        UserTeamDTO userTeamDTO2 = new UserTeamDTO();
        assertThat(userTeamDTO1).isNotEqualTo(userTeamDTO2);
        userTeamDTO2.setId(userTeamDTO1.getId());
        assertThat(userTeamDTO1).isEqualTo(userTeamDTO2);
        userTeamDTO2.setId(2L);
        assertThat(userTeamDTO1).isNotEqualTo(userTeamDTO2);
        userTeamDTO1.setId(null);
        assertThat(userTeamDTO1).isNotEqualTo(userTeamDTO2);
    }
}
