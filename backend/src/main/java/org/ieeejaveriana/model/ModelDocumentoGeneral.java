package org.ieeejaveriana.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
public class ModelDocumentoGeneral {
    @Id
    public Long IdDocumento;

    public String NombreDocumento;

    public String AutorDocumento;

    @ElementCollection
    public List<String> CoAutoresDocumento;

    @ElementCollection
    public List<String> PalabrasClaveDocumento;

    public Date FechaPublicacionDocumento;

    public String ResumenDocumento;

    public String DireccionContenidoDocumento;

    public String getDireccionContenidoDocumento() {
        return DireccionContenidoDocumento;
    }

    public String getResumenDocumento() {
        return ResumenDocumento;
    }

    public Date getFechaPublicacionDocumento() {
        return FechaPublicacionDocumento;
    }

    public List<String> getPalabrasClaveDocumento() {
        return PalabrasClaveDocumento;
    }

    public List<String> getCoAutoresDocumento() {
        return CoAutoresDocumento;
    }

    public String getAutorDocumento() {
        return AutorDocumento;
    }

    public Long getIdDocumento() {
        return IdDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        IdDocumento = idDocumento;
    }

    public void setAutorDocumento(String autorDocumento) {
        AutorDocumento = autorDocumento;
    }

    public void setCoAutoresDocumento(List<String> coAutoresDocumento) {
        CoAutoresDocumento = coAutoresDocumento;
    }

    public void setPalabrasClaveDocumento(List<String> palabrasClaveDocumento) {
        PalabrasClaveDocumento = palabrasClaveDocumento;
    }

    public void setFechaPublicacionDocumento(Date fechaPublicacionDocumento) {
        FechaPublicacionDocumento = fechaPublicacionDocumento;
    }

    public void setResumenDocumento(String resumenDocumento) {
        ResumenDocumento = resumenDocumento;
    }

    public void setDireccionContenidoDocumento(String direccionContenidoDocumento) {
        DireccionContenidoDocumento = direccionContenidoDocumento;
    }

    public String getNombreDocumento() {
        return NombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        NombreDocumento = nombreDocumento;
    }
}
