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

import java.util.*;

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
            } else if(DistanciaEntrePalabras <= ((UsuarioTemporal.getNombreUsuario().length())*20)/100){
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
            } else if(DistanciaEntrePalabras <= ((DocumentoTemporal.getNombreDocumento().length())*20)/100){
                return DocumentoTemporal;
            }
        }
        return null;
    }

    //METEXPL: Este es un metodo para encontrar los documentos de un autor especifico (por nombre)
    public List<ModelDocumentoGeneral> encontrar_documentos_por_autor_del_documento(String AutorDocumento){
        Long IdAutor = encontrar_autor_documento(AutorDocumento);

        if(IdAutor == null){
            return new ArrayList<>();
        }

        return repositoryDocumentoGeneral.encontrarDocumentosPorIdAutor(IdAutor);
    }

    //METEXPL: Este es un metodo para encontrar los documentos por palabras clave
    public List<ModelDocumentoGeneral> encontrar_documento_por_palabras_clave (String PalabrasClave) {
        List<ModelDocumentoGeneral> DocumentosEncontrados = new ArrayList<>(); //COM: las palabras claves se deben concatenar tipo "palabra1"+" "+"palabra2"

        String[] PalabrasClaves = PalabrasClave.trim().split("\\s+");
        LevenshteinDistance DistaciaPalabras = new LevenshteinDistance();

        java.util.Map<Long, String> MapaNombresTemas = new java.util.HashMap<>();
        for (ModelTemaGeneral TemaTemporal : repositoryTemaGeneral.findAll()) {
            MapaNombresTemas.put(TemaTemporal.getIdTema(), TemaTemporal.getNombreTema());
        }

        repositoryDocumentoGeneral.findAll().parallelStream().forEach(DocumentoTemporal -> {

            boolean DocumentoYaFueAñadido = false;

            String DescripcionDocumentoTemporal = DocumentoTemporal.getResumenDocumento();
            if (DescripcionDocumentoTemporal != null) {
                String[] PalabrasResumen = DescripcionDocumentoTemporal.trim().split("\\s+");

                for(String PalabraResumen : PalabrasResumen){
                    for(String PalabraClave : PalabrasClaves){
                        int DistanciaEntrePalabras = DistaciaPalabras.apply(PalabraResumen, PalabraClave);

                        if(PalabraClave.equals(PalabraResumen) || DistanciaEntrePalabras <= ((PalabraResumen.length()*20)/100)){
                            DocumentoYaFueAñadido = true;
                            break;
                        }
                    }
                    if(DocumentoYaFueAñadido) break;
                }
            }

            if (!DocumentoYaFueAñadido) {
                List<Long> TemasDocumentoTemporal = DocumentoTemporal.getTemas();
                if (TemasDocumentoTemporal != null) {
                    for (Long IdTema : TemasDocumentoTemporal) {
                        String PalabraTema = MapaNombresTemas.get(IdTema);

                        if (PalabraTema != null) {
                            for(String PalabraClave : PalabrasClaves){
                                int DistanciaEntrePalabras = DistaciaPalabras.apply(PalabraTema, PalabraClave);

                                if(PalabraClave.equals(PalabraTema) || DistanciaEntrePalabras <= ((PalabraTema.length())*20)/100){
                                    DocumentoYaFueAñadido = true;
                                    break;
                                }
                            }
                        }
                        if(DocumentoYaFueAñadido) break;
                    }
                }
            }

            if (DocumentoYaFueAñadido) {
                synchronized(DocumentosEncontrados) {
                    DocumentosEncontrados.add(DocumentoTemporal);
                }
            }
        });

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

        Map<Long, ModelDocumentoGeneral> DocumentosUnicos = new LinkedHashMap<>();
        ModelDocumentoGeneral DocumentoPorNombre = encontrar_nombre_documento(Informacion);

        if (DocumentoPorNombre != null) {
            DocumentosUnicos.put(DocumentoPorNombre.getIdDocumento(), DocumentoPorNombre);
        }

        List<ModelDocumentoGeneral> DocumentosPorAutor = encontrar_documentos_por_autor_del_documento(Informacion);

        if (DocumentosPorAutor != null) {
            for (ModelDocumentoGeneral Documento : DocumentosPorAutor) {
                DocumentosUnicos.putIfAbsent(Documento.getIdDocumento(), Documento);
            }
        }

        List<ModelDocumentoGeneral> DocumentosPorPalabras = encontrar_documento_por_palabras_clave(Informacion);

        if (DocumentosPorPalabras != null) {
            for (ModelDocumentoGeneral Documento : DocumentosPorPalabras) {
                DocumentosUnicos.putIfAbsent(Documento.getIdDocumento(), Documento);
            }
        }

        return new ArrayList<>(DocumentosUnicos.values());
    }

}
