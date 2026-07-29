package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.ieeejaveriana.services.ServiceDocumentoGeneral;
import org.ieeejaveriana.services.ServiceUsuarioGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/documento")
public class ControllerDocumentoGeneral {

    @Autowired
    private ServiceDocumentoGeneral serviceDocumentoGeneral;

    @PostMapping("/creardocumento")
    public ModelDocumentoGeneral crear_documento(@RequestParam("NombreDocumento") String NombreDocumento, @RequestParam("ResumenDocumento") String ResumenDocumento, @RequestParam("DireccionContenidoDocumento") String DireccionContenidoDocumento, @RequestParam("NombresAutores") List<String> NombresAutores, @RequestParam("DireccionCodigoProyecto") String DireccionCodigoProyecto, @RequestParam("DireccionPreimagenDocumento") String DireccionPreimagenDocumento, @RequestParam("EstadoDocumento") String EstadoDocumento, @RequestParam("NombresCategoriasDocumento") List<String> NombresCategoriasDocumento, @RequestParam("FechaFinalizacionDocumento") LocalDate FechaFinalizacionDocumento) {

        ModelDocumentoGeneral NuevoDocmento = new ModelDocumentoGeneral();
        NuevoDocmento.setNombreDocumento(NombreDocumento);
        NuevoDocmento.setResumenDocumento(ResumenDocumento);
        NuevoDocmento.setDireccionCodigoProyecto(DireccionCodigoProyecto);
        NuevoDocmento.setDireccionCodigoProyecto(DireccionCodigoProyecto);
        NuevoDocmento.setDireccionPreimagenDocumento(DireccionPreimagenDocumento);
        NuevoDocmento.setEstadoDocumento(EstadoDocumento);


        

    }

    @PostMapping("/register")
    public ModelUsuarioGeneral register(@RequestParam("NombreUsuario") String NombreUsuario, @RequestParam("CorreoUsuario") String CorreoUsuario, @RequestParam("ContraseñaUsuario") String ContraseñaUsuario, @RequestParam("LinkedInUsuario") String LinkedInUsuario, @RequestParam("GitHubUsuario") String GitHubUsuario ) throws NoSuchAlgorithmException {

        ModelUsuarioGeneral UsuarioNuevo = new ModelUsuarioGeneral();
        UsuarioNuevo.setNombreUsuario(NombreUsuario);
        UsuarioNuevo.setCorreoUsuario(CorreoUsuario);
        UsuarioNuevo.setContraseñaUsuario(ContraseñaUsuario);
        UsuarioNuevo.setLinkedInUsuario(LinkedInUsuario);
        UsuarioNuevo.setGitHubUsuario(GitHubUsuario);
        return serviceUsuarioGeneral.registrar_usuario_nuevo(UsuarioNuevo);
    }

}
