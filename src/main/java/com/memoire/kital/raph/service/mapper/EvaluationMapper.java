package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.EvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evaluation} and its DTO {@link EvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrimestreMapper.class})
public interface EvaluationMapper extends EntityMapper<EvaluationDTO, Evaluation> {

    default Evaluation fromId(String id) {
        if (id == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setId(id);
        return evaluation;
    }
}
