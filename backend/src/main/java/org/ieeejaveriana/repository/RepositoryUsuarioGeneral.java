package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUsuarioGeneral extends JpaRepository<ModelUsuarioGeneral, Long> {
}
