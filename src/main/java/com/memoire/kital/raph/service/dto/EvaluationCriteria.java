package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Evaluation} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.EvaluationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evaluations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvaluationCriteria implements Serializable, Criteria {

    private StringFilter id;

    private StringFilter type;

    private InstantFilter dateEvaluation;

    private StringFilter classe;

    private StringFilter matiere;

    private StringFilter trimestreId;

    public EvaluationCriteria() {
    }

    public EvaluationCriteria(EvaluationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.dateEvaluation = other.dateEvaluation == null ? null : other.dateEvaluation.copy();
        this.classe = other.classe == null ? null : other.classe.copy();
        this.matiere = other.matiere == null ? null : other.matiere.copy();
        this.trimestreId = other.trimestreId == null ? null : other.trimestreId.copy();
    }

    @Override
    public EvaluationCriteria copy() {
        return new EvaluationCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public InstantFilter getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(InstantFilter dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public StringFilter getClasse() {
        return classe;
    }

    public void setClasse(StringFilter classe) {
        this.classe = classe;
    }

    public StringFilter getMatiere() {
        return matiere;
    }

    public void setMatiere(StringFilter matiere) {
        this.matiere = matiere;
    }

    public StringFilter getTrimestreId() {
        return trimestreId;
    }

    public void setTrimestreId(StringFilter trimestreId) {
        this.trimestreId = trimestreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EvaluationCriteria that = (EvaluationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(dateEvaluation, that.dateEvaluation) &&
            Objects.equals(classe, that.classe) &&
            Objects.equals(matiere, that.matiere) &&
            Objects.equals(trimestreId, that.trimestreId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        dateEvaluation,
        classe,
        matiere,
        trimestreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (dateEvaluation != null ? "dateEvaluation=" + dateEvaluation + ", " : "") +
                (classe != null ? "classe=" + classe + ", " : "") +
                (matiere != null ? "matiere=" + matiere + ", " : "") +
                (trimestreId != null ? "trimestreId=" + trimestreId + ", " : "") +
            "}";
    }

}
