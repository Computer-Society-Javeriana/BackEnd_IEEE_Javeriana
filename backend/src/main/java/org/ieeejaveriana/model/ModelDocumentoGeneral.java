package org.ieeejaveriana.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Proyectos")
@NoArgsConstructor
public class ModelDocumentoGeneral {

    @Id
    @Column(name = "id_proyecto")
    public Long IdDocumento;

    @Column(name = "nombre")
    public String NombreDocumento;

    @Column(name = "descripcion")
    public String ResumenDocumento;

    @Column(name = "direccion")
    public String DireccionContenidoDocumento;

    @Column(name = "github")
    public String DireccionCodigoProyecto;

    @Column(name = "imagen")
    public String DireccionPreimagenDocumento;

    @Column(name = "estado")
    public String EstadoDocumento;

    @Column(name = "fecha")
    public LocalDate FechaFinalizacionDocumento;

    @ElementCollection
    @CollectionTable(
            name = "Usuarios_proyectos", //COM: nombre de tabla intermedia que representa la relacion muchos a muchos
            joinColumns = @JoinColumn(name = "id_proyecto") //COM: nombre de la variable de IDs de proyectos en la base de datos
    )
    @Column(name = "id_usuario")
    public List<Long> AutoresDocumentoId;

    @ElementCollection
    @CollectionTable(
            name = "temas_proyetos", //COM: nombre de tabla intermedia que representa la relacion muchos a muchos
            joinColumns = @JoinColumn(name = "id_proyecto") //COM: nombre de la variable de IDs de proyectos en la base de datos
    )
    @Column(name = "id_tema")
    public List<Long> temas;


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

    public List<Long> getTemas() {
        return temas;
    }

    public void setTemas(List<Long> temas) {
        this.temas = temas;
    }
}

