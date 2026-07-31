package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.Proyecto;
import org.ieeejaveriana.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // GET /proyectos
    @GetMapping
    public ResponseEntity<List<Proyecto>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.listarTodos());
    }

    // GET /proyectos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyecto(@PathVariable Long id) {
        return proyectoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /proyectos
    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        return ResponseEntity.ok(proyectoService.crear(proyecto));
    }

    // PUT /proyectos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto datosActualizados) {
        try {
            return ResponseEntity.ok(proyectoService.actualizar(id, datosActualizados));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /proyectos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
