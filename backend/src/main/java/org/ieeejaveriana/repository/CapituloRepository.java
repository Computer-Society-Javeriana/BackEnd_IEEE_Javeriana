package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, String> {
    List<Capitulo> findAllByOrderByNombreAsc();
}

