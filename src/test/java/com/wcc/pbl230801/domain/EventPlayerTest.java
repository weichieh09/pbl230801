package com.wcc.pbl230801.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wcc.pbl230801.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventPlayerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventPlayer.class);
        EventPlayer eventPlayer1 = new EventPlayer();
        eventPlayer1.setId(1L);
        EventPlayer eventPlayer2 = new EventPlayer();
        eventPlayer2.setId(eventPlayer1.getId());
        assertThat(eventPlayer1).isEqualTo(eventPlayer2);
        eventPlayer2.setId(2L);
        assertThat(eventPlayer1).isNotEqualTo(eventPlayer2);
        eventPlayer1.setId(null);
        assertThat(eventPlayer1).isNotEqualTo(eventPlayer2);
    }
}
