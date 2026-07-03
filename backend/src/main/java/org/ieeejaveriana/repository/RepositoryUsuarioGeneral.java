package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.ModelUsuarioGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUsuarioGeneral extends JpaRepository<ModelUsuarioGeneral, Long> {
}
