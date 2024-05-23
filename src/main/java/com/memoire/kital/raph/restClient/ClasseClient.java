package com.memoire.kital.raph.restClient;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClasseClient {
    private String id;
    private String nom;
}
