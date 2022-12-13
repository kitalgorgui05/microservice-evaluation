package com.memoire.kital.raph.restClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EleveClient {
    private String id;

    @NotNull
    @Size(min = 2, max = 30)
    private String prenom;

    @NotNull
    @Size(min = 2, max = 30)
    private String nom;

    //private String tuteur;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EleveClient)) {
            return false;
        }

        return id != null && id.equals(((EleveClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() +
            "}";
    }
}
