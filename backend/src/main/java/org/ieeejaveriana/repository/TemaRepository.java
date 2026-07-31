package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    Optional<Tema> findByNombreTema(String nombreTema);
}
