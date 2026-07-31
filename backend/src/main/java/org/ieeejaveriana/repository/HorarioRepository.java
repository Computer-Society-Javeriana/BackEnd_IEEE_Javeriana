package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByCapituloIdCapitulo(String idCapitulo);
}
