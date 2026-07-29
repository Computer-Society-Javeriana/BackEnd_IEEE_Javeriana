package org.ieeejaveriana.services;

import jakarta.persistence.Id;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.h2.engine.Mode;
import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelTemaGeneral;
import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.ieeejaveriana.repository.RepositoryDocumentoGeneral;
import org.ieeejaveriana.repository.RepositoryTemaGeneral;
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

    @Autowired
    private RepositoryTemaGeneral repositoryTemaGeneral;

    //METEXPL: Este es un metodo para verificar que el autor de un documento existe o no dentro de la base de datos
    public Long encontrar_autor_documento(String AutorDocumento){
        LevenshteinDistance DistaciaPalabras = new LevenshteinDistance();
        for (ModelUsuarioGeneral UsuarioTemporal : repositoryUsuarioGeneral.findAll()) {
            int DistanciaEntrePalabras = DistaciaPalabras.apply(UsuarioTemporal.getNombreUsuario(), AutorDocumento);

            if (UsuarioTemporal.getNombreUsuario().equals(AutorDocumento)) {
                return UsuarioTemporal.getIdUsuario();
            } else if(DistanciaEntrePalabras <= UsuarioTemporal.getNombreUsuario().length()){
                return UsuarioTemporal.getIdUsuario();
            }

        }
        return null;
    }

    //METEXPL: Este es un metodo para verificar que el nombre de un documento existe o no dentro de la base de datos
    public ModelDocumentoGeneral encontrar_nombre_documento(String NombreDocumento){
        LevenshteinDistance DistaciaPalabras = new LevenshteinDistance();

        for (ModelDocumentoGeneral DocumentoTemporal : repositoryDocumentoGeneral.findAll()) {
            int DistanciaEntrePalabras = DistaciaPalabras.apply(DocumentoTemporal.getNombreDocumento(), NombreDocumento);
            if (DocumentoTemporal.getNombreDocumento().equals(NombreDocumento)) {
                return DocumentoTemporal;
            } else if(DistanciaEntrePalabras <= DocumentoTemporal.getNombreDocumento().length()){
                return DocumentoTemporal;
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
            List<Long> TemasDocumentoTemporal = DocumentoTemporal.getTemas();
            String[] PalabrasResumen = DescripcionDocumentoTemporal.trim().split("\\s+");
            String[] PalabrasClaves = PalabrasClave.trim().split("\\s+");
            LevenshteinDistance DistaciaPalabras = new LevenshteinDistance();

            for(String PalabraResumen : PalabrasResumen){
                for(String PalabraClave : PalabrasClaves){
                    int DistanciaEntrePalabras = DistaciaPalabras.apply(PalabraResumen, PalabraClave);
                    if(PalabraClave.equals(PalabraResumen)){
                        DocumentosEncontrados.add(DocumentoTemporal);
                    } else if(DistanciaEntrePalabras <= PalabraResumen.length()){
                        DocumentosEncontrados.add(DocumentoTemporal);
                    }
                }
            }

            List<String> NombreTemas = new ArrayList<>();

            for (Long IdTema : TemasDocumentoTemporal) {
                repositoryTemaGeneral.findById(IdTema).ifPresent(NuevoTema -> {NombreTemas.add(NuevoTema.getNombreTema());}); //COM: busca por Id, si lo encuentra busca el nombre del tema y lo guarda en la lista de nombres de temas
            }

            for(String PalabraTema : NombreTemas){
                for(String PalabraClave : PalabrasClaves){
                    if(PalabraClave.equals(PalabraTema)){
                        DocumentosEncontrados.add(DocumentoTemporal);
                    }
                }
            }

        }

        return DocumentosEncontrados;
    }

    //METXPL: Funcion que sirve para guardar documentos en la base de datos
    public ModelDocumentoGeneral guardar_documento_nuevo(ModelDocumentoGeneral DocumentoNuevo){
        if(encontrar_nombre_documento(DocumentoNuevo.getNombreDocumento()) != null){
            throw new RuntimeException("El Nombre de Documento que intenta ingresar ya está registrado"); //METUSEEXPL: se utiliza el metodo "existe_nombre_documento para verificar si el NombreDocumento que esta tratando de ingresar un nuevo usuario ya esta siendo usado por otro CorreoUsuario
        }
        return repositoryDocumentoGeneral.save(DocumentoNuevo);
    }

    //METXPL: Funcion para buscar documentos en el repositorio a partir del nombre, palabras clave o autor
    public List<ModelDocumentoGeneral> buscar_documento_existente(String Informacion){
        List<Long> IdsDocumentos = new ArrayList<>();
        List<ModelDocumentoGeneral> DocumentosEncontradosBusqueda = new ArrayList<>();

        try{
            ModelDocumentoGeneral DocumentoEncontrado = encontrar_nombre_documento(Informacion); //COM: encuentra el documento si la informacion proporcionada se trata de ello
            Long IdDocumentoEncontrado = DocumentoEncontrado.getIdDocumento();
            IdsDocumentos.add(IdDocumentoEncontrado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<ModelDocumentoGeneral> DocumentosEncontrados = new ArrayList<>();

            DocumentosEncontrados.addAll(encontrar_documentos_por_autor_del_documento(Informacion)); //COM: encuentra los documentos de un autor si su nombre (o nombres) son la informacion

            DocumentosEncontrados.addAll(encontrar_documento_por_palabras_clave(Informacion)); //COM: añade documentos a partir de palabras clave si la informacion son palabras clave

            for(ModelDocumentoGeneral DocumentoENcontrado : DocumentosEncontrados){ //COM: trata de obtener los Ids de los dcumentos encontrados
                Long IdDocumentoEncontrado = DocumentoENcontrado.getIdDocumento();
                if(!IdsDocumentos.contains(IdDocumentoEncontrado)){ //COM: si por casualidad uno de los Ids de los documentos encontrados es el mismo del Id encontrado por el título, pues no lo añade, sino, lo añade a la lista de Ids
                    IdsDocumentos.add(IdDocumentoEncontrado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for(Long IdDocumentoEncontrado : IdsDocumentos){
            repositoryDocumentoGeneral.findById(IdDocumentoEncontrado).ifPresent(DocumentosEncontradosBusqueda::add); //COM: busca en la db si se encuentra el Id del documento, si lo encuentra toma el objeto Documento y lo añade a la lista de documentos
        }

        return DocumentosEncontradosBusqueda;
    }

}
