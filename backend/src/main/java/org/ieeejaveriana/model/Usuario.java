package org.ieeejaveriana.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "ultima_login")
    private LocalDateTime ultimaLogin;

    @Column(name = "github")
    private String github;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "biografia")
    private String biografia;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "contrasenia_hash", nullable = false)
    private String contraseniaHash;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("usuario")
    private java.util.List<UsuarioCapitulo> roles;
}
