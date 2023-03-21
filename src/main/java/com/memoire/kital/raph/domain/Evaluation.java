package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.ClasseClient;
import com.memoire.kital.raph.restClient.MatiereClient;
import com.memoire.kital.raph.utils.SizeMapper;
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
import java.time.Instant;

/**
 * A Evaluation.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evaluation implements Serializable {
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
}
