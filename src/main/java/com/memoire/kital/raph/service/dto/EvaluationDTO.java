package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import lombok.*;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Evaluation} entity.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Builder
public class EvaluationDTO implements Serializable {
    private String id;
    private String type;
    private Instant dateEvaluation;
    private String classe;
    private ClasseClient classeClient;
    private String matiere;
    private MatiereClient matiereClient;
    private TrimestreDTO trimestre;

   public EvaluationDTO(String id) {
        this.id = id;
    }

    public EvaluationDTO(
        String id,
        String type,
        Instant dateEvaluation,
        String classe,
        ClasseClient classeClient,
        String matiere,
        MatiereClient matiereClient,
        TrimestreDTO trimestre
    ) {
        this.id = id;
        this.type = type;
        this.dateEvaluation = dateEvaluation;
        this.classe = classe;
        this.classeClient = classeClient;
        this.matiere = matiere;
        this.matiereClient = matiereClient;
        this.trimestre = trimestre;
    }
}
