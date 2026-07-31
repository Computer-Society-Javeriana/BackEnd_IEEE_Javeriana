package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByEstado(String estado);

    @Override
    @EntityGraph(attributePaths = {"colaboradores", "temas"})
    List<Proyecto> findAll();
}
