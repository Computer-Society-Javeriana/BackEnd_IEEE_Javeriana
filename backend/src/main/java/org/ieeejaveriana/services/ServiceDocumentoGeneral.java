package org.ieeejaveriana.services;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.ieeejaveriana.repository.RepositoryDocumentoGeneral;
import org.ieeejaveriana.repository.RepositoryUsuarioGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDocumentoGeneral {

    @Autowired
    private RepositoryDocumentoGeneral repositoryDocumentoGeneral;

    @Autowired
    private RepositoryUsuarioGeneral repositoryUsuarioGeneral;

    //METEXPL: Este es un metodo para verificar que el autor de un documento existe o no dentro de la base de datos
    public String encontrar_autor_documento(String AutorDocumento){
        for (ModelUsuarioGeneral UsuarioTemporal : repositoryUsuarioGeneral.findAll()) {
            if (UsuarioTemporal.().equals(AutorDocumento)) {
                return AutorDocumento;
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

}
