package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.Proyecto;
import org.ieeejaveriana.model.Usuario;
import org.ieeejaveriana.model.UsuarioCapitulo;
import org.ieeejaveriana.repository.UsuarioCapituloRepository;
import org.ieeejaveriana.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioCapituloRepository usuarioCapituloRepository;

    // GET /usuarios
    // GET /usuarios?categoria={idCapitulo}
    // GET /usuarios?todos=true
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(
            @RequestParam(value = "categoria", required = false) String idCapitulo,
            @RequestParam(value = "todos", required = false, defaultValue = "false") boolean todos) {

        if (todos) {
            return ResponseEntity.ok(usuarioService.listarTodos());
        }

        if (idCapitulo != null) {
            List<UsuarioCapitulo> miembros = usuarioCapituloRepository.findByCapituloIdCapitulo(idCapitulo);
            List<Usuario> usuarios = miembros.stream()
                    .map(UsuarioCapitulo::getUsuario)
                    .distinct()
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usuarios);
        }

        // Para la vista de Junta / Equipo: Solo usuarios con al menos un capítulo asignado
        List<UsuarioCapitulo> todosMiembros = usuarioCapituloRepository.findAll();
        List<Usuario> usuariosConCapitulo = todosMiembros.stream()
                .map(UsuarioCapitulo::getUsuario)
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosConCapitulo);
    }

    // GET /usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /usuarios/{id}/proyectos
    @GetMapping("/{id}/proyectos")
    public ResponseEntity<?> obtenerProyectosDeUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    // Los proyectos se obtienen buscando al usuario en la tabla intermedia
                    // Por ahora retornamos la info del usuario; el frontend puede buscar proyectos por usuario
                    return ResponseEntity.ok(usuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datosActualizados) {
        try {
            Usuario actualizado = usuarioService.actualizarPerfil(id, datosActualizados);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
