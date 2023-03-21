package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.AnneeClient;
import lombok.*;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Trimestre} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TrimestreDTO implements Serializable {
    private String id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String annee;
    private AnneeClient anneeClient;

    public TrimestreDTO(String id) {
        this.id = id;
    }
}
