package org.ieeejaveriana.services;

import org.ieeejaveriana.model.LearningPath;
import org.ieeejaveriana.repository.LearningPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningPathService {

    @Autowired
    private LearningPathRepository learningPathRepository;

    public List<LearningPath> listarTodos() {
        return learningPathRepository.findAll();
    }

    public List<LearningPath> listarPorCapitulo(String idCapitulo) {
        return learningPathRepository.findByCapituloIdCapitulo(idCapitulo);
    }

    public Optional<LearningPath> buscarPorId(Long id) {
        return learningPathRepository.findById(id);
    }

    public LearningPath crear(LearningPath path) {
        return learningPathRepository.save(path);
    }

    public LearningPath actualizar(Long id, LearningPath datosActualizados) {
        LearningPath path = learningPathRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learning path no encontrado"));

        if (datosActualizados.getTitulo() != null) path.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getDescripcion() != null) path.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getUrlRecurso() != null) path.setUrlRecurso(datosActualizados.getUrlRecurso());
        if (datosActualizados.getNivel() != null) path.setNivel(datosActualizados.getNivel());

        return learningPathRepository.save(path);
    }

    public void eliminar(Long id) {
        learningPathRepository.deleteById(id);
    }
}
