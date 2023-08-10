package com.wcc.pbl230801.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VwEventResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VwEventResultDTO.class);
        VwEventResultDTO vwEventResultDTO1 = new VwEventResultDTO();
        vwEventResultDTO1.setId(1L);
        VwEventResultDTO vwEventResultDTO2 = new VwEventResultDTO();
        assertThat(vwEventResultDTO1).isNotEqualTo(vwEventResultDTO2);
        vwEventResultDTO2.setId(vwEventResultDTO1.getId());
        assertThat(vwEventResultDTO1).isEqualTo(vwEventResultDTO2);
        vwEventResultDTO2.setId(2L);
        assertThat(vwEventResultDTO1).isNotEqualTo(vwEventResultDTO2);
        vwEventResultDTO1.setId(null);
        assertThat(vwEventResultDTO1).isNotEqualTo(vwEventResultDTO2);
    }
}
