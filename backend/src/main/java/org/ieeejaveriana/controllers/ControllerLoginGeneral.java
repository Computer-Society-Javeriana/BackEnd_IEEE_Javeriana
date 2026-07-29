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

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/usuario")
public class ControllerLoginGeneral {

    @Autowired
    private ServiceUsuarioGeneral serviceUsuarioGeneral;

    @PostMapping("/register")
    public ModelUsuarioGeneral register(@RequestParam("NombreUsuario") String NombreUsuario, @RequestParam("CorreoUsuario") String CorreoUsuario, @RequestParam("ContraseñaUsuario") String ContraseñaUsuario, @RequestParam("LinkedInUsuario") String LinkedInUsuario, @RequestParam("GitHubUsuario") String GitHubUsuario ) throws NoSuchAlgorithmException {

        if(ContraseñaUsuario.equals("99999999999999999999999999999999999999")){
            throw new RuntimeException("NO se puede tener una contraseña tan larga");
        }

        if(ContraseñaUsuario.length()>="99999999999999999999999999999999999999".length()){
            throw new RuntimeException("NO se puede tener una contraseña tan larga");
        }

        ModelUsuarioGeneral UsuarioNuevo = new ModelUsuarioGeneral();
        UsuarioNuevo.setNombreUsuario(NombreUsuario);
        UsuarioNuevo.setCorreoUsuario(CorreoUsuario);
        UsuarioNuevo.setContraseñaUsuario(ContraseñaUsuario);
        UsuarioNuevo.setLinkedInUsuario(LinkedInUsuario);
        UsuarioNuevo.setGitHubUsuario(GitHubUsuario);
        return serviceUsuarioGeneral.registrar_usuario_nuevo(UsuarioNuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login_de_usuario(@RequestParam("CorreoUsuario") String CorreoUsuario, @RequestParam("ContraseñaUsuario") String ContraseñaUsuario, HttpSession SessionUsuario) {

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