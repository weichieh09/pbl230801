package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchZTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchZ.class);
        MatchZ matchZ1 = new MatchZ();
        matchZ1.setId(1L);
        MatchZ matchZ2 = new MatchZ();
        matchZ2.setId(matchZ1.getId());
        assertThat(matchZ1).isEqualTo(matchZ2);
        matchZ2.setId(2L);
        assertThat(matchZ1).isNotEqualTo(matchZ2);
        matchZ1.setId(null);
        assertThat(matchZ1).isNotEqualTo(matchZ2);
    }
}
