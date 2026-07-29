package org.ieeejaveriana.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temas")
@NoArgsConstructor
public class ModelTemaGeneral {

    @Id
    @Column(name = "id_tema")
    public Long IdTema;

    @Column(name = "nombre_tema")
    public String NombreTema;

    public String getNombreTema() {
        return NombreTema;
    }

    public void setNombreTema(String nombreTema) {
        NombreTema = nombreTema;
    }

    public Long getIdTema() {
        return IdTema;
    }

    public void setIdTema(Long idTema) {
        IdTema = idTema;
    }
}
