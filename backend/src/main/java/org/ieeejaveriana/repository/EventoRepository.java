package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByCapituloIdCapitulo(String idCapitulo);

    List<Evento> findByCapituloIdCapituloAndFechaAfter(String idCapitulo, LocalDateTime fecha);

    List<Evento> findByCapituloIdCapituloAndFechaBefore(String idCapitulo, LocalDateTime fecha);
}
