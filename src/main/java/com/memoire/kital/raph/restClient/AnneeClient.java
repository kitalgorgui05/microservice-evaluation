package com.memoire.kital.raph.restClient;

import com.memoire.kital.raph.utils.SizeMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnneeClient {

    private String id;

    @NotNull
    @Size(min = 4, max = 10)
    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof AnneeClient)) {
            return false;
        }

        return id != null && id.equals(((AnneeClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnneeClient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
