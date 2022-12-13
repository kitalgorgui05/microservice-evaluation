package com.memoire.kital.raph.restClient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClasseClient {
    private String id;
    @NotNull
    @Size(min = 4, max = 50)
    private String nom;

    @NotNull
    @Size(min = 5, max = 50)
    private String mensualite;

    //private NiveauDTO niveauDTO;

    /*public NiveauDTO getNiveauDTO() {
        return niveauDTO;
    }

    public void setNiveauDTO(NiveauDTO niveauDTO) {
        this.niveauDTO = niveauDTO;
    }
*/
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

    public String getMensualite() {
        return mensualite;
    }

    public void setMensualite(String mensualite) {
        this.mensualite = mensualite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseClient)) {
            return false;
        }

        return id != null && id.equals(((ClasseClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", mensualite='" + getMensualite() +
            "}";
    }
}
