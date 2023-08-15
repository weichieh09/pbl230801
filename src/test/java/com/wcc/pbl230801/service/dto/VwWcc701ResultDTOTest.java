package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VwWcc701ResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VwWcc701ResultDTO.class);
        VwWcc701ResultDTO vwWcc701ResultDTO1 = new VwWcc701ResultDTO();
        vwWcc701ResultDTO1.setId(1L);
        VwWcc701ResultDTO vwWcc701ResultDTO2 = new VwWcc701ResultDTO();
        assertThat(vwWcc701ResultDTO1).isNotEqualTo(vwWcc701ResultDTO2);
        vwWcc701ResultDTO2.setId(vwWcc701ResultDTO1.getId());
        assertThat(vwWcc701ResultDTO1).isEqualTo(vwWcc701ResultDTO2);
        vwWcc701ResultDTO2.setId(2L);
        assertThat(vwWcc701ResultDTO1).isNotEqualTo(vwWcc701ResultDTO2);
        vwWcc701ResultDTO1.setId(null);
        assertThat(vwWcc701ResultDTO1).isNotEqualTo(vwWcc701ResultDTO2);
    }
}
