package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios_todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTodo {

    @EmbeddedId
    private UsuarioTodoId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idTodos")
    @JoinColumn(name = "id_todos")
    private Todo todo;

    @Column(name = "es_lider", nullable = false)
    private Boolean esLider = false;
}
