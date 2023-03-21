package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.restClient.AnneeClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Trimestre.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trimestres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trimestre implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "annee", nullable = false)
    private String annee;

    @Transient
    private AnneeClient anneeClient;
}
