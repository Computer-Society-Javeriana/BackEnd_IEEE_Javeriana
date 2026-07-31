package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Tema;
import org.ieeejaveriana.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    public List<Tema> listarTodos() {
        return temaRepository.findAll();
    }

    public Optional<Tema> buscarPorId(Long id) {
        return temaRepository.findById(id);
    }

    public Tema crear(Tema tema) {
        if (temaRepository.findByNombreTema(tema.getNombreTema()).isPresent()) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        return temaRepository.save(tema);
    }

    public Tema actualizar(Long id, Tema datosActualizados) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (datosActualizados.getNombreTema() != null) tema.setNombreTema(datosActualizados.getNombreTema());

        return temaRepository.save(tema);
    }

    public void eliminar(Long id) {
        temaRepository.deleteById(id);
    }
}
