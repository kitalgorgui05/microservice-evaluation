package com.memoire.kital.raph.restClient;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EleveDTOReq {
    private String id;
    private String prenom;
    private String nom;
}
