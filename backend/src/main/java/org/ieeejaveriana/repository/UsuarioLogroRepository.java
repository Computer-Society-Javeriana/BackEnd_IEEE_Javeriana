package org.ieeejaveriana.repository;

import org.ieeejaveriana.model.UsuarioLogro;
import org.ieeejaveriana.model.UsuarioLogroId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioLogroRepository extends JpaRepository<UsuarioLogro, UsuarioLogroId> {

    List<UsuarioLogro> findByUsuarioIdUsuario(Long idUsuario);

    List<UsuarioLogro> findByLogroIdLogro(String idLogro);
}
