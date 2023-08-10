package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VwEventResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VwEventResult.class);
        VwEventResult vwEventResult1 = new VwEventResult();
        vwEventResult1.setId(1L);
        VwEventResult vwEventResult2 = new VwEventResult();
        vwEventResult2.setId(vwEventResult1.getId());
        assertThat(vwEventResult1).isEqualTo(vwEventResult2);
        vwEventResult2.setId(2L);
        assertThat(vwEventResult1).isNotEqualTo(vwEventResult2);
        vwEventResult1.setId(null);
        assertThat(vwEventResult1).isNotEqualTo(vwEventResult2);
    }
}
