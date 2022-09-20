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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Trimestre} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.TrimestreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trimestres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrimestreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateDebut;

    private LocalDateFilter dateFin;

    private StringFilter annee;

    public TrimestreCriteria() {
    }

    public TrimestreCriteria(TrimestreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateDebut = other.dateDebut == null ? null : other.dateDebut.copy();
        this.dateFin = other.dateFin == null ? null : other.dateFin.copy();
        this.annee = other.annee == null ? null : other.annee.copy();
    }

    @Override
    public TrimestreCriteria copy() {
        return new TrimestreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateFilter dateFin) {
        this.dateFin = dateFin;
    }

    public StringFilter getAnnee() {
        return annee;
    }

    public void setAnnee(StringFilter annee) {
        this.annee = annee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TrimestreCriteria that = (TrimestreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(annee, that.annee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateDebut,
        dateFin,
        annee
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrimestreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (annee != null ? "annee=" + annee + ", " : "") +
            "}";
    }

}
