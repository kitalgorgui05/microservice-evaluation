package com.memoire.kital.raph.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Evaluation} entity.
 */
public class EvaluationDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 2, max = 20)
    private String type;

    @NotNull
    private Instant dateEvaluation;

    @NotNull
    private String classe;

    @NotNull
    private String matiere;


    private Long trimestreId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Instant dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Long getTrimestreId() {
        return trimestreId;
    }

    public void setTrimestreId(Long trimestreId) {
        this.trimestreId = trimestreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", dateEvaluation='" + getDateEvaluation() + "'" +
            ", classe='" + getClasse() + "'" +
            ", matiere='" + getMatiere() + "'" +
            ", trimestreId=" + getTrimestreId() +
            "}";
    }
}
