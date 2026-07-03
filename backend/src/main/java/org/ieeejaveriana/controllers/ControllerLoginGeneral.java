package org.ieeejaveriana.controllers;

import jakarta.servlet.http.HttpSession;
import org.ieeejaveriana.model.*;
import org.ieeejaveriana.repository.RepositoryUsuarioGeneral;
import org.ieeejaveriana.services.ServiceUsuarioGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/usuario")
public class ControllerLoginGeneral {

    @Autowired
    private ServiceUsuarioGeneral serviceUsuarioGeneral;

    @PostMapping("/register")
    public ModelUsuarioGeneral register(@RequestParam String CorreoUsuario, @RequestParam String ContraseñaUsuario) throws NoSuchAlgorithmException {
        ModelUsuarioGeneral UsuarioNuevo = new ModelUsuarioGeneral();
        UsuarioNuevo.setCorreoUsuario(CorreoUsuario);
        UsuarioNuevo.setContraseñaUsuario(ContraseñaUsuario);
        return serviceUsuarioGeneral.registrar_usuario_nuevo(UsuarioNuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login_de_usuario(@RequestParam String CorreoUsuario, @RequestParam String ContraseñaUsuario, HttpSession SessionUsuario) {
        try {
            ModelUsuarioGeneral usuario = serviceUsuarioGeneral.login_usuario(CorreoUsuario, ContraseñaUsuario, SessionUsuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout_de_usuario(HttpSession SessionUsuario) {
        try {
            serviceUsuarioGeneral.logout_usuario(SessionUsuario);
            return ResponseEntity.ok("Sesión cerrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cerrar sesión");
        }
    }
}
