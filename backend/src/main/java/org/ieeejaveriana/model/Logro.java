package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "logros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logro {

    @Id
    @Column(name = "id_logro", length = 5)
    private String idLogro;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @ManyToMany
    @JoinTable(
            name = "usuarios_logros",
            joinColumns = @JoinColumn(name = "id_logro"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> contribuyentes;
}
