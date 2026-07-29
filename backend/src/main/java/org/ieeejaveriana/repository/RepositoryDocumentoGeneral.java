package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryDocumentoGeneral extends JpaRepository<ModelDocumentoGeneral, Long> {
    @Query("SELECT d FROM ModelDocumentoGeneral d JOIN d.AutoresDocumentoId autorId WHERE autorId = :IdAutor")
    List<ModelDocumentoGeneral> encontrarDocumentosPorIdAutor(@Param("IdAutor") Long IdAutor);

}