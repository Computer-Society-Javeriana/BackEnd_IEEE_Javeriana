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

    public String AutorDocumento;

    @ElementCollection
    public List<String> CoAutoresDocumento;

    @ElementCollection
    public List<String> PalabrasClaveDocumento;

    public Date FechaPublicacionDocumento;

    public String ResumenDocumento;

    public String DireccionContenidoDocumento;

}
