package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDocumentoGeneral extends JpaRepository<ModelDocumentoGeneral, Long> {
}