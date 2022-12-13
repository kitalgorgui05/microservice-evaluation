package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import com.memoire.kital.raph.utils.SizeMapper;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Evaluation.
 */
@Entity
@Table(name = "evaluations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evaluation implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.EvaluationTypeSize.MIN, max = SizeMapper.EvaluationTypeSize.MAX)
    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @NotNull
    @Column(name = "date_evaluation", nullable = false)
    private Instant dateEvaluation;

    @NotNull
    @Column(name = "classe", nullable = false)
    private String classe;

    @Transient
    private ClasseClient classeClient;

    @NotNull
    @Column(name = "matiere", nullable = false)
    private String matiere;

    @Transient
    private MatiereClient matiereClient;

    @ManyToOne
    @JsonIgnoreProperties(value = "evaluations", allowSetters = true)
    private Trimestre trimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Evaluation type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getDateEvaluation() {
        return dateEvaluation;
    }

    public Evaluation dateEvaluation(Instant dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
        return this;
    }

    public void setDateEvaluation(Instant dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public String getClasse() {
        return classe;
    }

    public Evaluation classe(String classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMatiere() {
        return matiere;
    }

    public Evaluation matiere(String matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Trimestre getTrimestre() {
        return trimestre;
    }

    public Evaluation trimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
        return this;
    }

    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public ClasseClient getClasseClient() {
        return classeClient;
    }

    public void setClasseClient(ClasseClient classeClient) {
        this.classeClient = classeClient;
    }

    public MatiereClient getMatiereClient() {
        return matiereClient;
    }

    public void setMatiereClient(MatiereClient matiereClient) {
        this.matiereClient = matiereClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evaluation)) {
            return false;
        }
        return id != null && id.equals(((Evaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", dateEvaluation='" + getDateEvaluation() + "'" +
            ", classe='" + getClasse() + "'" +
            ", matiere='" + getMatiere() + "'" +
            ",classeClient='"+getClasseClient()+"'"+
            ",matiereClient='"+getMatiereClient()+
            "}";
    }
}
