package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VwWcc701ResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VwWcc701Result.class);
        VwWcc701Result vwWcc701Result1 = new VwWcc701Result();
        vwWcc701Result1.setId(1L);
        VwWcc701Result vwWcc701Result2 = new VwWcc701Result();
        vwWcc701Result2.setId(vwWcc701Result1.getId());
        assertThat(vwWcc701Result1).isEqualTo(vwWcc701Result2);
        vwWcc701Result2.setId(2L);
        assertThat(vwWcc701Result1).isNotEqualTo(vwWcc701Result2);
        vwWcc701Result1.setId(null);
        assertThat(vwWcc701Result1).isNotEqualTo(vwWcc701Result2);
    }
}
