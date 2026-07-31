package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Todo;
import org.ieeejaveriana.model.UsuarioTodo;
import org.ieeejaveriana.repository.TodoRepository;
import org.ieeejaveriana.repository.UsuarioTodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UsuarioTodoRepository usuarioTodoRepository;

    public List<Todo> listarTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> buscarPorId(Long id) {
        return todoRepository.findById(id);
    }

    public List<Todo> listarPorUsuario(Long idUsuario) {
        List<UsuarioTodo> relaciones = usuarioTodoRepository.findByUsuarioIdUsuario(idUsuario);
        return relaciones.stream()
                .map(UsuarioTodo::getTodo)
                .collect(Collectors.toList());
    }

    public List<Todo> listarEquiposDeMonitor(Long idUsuario) {
        List<UsuarioTodo> relaciones = usuarioTodoRepository.findByUsuarioIdUsuarioAndEsLiderTrue(idUsuario);
        return relaciones.stream()
                .map(UsuarioTodo::getTodo)
                .collect(Collectors.toList());
    }

    public Todo crear(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo actualizar(Long id, Todo datosActualizados) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (datosActualizados.getTitulo() != null) todo.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getDescripcion() != null) todo.setDescripcion(datosActualizados.getDescripcion());

        return todoRepository.save(todo);
    }

    public void eliminar(Long id) {
        todoRepository.deleteById(id);
    }
}
