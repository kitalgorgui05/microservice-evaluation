package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.TrimestreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trimestre} and its DTO {@link TrimestreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrimestreMapper extends EntityMapper<TrimestreDTO, Trimestre> {



    default Trimestre fromId(String id) {
        if (id == null) {
            return null;
        }
        Trimestre trimestre = new Trimestre();
        trimestre.setId(id);
        return trimestre;
    }
}
