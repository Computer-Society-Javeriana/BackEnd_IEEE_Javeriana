package org.ieeejaveriana.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
public class ModelDocumentoGeneral {
    @Id
    public Long IdDocumento;

    public String NombreDocumento;

    public String ResumenDocumento;

    public String DireccionContenidoDocumento;

    public String DireccionCodigoProyecto;

    public String DireccionPreimagenDocumento;

    public String EstadoDocumento;

    public List<Long> AutoresDocumentoId;

    public LocalDate FechaFinalizacionDocumento;

    public Long getIdDocumento() {
        return IdDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        IdDocumento = idDocumento;
    }

    public String getNombreDocumento() {
        return NombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        NombreDocumento = nombreDocumento;
    }

    public String getResumenDocumento() {
        return ResumenDocumento;
    }

    public void setResumenDocumento(String resumenDocumento) {
        ResumenDocumento = resumenDocumento;
    }

    public String getDireccionContenidoDocumento() {
        return DireccionContenidoDocumento;
    }

    public void setDireccionContenidoDocumento(String direccionContenidoDocumento) {
        DireccionContenidoDocumento = direccionContenidoDocumento;
    }

    public String getDireccionCodigoProyecto() {
        return DireccionCodigoProyecto;
    }

    public void setDireccionCodigoProyecto(String direccionCodigoProyecto) {
        DireccionCodigoProyecto = direccionCodigoProyecto;
    }

    public String getDireccionPreimagenDocumento() {
        return DireccionPreimagenDocumento;
    }

    public void setDireccionPreimagenDocumento(String direccionPreimagenDocumento) {
        DireccionPreimagenDocumento = direccionPreimagenDocumento;
    }

    public String getEstadoDocumento() {
        return EstadoDocumento;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        EstadoDocumento = estadoDocumento;
    }

    public List<Long> getAutoresDocumentoId() {
        return AutoresDocumentoId;
    }

    public void setAutoresDocumentoId(List<Long> autoresDocumentoId) {
        AutoresDocumentoId = autoresDocumentoId;
    }

    public LocalDate getFechaFinalizacionDocumento() {
        return FechaFinalizacionDocumento;
    }

    public void setFechaFinalizacionDocumento(LocalDate fechaFinalizacionDocumento) {
        FechaFinalizacionDocumento = fechaFinalizacionDocumento;
    }
}

