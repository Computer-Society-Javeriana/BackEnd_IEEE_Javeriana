package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.UsuarioTodo;
import org.ieeejaveriana.model.UsuarioTodoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioTodoRepository extends JpaRepository<UsuarioTodo, UsuarioTodoId> {

    List<UsuarioTodo> findByUsuarioIdUsuario(Long idUsuario);

    List<UsuarioTodo> findByUsuarioIdUsuarioAndEsLiderTrue(Long idUsuario);

    List<UsuarioTodo> findByTodoIdTodo(Long idTodo);
}
