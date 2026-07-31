package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Logro;
import org.ieeejaveriana.repository.LogroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogroService {

    @Autowired
    private LogroRepository logroRepository;

    public List<Logro> listarTodos() {
        return logroRepository.findAll();
    }

    public Optional<Logro> buscarPorId(String id) {
        return logroRepository.findById(id);
    }

    public List<Logro> listarPorCapitulo(String idCapitulo) {
        return logroRepository.findByCapituloIdCapitulo(idCapitulo);
    }

    public Logro crear(Logro logro) {
        if (logroRepository.findById(logro.getIdLogro()).isPresent()) {
            throw new RuntimeException("Ya existe un logro con ese ID");
        }
        return logroRepository.save(logro);
    }

    public Logro actualizar(String id, Logro datosActualizados) {
        Logro logro = logroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Logro no encontrado"));

        if (datosActualizados.getTitulo() != null) logro.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getDescripcion() != null) logro.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getImagen() != null) logro.setImagen(datosActualizados.getImagen());
        if (datosActualizados.getContribuyentes() != null) logro.setContribuyentes(datosActualizados.getContribuyentes());

        return logroRepository.save(logro);
    }

    public void eliminar(String id) {
        logroRepository.deleteById(id);
    }
}
