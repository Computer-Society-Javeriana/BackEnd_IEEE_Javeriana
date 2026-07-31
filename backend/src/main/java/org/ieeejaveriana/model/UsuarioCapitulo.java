package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_capitulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCapitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_capitulo")
    private Long idUsuarioCapitulo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_capitulo", nullable = false)
    private Capitulo capitulo;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "admin", nullable = false)
    private Boolean admin = false;
}
