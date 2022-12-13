package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.EleveClient;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Note} entity.
 */
public class NoteDTO implements Serializable {

    private String id;

    @NotNull
    private Double note;

    @NotNull
    private String eleve;

    private EleveClient eleveClient;

    @Lob
    private String apperciation;


    private String evaluationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public EleveClient getEleveClient() {
        return eleveClient;
    }

    public void setEleveClient(EleveClient eleveClient) {
        this.eleveClient = eleveClient;
    }

    public String getApperciation() {
        return apperciation;
    }

    public void setApperciation(String apperciation) {
        this.apperciation = apperciation;
    }

    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
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
