package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Galeria;
import org.ieeejaveriana.repository.GaleriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GaleriaService {

    @Autowired
    private GaleriaRepository galeriaRepository;

    public List<Galeria> listarTodos() {
        return galeriaRepository.findAll();
    }

    public List<Galeria> listarPorCapitulo(String idCapitulo) {
        return galeriaRepository.findByCapituloIdCapitulo(idCapitulo);
    }

    public Optional<Galeria> buscarPorId(Long id) {
        return galeriaRepository.findById(id);
    }

    public Galeria crear(Galeria foto) {
        return galeriaRepository.save(foto);
    }

    public Galeria actualizar(Long id, Galeria datosActualizados) {
        Galeria foto = galeriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));

        if (datosActualizados.getTitulo() != null) foto.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getUrlImagen() != null) foto.setUrlImagen(datosActualizados.getUrlImagen());
        if (datosActualizados.getPeriodo() != null) foto.setPeriodo(datosActualizados.getPeriodo());

        return galeriaRepository.save(foto);
    }

    public void eliminar(Long id) {
        galeriaRepository.deleteById(id);
    }
}
