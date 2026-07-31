package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios_logros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogro {

    @EmbeddedId
    private UsuarioLogroId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idLogro")
    @JoinColumn(name = "id_logro")
    private Logro logro;
}
