package org.ieeejaveriana.controllers;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelTemaGeneral;
import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.ieeejaveriana.repository.RepositoryDocumentoGeneral;
import org.ieeejaveriana.repository.RepositoryUsuarioGeneral;
import org.ieeejaveriana.services.ServiceDocumentoGeneral;
import org.ieeejaveriana.services.ServiceTemaGeneral;
import org.ieeejaveriana.services.ServiceUsuarioGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documento")
public class ControllerDocumentoGeneral {

    @Autowired
    private ServiceDocumentoGeneral serviceDocumentoGeneral;

    @Autowired
    private ServiceTemaGeneral serviceTemaGeneral;

    @Autowired
    private ServiceUsuarioGeneral serviceUsuarioGeneral;
    @Autowired
    private RepositoryUsuarioGeneral repositoryUsuarioGeneral;

    @PostMapping("/creardocumento")
    public ModelDocumentoGeneral crear_documento(@RequestParam("NombreDocumento") String NombreDocumento, @RequestParam("ResumenDocumento") String ResumenDocumento, @RequestParam("DireccionContenidoDocumento") String DireccionContenidoDocumento, @RequestParam("NombresAutores") List<String> NombresAutores, @RequestParam("DireccionCodigoProyecto") String DireccionCodigoProyecto, @RequestParam("DireccionPreimagenDocumento") String DireccionPreimagenDocumento, @RequestParam("EstadoDocumento") String EstadoDocumento, @RequestParam("NombresCategoriasDocumento") List<String> NombresTemas, @RequestParam("FechaFinalizacionDocumento") LocalDate FechaFinalizacionDocumento, @RequestParam("CorreoUsuario") Optional<List<String>> CorreoUsuario, @RequestParam("LinkedInUsuario") Optional<List<String>> LinkedInsAutores, @RequestParam("GitHubUsuario") Optional<List<String>> GitHubsAutores) throws NoSuchAlgorithmException {

        ModelDocumentoGeneral NuevoDocmento = new ModelDocumentoGeneral();
        NuevoDocmento.setNombreDocumento(NombreDocumento);
        NuevoDocmento.setResumenDocumento(ResumenDocumento);
        NuevoDocmento.setDireccionCodigoProyecto(DireccionCodigoProyecto);
        NuevoDocmento.setDireccionContenidoDocumento(DireccionContenidoDocumento);
        NuevoDocmento.setDireccionPreimagenDocumento(DireccionPreimagenDocumento);
        NuevoDocmento.setEstadoDocumento(EstadoDocumento);
        NuevoDocmento.setFechaFinalizacionDocumento(FechaFinalizacionDocumento);

        List<Long> IdAutores = new ArrayList<>();
        List<Long> IdTemas = new ArrayList<>();


        for(String NombreTema : NombresTemas){
            if(serviceTemaGeneral.encontrar_tema_por_nombre(NombreTema)!=null){
                IdTemas.add(serviceTemaGeneral.encontrar_tema_por_nombre(NombreTema));
            }else{
                JOptionPane.showMessageDialog(null, "No existe el tema '"+NombreTema+"' ");
            }
        }

        for (int i = 0; i < NombresAutores.size(); i++) {
            String NombreAutor = NombresAutores.get(i);

            if (serviceUsuarioGeneral.encontrar_usuario_por_nombre(NombreAutor) != null) {
                IdAutores.add(serviceUsuarioGeneral.encontrar_id_usuario_por_nombre(NombreAutor));
            } else {
                ModelUsuarioGeneral NuevoAutor = new ModelUsuarioGeneral();

                final int index = i;

                String CorreoObtenido = CorreoUsuario.filter(lista -> index < lista.size()).map(lista -> lista.get(index)).orElse("No hay");

                String LinkedInObtenido = LinkedInsAutores.filter(lista -> index < lista.size()).map(lista -> lista.get(index)).orElse("No hay");

                String GitHubObtenido = GitHubsAutores.filter(lista -> index < lista.size()).map(lista -> lista.get(index)).orElse("No hay");

                NuevoAutor.setNombreUsuario(NombreAutor);
                NuevoAutor.setCorreoUsuario(CorreoObtenido);
                NuevoAutor.setContraseñaUsuario("99999999999999999999999999999999999999");
                NuevoAutor.setGitHubUsuario(GitHubObtenido);
                NuevoAutor.setLinkedInUsuario(LinkedInObtenido);

                repositoryUsuarioGeneral.save(NuevoAutor);

                Long IdNuevoAutor = serviceUsuarioGeneral.encontrar_id_usuario_por_nombre(NombreAutor);
                IdAutores.add(IdNuevoAutor);
            }
        }

        NuevoDocmento.setTemas(IdTemas);
        NuevoDocmento.setAutoresDocumentoId(IdAutores);

        return serviceDocumentoGeneral.guardar_documento_nuevo(NuevoDocmento);
    }

}
