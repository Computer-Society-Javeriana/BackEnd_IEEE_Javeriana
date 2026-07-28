package org.ieeejaveriana.services;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.ieeejaveriana.repository.RepositoryDocumentoGeneral;
import org.ieeejaveriana.repository.RepositoryUsuarioGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Service
public class ServiceDocumentoGeneral {

    @Autowired
    private RepositoryDocumentoGeneral repositoryDocumentoGeneral;

    @Autowired
    private RepositoryUsuarioGeneral repositoryUsuarioGeneral;

    @Autowired
    private ServiceUsuarioGeneral serviceUsuarioGeneral;

    //METEXPL: Este es un metodo para verificar que el autor de un documento existe o no dentro de la base de datos
    public Long encontrar_autor_documento(String AutorDocumento){
        for (ModelUsuarioGeneral UsuarioTemporal : repositoryUsuarioGeneral.findAll()) {
            if (UsuarioTemporal.getNombreUsuario().equals(AutorDocumento)) {
                return UsuarioTemporal.getIdUsuario();
            }
        }
        return null;
    }

    //METEXPL: Este es un metodo para verificar que el nombre de un documento existe o no dentro de la base de datos
    public String encontrar_nombre_documento(String NombreDocumento){
        for (ModelDocumentoGeneral DocumentoTemporal : repositoryDocumentoGeneral.findAll()) {
            if (DocumentoTemporal.getNombreDocumento().equals(NombreDocumento)) {
                return NombreDocumento;
            }
        }
        return null;
    }

    //METEXPL: Este es un metodo para encontrar los documentos de un autor especifico (por nombre)
    public List<ModelDocumentoGeneral> encontrar_documentos_por_autor_del_documento(String AutorDocumento){

        if(encontrar_autor_documento(AutorDocumento)==null){
            return null;
        }

        List<ModelDocumentoGeneral> DocumentosEncontrados = new ArrayList<>();

        for (ModelDocumentoGeneral DocumentoTemporal : repositoryDocumentoGeneral.findAll()){
            List<Long> IdsAutoresDocumentoTemporal = DocumentoTemporal.getAutoresDocumentoId();

            for (Long IdAutor : IdsAutoresDocumentoTemporal){
                ModelUsuarioGeneral UsuarioTemporal = serviceUsuarioGeneral.encontrar_usuario_por_id(IdAutor);

                if(UsuarioTemporal.getNombreUsuario().equals(AutorDocumento)){
                    DocumentosEncontrados.add(DocumentoTemporal);
                }
            }
        }

        return DocumentosEncontrados;
    }

    public List<ModelDocumentoGeneral> encontrar_documento_por_palabras_clave (String PalabrasClave) { //COM: las palabras claves se deben concatenar tipo "palabra1"+" "+"palabra2"
        List<ModelDocumentoGeneral> DocumentosEncontrados = new ArrayList<>();

        for (ModelDocumentoGeneral DocumentoTemporal : repositoryDocumentoGeneral.findAll()){
            String DescripcionDocumentoTemporal = DocumentoTemporal.getResumenDocumento();
            String TemasDocumentoTemporal = DocumentoTemporal.getTemasDocumento();
            String[] PalabrasResumen = DescripcionDocumentoTemporal.trim().split("\\s+");
            String[] PalabrasTemas = TemasDocumentoTemporal.trim().split("\\s+");
            String[] PalabrasClaves = PalabrasClave.trim().split("\\s+");

            for(String PalabraResumen : PalabrasResumen){
                for(String PalabraClave : PalabrasClaves){
                    if(PalabraClave.equals(PalabraResumen)){
                        DocumentosEncontrados.add(DocumentoTemporal);
                    }
                }
            }

            for(String PalabraTema : PalabrasTemas){
                for(String PalabraClave : PalabrasClaves){
                    if(PalabraClave.equals(PalabraTema)){
                        DocumentosEncontrados.add(DocumentoTemporal);
                    }
                }
            }

        }

        return DocumentosEncontrados;
    }

    public ModelDocumentoGeneral guardar_documento_nuevo(ModelDocumentoGeneral DocumentoNuevo){
        if(encontrar_nombre_documento(DocumentoNuevo.getNombreDocumento()))
    }
    public ModelUsuarioGeneral registrar_usuario_nuevo(ModelUsuarioGeneral UsuarioNuevo) {
        if(encontrar_usuario_por_correo(UsuarioNuevo.getCorreoUsuario()) != null){ //METUSEEXPL: se utiliza el metodo "existe_correo_usuario" para verificar si el CorreoUsuario que esta tratando de ingresar un nuevo usuario ya esta siendo usado por otro CorreoUsuario
            throw new RuntimeException("El CorreoUsuario que intenta ingresar ya está registrado");
        }
        return repositoryUsuarioGeneral.save(UsuarioNuevo);
    }



}
