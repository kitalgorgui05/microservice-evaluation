package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class TrimestreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trimestre.class);
        Trimestre trimestre1 = new Trimestre();
        trimestre1.setId(null);
        Trimestre trimestre2 = new Trimestre();
        trimestre2.setId(trimestre1.getId());
        assertThat(trimestre1).isEqualTo(trimestre2);
        trimestre2.setId(null);
        assertThat(trimestre1).isNotEqualTo(trimestre2);
        trimestre1.setId(null);
        assertThat(trimestre1).isNotEqualTo(trimestre2);
    }
}
