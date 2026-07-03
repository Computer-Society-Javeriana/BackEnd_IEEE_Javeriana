package org.ieeejaveriana.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ieeejaveriana.model.*;
import org.ieeejaveriana.repository.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpSession;

@Service
public class ServiceUsuarioGeneral {

    @Autowired
    private RepositoryUsuarioGeneral repositoryUsuarioGeneral;

    //METEXPL: Este es un metodo para verificar que el CorreoUsuario de un usuario existe o no dentro de la base de datos
    public ModelUsuarioGeneral encontrar_usuario_por_correo(String CorreoUsuario){
        for (ModelUsuarioGeneral UsuarioTemporal : repositoryUsuarioGeneral.findAll()) {
            if (UsuarioTemporal.getCorreoUsuario().equals(CorreoUsuario)) {
                return UsuarioTemporal;
            }
        }
        return null;
    }

    //METEXPL: Este metodo es para registrar a nuevos usuarios en la base de datos
    public ModelUsuarioGeneral registrar_usuario_nuevo(ModelUsuarioGeneral UsuarioNuevo) {
        if(encontrar_usuario_por_correo(UsuarioNuevo.getCorreoUsuario()) != null){ //METUSEEXPL: se utiliza el metodo "existe_correo_usuario" para verificar si el CorreoUsuario que esta tratando de ingresar un nuevo usuario ya esta siendo usado por otro CorreoUsuario
            throw new RuntimeException("El CorreoUsuario que intenta ingresar ya está registrado");
        }
        return repositoryUsuarioGeneral.save(UsuarioNuevo);
    }

    //METEXPL: Este metodo es para realizar login
    public ModelUsuarioGeneral login_usuario(String CorreoDelUsuario, String ContraseñaDelUsuario, HttpSession SessionUsuario) throws NoSuchAlgorithmException {
        ModelUsuarioGeneral UsuarioTemporal = encontrar_usuario_por_correo(CorreoDelUsuario);

        if (UsuarioTemporal == null)
            throw new IllegalArgumentException("El usuario no ha sido encontrado");

        //COM: Las siguientes lineas sirven para aplicar un metodo hash al texto plano que ingresa el usuario
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytesDelHash = md.digest(ContraseñaDelUsuario.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytesDelHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        String ContraseñaUsuarioConHash = hexString.toString();
        //COM: aca termina la aplicacion de hash

        if (!UsuarioTemporal.getContraseñaUsuario().equals(ContraseñaUsuarioConHash)) {
            throw new RuntimeException("La contraseña ingresada es incorrecta");
        }

        //COM: el siguiente codigo es para guardar la sesion
        SessionUsuario.setAttribute("usuarioLogueado", UsuarioTemporal);

        //COM: el siguiente codigo es un contador de inacitivida
        SessionUsuario.setMaxInactiveInterval(1800);

        return UsuarioTemporal;
    }

    //METEXPL: Este metodo es para realizar logout
    public void logout_usuario(HttpSession SessionUsuario) {
        if (SessionUsuario != null) {
            SessionUsuario.invalidate();
        }
    }
}