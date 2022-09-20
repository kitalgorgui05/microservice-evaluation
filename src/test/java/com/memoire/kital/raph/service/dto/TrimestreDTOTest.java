package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class TrimestreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrimestreDTO.class);
        TrimestreDTO trimestreDTO1 = new TrimestreDTO();
        trimestreDTO1.setId(1L);
        TrimestreDTO trimestreDTO2 = new TrimestreDTO();
        assertThat(trimestreDTO1).isNotEqualTo(trimestreDTO2);
        trimestreDTO2.setId(trimestreDTO1.getId());
        assertThat(trimestreDTO1).isEqualTo(trimestreDTO2);
        trimestreDTO2.setId(2L);
        assertThat(trimestreDTO1).isNotEqualTo(trimestreDTO2);
        trimestreDTO1.setId(null);
        assertThat(trimestreDTO1).isNotEqualTo(trimestreDTO2);
    }
}
