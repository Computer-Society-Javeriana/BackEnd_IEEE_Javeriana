package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.Todo;
import org.ieeejaveriana.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // GET /todos
    // GET /todos?usuarios={id}
    @GetMapping
    public ResponseEntity<List<Todo>> listarTodos(@RequestParam(value = "usuarios", required = false) Long idUsuario) {
        if (idUsuario != null) {
            return ResponseEntity.ok(todoService.listarPorUsuario(idUsuario));
        }
        return ResponseEntity.ok(todoService.listarTodos());
    }

    // GET /todos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Todo> obtenerTodo(@PathVariable Long id) {
        return todoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /todos
    @PostMapping
    public ResponseEntity<Todo> crearTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.crear(todo));
    }

    // PUT /todos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Todo> actualizarTodo(@PathVariable Long id, @RequestBody Todo datos) {
        try {
            return ResponseEntity.ok(todoService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /todos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTodo(@PathVariable Long id) {
        todoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
