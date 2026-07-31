package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Capitulo;
import org.ieeejaveriana.repository.CapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapituloService {

    @Autowired
    private CapituloRepository capituloRepository;

    public List<Capitulo> listarTodos() {
        return capituloRepository.findAllByOrderByNombreAsc();
    }

    public Optional<Capitulo> buscarPorId(String id) {
        return capituloRepository.findById(id);
    }

    public Capitulo crear(Capitulo capitulo) {
        if (capituloRepository.findById(capitulo.getIdCapitulo()).isPresent()) {
            throw new RuntimeException("Ya existe un capítulo con ese ID");
        }
        return capituloRepository.save(capitulo);
    }

    public Capitulo actualizar(String id, Capitulo datosActualizados) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capítulo no encontrado"));

        if (datosActualizados.getNombre() != null) capitulo.setNombre(datosActualizados.getNombre());
        if (datosActualizados.getLogo() != null) capitulo.setLogo(datosActualizados.getLogo());

        return capituloRepository.save(capitulo);
    }

    public void eliminar(String id) {
        capituloRepository.deleteById(id);
    }
}
