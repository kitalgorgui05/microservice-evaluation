package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrimestreMapperTest {

    private TrimestreMapper trimestreMapper;

    @BeforeEach
    public void setUp() {
        trimestreMapper = new TrimestreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trimestreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trimestreMapper.fromId(null)).isNull();
    }
}
