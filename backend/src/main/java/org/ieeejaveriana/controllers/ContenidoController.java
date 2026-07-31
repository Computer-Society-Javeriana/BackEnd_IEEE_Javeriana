package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.*;
import org.ieeejaveriana.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {

    @Autowired private EventoService eventoService;
    @Autowired private TemaService temaService;
    @Autowired private LogroService logroService;
    @Autowired private CapituloService capituloService;
    @Autowired private HorarioService horarioService;
    @Autowired private LearningPathService learningPathService;
    @Autowired private GaleriaService galeriaService;

    // ==================== EVENTOS ====================

    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/eventos/{id}")
    public ResponseEntity<Evento> obtenerEvento(@PathVariable Long id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/eventos")
    public ResponseEntity<Evento> crearEvento(@RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.crear(evento));
    }

    @PutMapping("/eventos/{id}")
    public ResponseEntity<Evento> actualizarEvento(@PathVariable Long id, @RequestBody Evento datos) {
        try {
            return ResponseEntity.ok(eventoService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== CATEGORÍAS (TEMAS) ====================

    @GetMapping("/categorias")
    public ResponseEntity<List<Tema>> listarCategorias() {
        return ResponseEntity.ok(temaService.listarTodos());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Tema> obtenerCategoria(@PathVariable Long id) {
        return temaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Tema tema) {
        try {
            return ResponseEntity.ok(temaService.crear(tema));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Tema> actualizarCategoria(@PathVariable Long id, @RequestBody Tema datos) {
        try {
            return ResponseEntity.ok(temaService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        temaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== LOGROS ====================

    @GetMapping("/logros")
    public ResponseEntity<List<Logro>> listarLogros() {
        return ResponseEntity.ok(logroService.listarTodos());
    }

    @GetMapping("/logros/{id}")
    public ResponseEntity<Logro> obtenerLogro(@PathVariable String id) {
        return logroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logros")
    public ResponseEntity<?> crearLogro(@RequestBody Logro logro) {
        try {
            return ResponseEntity.ok(logroService.crear(logro));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/logros/{id}")
    public ResponseEntity<Logro> actualizarLogro(@PathVariable String id, @RequestBody Logro datos) {
        try {
            return ResponseEntity.ok(logroService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/logros/{id}")
    public ResponseEntity<Void> eliminarLogro(@PathVariable String id) {
        logroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== CAPÍTULOS ====================

    @GetMapping("/capitulos")
    public ResponseEntity<List<Capitulo>> listarCapitulos() {
        return ResponseEntity.ok(capituloService.listarTodos());
    }

    @GetMapping("/capitulos/{id}")
    public ResponseEntity<Capitulo> obtenerCapitulo(@PathVariable String id) {
        return capituloService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/capitulos")
    public ResponseEntity<?> crearCapitulo(@RequestBody Capitulo capitulo) {
        try {
            return ResponseEntity.ok(capituloService.crear(capitulo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/capitulos/{id}")
    public ResponseEntity<Capitulo> actualizarCapitulo(@PathVariable String id, @RequestBody Capitulo datos) {
        try {
            return ResponseEntity.ok(capituloService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/capitulos/{id}")
    public ResponseEntity<Void> eliminarCapitulo(@PathVariable String id) {
        capituloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== HORARIOS ====================

    @GetMapping("/horarios")
    public ResponseEntity<List<Horario>> listarHorarios(@RequestParam(value = "capitulo", required = false) String idCapitulo) {
        if (idCapitulo != null) {
            return ResponseEntity.ok(horarioService.listarPorCapitulo(idCapitulo));
        }
        return ResponseEntity.ok(horarioService.listarTodos());
    }

    @GetMapping("/horarios/{id}")
    public ResponseEntity<Horario> obtenerHorario(@PathVariable Long id) {
        return horarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/horarios")
    public ResponseEntity<Horario> crearHorario(@RequestBody Horario horario) {
        return ResponseEntity.ok(horarioService.crear(horario));
    }

    @PutMapping("/horarios/{id}")
    public ResponseEntity<Horario> actualizarHorario(@PathVariable Long id, @RequestBody Horario datos) {
        try {
            return ResponseEntity.ok(horarioService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== LEARNING PATH ====================

    @GetMapping("/learning-path")
    public ResponseEntity<List<LearningPath>> listarLearningPath(@RequestParam(value = "capitulo", required = false) String idCapitulo) {
        if (idCapitulo != null) {
            return ResponseEntity.ok(learningPathService.listarPorCapitulo(idCapitulo));
        }
        return ResponseEntity.ok(learningPathService.listarTodos());
    }

    @GetMapping("/learning-path/{id}")
    public ResponseEntity<LearningPath> obtenerLearningPath(@PathVariable Long id) {
        return learningPathService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/learning-path")
    public ResponseEntity<LearningPath> crearLearningPath(@RequestBody LearningPath path) {
        return ResponseEntity.ok(learningPathService.crear(path));
    }

    @PutMapping("/learning-path/{id}")
    public ResponseEntity<LearningPath> actualizarLearningPath(@PathVariable Long id, @RequestBody LearningPath datos) {
        try {
            return ResponseEntity.ok(learningPathService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/learning-path/{id}")
    public ResponseEntity<Void> eliminarLearningPath(@PathVariable Long id) {
        learningPathService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== GALERÍA ====================

    @GetMapping("/galeria")
    public ResponseEntity<List<Galeria>> listarGaleria(@RequestParam(value = "capitulo", required = false) String idCapitulo) {
        if (idCapitulo != null) {
            return ResponseEntity.ok(galeriaService.listarPorCapitulo(idCapitulo));
        }
        return ResponseEntity.ok(galeriaService.listarTodos());
    }

    @GetMapping("/galeria/{id}")
    public ResponseEntity<Galeria> obtenerFoto(@PathVariable Long id) {
        return galeriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/galeria")
    public ResponseEntity<Galeria> crearFoto(@RequestBody Galeria foto) {
        return ResponseEntity.ok(galeriaService.crear(foto));
    }

    @PutMapping("/galeria/{id}")
    public ResponseEntity<Galeria> actualizarFoto(@PathVariable Long id, @RequestBody Galeria datos) {
        try {
            return ResponseEntity.ok(galeriaService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/galeria/{id}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Long id) {
        galeriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
