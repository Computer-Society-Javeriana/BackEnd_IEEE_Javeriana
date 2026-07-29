package org.ieeejaveriana.services;

import org.ieeejaveriana.model.ModelTemaGeneral;
import org.ieeejaveriana.repository.RepositoryTemaGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTemaGeneral {

    @Autowired
    RepositoryTemaGeneral repositoryTemaGeneral;

    //METXPL: metodo para encontrar el Id de un Tema a partir de su Nombre
    public Long encontrar_tema_por_nombre(String NombreTema){
        for(ModelTemaGeneral TemaEncontrado : repositoryTemaGeneral.findAll()){
            if(TemaEncontrado.getNombreTema().equals(NombreTema)){
                return TemaEncontrado.getIdTema();
            }
        }
        return null;
    }
}
