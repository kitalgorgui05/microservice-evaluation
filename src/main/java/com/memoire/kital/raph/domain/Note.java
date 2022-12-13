package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.EleveClient;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Note.
 */
@Entity
@Table(name = "notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Note implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id" , unique = true)
    private String id;
    @NotNull
    @Column(name = "note", nullable = false)
    private Double note;

    @NotNull
    @Column(name = "eleve", nullable = false)
    private String eleve;

    @Transient
    private EleveClient eleveClient;
    @Lob
    @Column(name = "apperciation", nullable = false)
    private String apperciation;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Evaluation evaluation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public Note note(Double note) {
        this.note = note;
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getEleve() {
        return eleve;
    }

    public Note eleve(String eleve) {
        this.eleve = eleve;
        return this;
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

    public Note apperciation(String apperciation) {
        this.apperciation = apperciation;
        return this;
    }

    public void setApperciation(String apperciation) {
        this.apperciation = apperciation;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Note evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", eleve='" + getEleve() + "'" +
            ", apperciation='" + getApperciation() + "'" +
            "}";
    }
}
