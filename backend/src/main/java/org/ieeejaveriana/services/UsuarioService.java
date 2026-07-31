package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Usuario;
import org.ieeejaveriana.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario registrar(Usuario usuario) throws NoSuchAlgorithmException {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ingresado ya está registrado");
        }
        usuario.setContraseniaHash(hashSHA512(usuario.getContraseniaHash()));
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String correo, String contrasenia, HttpSession session) throws NoSuchAlgorithmException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String hashIngresado = hashSHA512(contrasenia);
        if (!usuario.getContraseniaHash().equals(hashIngresado)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        session.setAttribute("usuarioLogueado", usuario);
        session.setMaxInactiveInterval(1800); // 30 minutos
        return usuario;
    }

    public void logout(HttpSession session) {
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario != null) {
                usuario.setUltimaLogin(LocalDateTime.now());
                usuarioRepository.save(usuario);
            }
            session.invalidate();
        }
    }

    public Usuario obtenerDesdeSession(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    public Usuario actualizarPerfil(Long id, Usuario datosActualizados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (datosActualizados.getNombre() != null) usuario.setNombre(datosActualizados.getNombre());
        if (datosActualizados.getGithub() != null) usuario.setGithub(datosActualizados.getGithub());
        if (datosActualizados.getLinkedin() != null) usuario.setLinkedin(datosActualizados.getLinkedin());
        if (datosActualizados.getBiografia() != null) usuario.setBiografia(datosActualizados.getBiografia());

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    private String hashSHA512(String texto) throws NoSuchAlgorithmException {
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytesDelHash = md.digest(texto.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytesDelHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
