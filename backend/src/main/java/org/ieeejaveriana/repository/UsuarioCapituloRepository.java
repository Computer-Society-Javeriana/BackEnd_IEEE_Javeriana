package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.UsuarioCapitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioCapituloRepository extends JpaRepository<UsuarioCapitulo, Long> {

    List<UsuarioCapitulo> findByCapituloIdCapitulo(String idCapitulo);

    List<UsuarioCapitulo> findByCapituloIdCapituloAndRol(String idCapitulo, String rol);

    List<UsuarioCapitulo> findByCapituloIdCapituloAndAdminTrue(String idCapitulo);

    List<UsuarioCapitulo> findByUsuarioIdUsuario(Long idUsuario);
}
