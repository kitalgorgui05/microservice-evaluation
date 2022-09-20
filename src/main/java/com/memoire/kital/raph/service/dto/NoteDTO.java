package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Note} entity.
 */
public class NoteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double note;

    @NotNull
    private String eleve;

    
    @Lob
    private String apperciation;


    private Long evaluationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getEleve() {
        return eleve;
    }

    public void setEleve(String eleve) {
        this.eleve = eleve;
    }

    public String getApperciation() {
        return apperciation;
    }

    public void setApperciation(String apperciation) {
        this.apperciation = apperciation;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        return id != null && id.equals(((NoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", eleve='" + getEleve() + "'" +
            ", apperciation='" + getApperciation() + "'" +
            ", evaluationId=" + getEvaluationId() +
            "}";
    }
}
