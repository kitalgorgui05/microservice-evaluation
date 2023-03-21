package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.restClient.EleveDTOReq;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Note} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class NoteDTO implements Serializable {
    private String id;
    private Double note;
    private String eleve;
    private EleveDTOReq eleveDTOReq;
    private String apperciation;
    private EvaluationDTO evaluation;
}
