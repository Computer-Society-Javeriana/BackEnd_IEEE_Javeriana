package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tema")
    private Long idTema;

    @Column(name = "nombre_tema", nullable = false)
    private String nombreTema;
}
