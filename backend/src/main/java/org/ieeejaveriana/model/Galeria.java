package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galeria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Galeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long idFoto;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "periodo", length = 20)
    private String periodo;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;
}
