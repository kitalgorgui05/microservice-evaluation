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

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Note} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.NoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NoteCriteria implements Serializable, Criteria {

    private StringFilter id;

    private DoubleFilter note;

    private StringFilter eleve;

    private StringFilter evaluationId;

    public NoteCriteria() {
    }

    public NoteCriteria(NoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.eleve = other.eleve == null ? null : other.eleve.copy();
        this.evaluationId = other.evaluationId == null ? null : other.evaluationId.copy();
    }

    @Override
    public NoteCriteria copy() {
        return new NoteCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public DoubleFilter getNote() {
        return note;
    }

    public void setNote(DoubleFilter note) {
        this.note = note;
    }

    public StringFilter getEleve() {
        return eleve;
    }

    public void setEleve(StringFilter eleve) {
        this.eleve = eleve;
    }

    public StringFilter getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(StringFilter evaluationId) {
        this.evaluationId = evaluationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoteCriteria that = (NoteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(note, that.note) &&
            Objects.equals(eleve, that.eleve) &&
            Objects.equals(evaluationId, that.evaluationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        note,
        eleve,
        evaluationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (eleve != null ? "eleve=" + eleve + ", " : "") +
                (evaluationId != null ? "evaluationId=" + evaluationId + ", " : "") +
            "}";
    }

}
