package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.Proyecto;
import org.ieeejaveriana.services.ProyectoFiltroService;
import org.ieeejaveriana.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private ProyectoFiltroService proyectoFiltroService;

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

    //GET /proyectos/autor/{NombreAutor}
    @GetMapping("/autor/{NombreAutor}")
    public ResponseEntity<List<Proyecto>> buscar_por_autor(@PathVariable String NombreAutor) {
        List<Proyecto> Proyectos = proyectoFiltroService.encontrar_proyectos_por_autor(NombreAutor);
        return ResponseEntity.ok(Proyectos);
    }

    //GET /proyectos/capitulo/{IdCapitulo}
    @GetMapping("/capitulo/{IdCapitulo}")
    public ResponseEntity<List<Proyecto>> buscar_por_capitulo(@PathVariable String IdCapitulo) {
        List<Proyecto> Proyectos = proyectoFiltroService.encontrar_proyectos_por_capitulo(IdCapitulo);
        return ResponseEntity.ok(Proyectos);
    }

    //GET /proyectos/buscar?palabras=inteligencia artificial
    @GetMapping("/palabras")
    public ResponseEntity<List<Proyecto>> buscar_por_palabra_clave(@RequestParam(name = "palabras") String PalabrasClave) {
        List<Proyecto> Proyectos = proyectoFiltroService.encontrar_proyecto_por_palabras_clave(PalabrasClave);
        return ResponseEntity.ok(Proyectos);
    }

    //GET /proyectos/nombre/{NombreProyecto}
    @GetMapping("/nombre/{NombreProyecto}")
    public ResponseEntity<List<Proyecto>> buscar_por_nombre_proyecto(@PathVariable String NombreProyecto) {
        List<Proyecto> Proyectos = proyectoFiltroService.encontrar_nombre_proyecto(NombreProyecto);
        return ResponseEntity.ok(Proyectos);
    }
}
