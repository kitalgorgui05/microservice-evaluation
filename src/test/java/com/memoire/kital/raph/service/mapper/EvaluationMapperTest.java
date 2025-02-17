package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationMapperTest {

    private EvaluationMapper evaluationMapper;

    @BeforeEach
    public void setUp() {
        evaluationMapper = new EvaluationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(evaluationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(evaluationMapper.fromId(null)).isNull();
    }
}
