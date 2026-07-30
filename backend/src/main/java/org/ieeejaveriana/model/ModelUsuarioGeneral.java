package org.ieeejaveriana.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
@NoArgsConstructor
public class ModelUsuarioGeneral {

    @Id
    @Column(name = "id_usuario")
    public Long IdUsuario;

    @Column(name = "correo")
    public String CorreoUsuario;

    @Column(name = "contrasenia_hash")
    @JsonIgnore //COM: ignora este parametro al momento de manejar Jsons
    public String ContraseñaUsuario;

    @Column(name = "linkedin")
    public String LinkedInUsuario;

    @Column(name = "github")
    public String GitHubUsuario;

    @Column(name = "nombre")
    public String NombreUsuario;

    @Column(name = "ultima_login")
    public LocalDateTime UltimaConexionUsuario;

    public Long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Long IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getCorreoUsuario() {
        return CorreoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.CorreoUsuario = correoUsuario;
    }

    public String getContraseñaUsuario() {
        return ContraseñaUsuario;
    }

    public void setContraseñaUsuario(String ContraseñaUsuario) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytesDelHash = md.digest(ContraseñaUsuario.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytesDelHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        this.ContraseñaUsuario = hexString.toString();
    }

    public String getLinkedInUsuario() {
        return LinkedInUsuario;
    }

    public void setLinkedInUsuario(String linkedInUsuario) {
        LinkedInUsuario = linkedInUsuario;
    }

    public String getGitHubUsuario() {
        return GitHubUsuario;
    }

    public void setGitHubUsuario(String gitHubUsuario) {
        GitHubUsuario = gitHubUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public LocalDateTime getUltimaConexionUsuario() {
        return UltimaConexionUsuario;
    }

    public void setUltimaConexionUsuario(LocalDateTime ultimaConexionUsuario) {
        UltimaConexionUsuario = ultimaConexionUsuario;
    }
}