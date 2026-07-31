package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "capitulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capitulo {

    @Id
    @Column(name = "id_capitulo", length = 5)
    private String idCapitulo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "logo", nullable = false)
    private String logo;
}
