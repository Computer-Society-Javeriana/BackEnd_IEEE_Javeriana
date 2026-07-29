package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.ModelDocumentoGeneral;
import org.ieeejaveriana.model.ModelTemaGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTemaGeneral extends JpaRepository<ModelTemaGeneral, Long> {
}
