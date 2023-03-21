package com.memoire.kital.raph.restClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NiveauClient {
    private String id;
    private String nom;
    private Set<MatiereClient> matieres = new HashSet<>();
}
