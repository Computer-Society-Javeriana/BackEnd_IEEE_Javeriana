package org.ieeejaveriana.controllers;

import jakarta.servlet.http.HttpSession;
import org.ieeejaveriana.model.Usuario;
import org.ieeejaveriana.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /cuentas — Crear cuenta (registro)
    @PostMapping
    public ResponseEntity<?> crearCuenta(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrar(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de hashing");
        }
    }

    // POST /cuentas/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("correo") String correo,
                                   @RequestParam("contrasenia") String contrasenia,
                                   HttpSession session) {
        try {
            Usuario usuario = usuarioService.login(correo, contrasenia, session);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de hashing");
        }
    }

    // POST /cuentas/logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            usuarioService.logout(session);
            return ResponseEntity.ok("Sesión cerrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cerrar sesión");
        }
    }

    // GET /cuentas/me — Perfil del usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<?> obtenerMiPerfil(HttpSession session) {
        Usuario usuario = usuarioService.obtenerDesdeSession(session);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        // Refrescar datos desde BD
        return usuarioService.buscarPorId(usuario.getIdUsuario())
                .map(u -> ResponseEntity.ok((Object) u))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    // PUT /cuentas/me — Actualizar perfil propio
    @PutMapping("/me")
    public ResponseEntity<?> actualizarMiPerfil(@RequestBody Usuario datosActualizados, HttpSession session) {
        Usuario usuario = usuarioService.obtenerDesdeSession(session);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        try {
            Usuario actualizado = usuarioService.actualizarPerfil(usuario.getIdUsuario(), datosActualizados);
            session.setAttribute("usuarioLogueado", actualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /cuentas/me — Eliminar propia cuenta
    @DeleteMapping("/me")
    public ResponseEntity<?> eliminarMiCuenta(HttpSession session) {
        Usuario usuario = usuarioService.obtenerDesdeSession(session);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        usuarioService.eliminar(usuario.getIdUsuario());
        session.invalidate();
        return ResponseEntity.ok("Cuenta eliminada");
    }

    // GET /cuentas/{id} — Perfil público
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPerfilPublico(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(u -> ResponseEntity.ok((Object) u))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }
}
