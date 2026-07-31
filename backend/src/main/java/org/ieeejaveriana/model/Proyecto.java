package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "github")
    private String github;

    @ManyToMany
    @JoinTable(
            name = "usuario_proyectos",
            joinColumns = @JoinColumn(name = "id_proyecto"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"roles", "contraseniaHash"})
    private Set<Usuario> colaboradores;

    @ManyToMany
    @JoinTable(
            name = "temas_proyectos",
            joinColumns = @JoinColumn(name = "id_proyectos"),
            inverseJoinColumns = @JoinColumn(name = "id_tema")
    )
    private Set<Tema> temas;
}
