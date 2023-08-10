package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchZDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchZDTO.class);
        MatchZDTO matchZDTO1 = new MatchZDTO();
        matchZDTO1.setId(1L);
        MatchZDTO matchZDTO2 = new MatchZDTO();
        assertThat(matchZDTO1).isNotEqualTo(matchZDTO2);
        matchZDTO2.setId(matchZDTO1.getId());
        assertThat(matchZDTO1).isEqualTo(matchZDTO2);
        matchZDTO2.setId(2L);
        assertThat(matchZDTO1).isNotEqualTo(matchZDTO2);
        matchZDTO1.setId(null);
        assertThat(matchZDTO1).isNotEqualTo(matchZDTO2);
    }
}
