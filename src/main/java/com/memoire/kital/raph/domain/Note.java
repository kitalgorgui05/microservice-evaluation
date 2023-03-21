package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.restClient.EleveDTOReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Note implements Serializable {
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
    private EleveDTOReq eleveDTOReq;
    @Lob
    @Column(name = "apperciation", nullable = false)
    private String apperciation;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private Evaluation evaluation;
}
