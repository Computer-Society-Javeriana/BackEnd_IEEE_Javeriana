package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Proyecto;
import org.ieeejaveriana.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> buscarPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public List<Proyecto> buscarPorEstado(String estado) {
        return proyectoRepository.findByEstado(estado);
    }

    public Proyecto crear(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizar(Long id, Proyecto datosActualizados) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        if (datosActualizados.getNombre() != null) proyecto.setNombre(datosActualizados.getNombre());
        if (datosActualizados.getDescripcion() != null) proyecto.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getEstado() != null) proyecto.setEstado(datosActualizados.getEstado());
        if (datosActualizados.getFecha() != null) proyecto.setFecha(datosActualizados.getFecha());
        if (datosActualizados.getImagen() != null) proyecto.setImagen(datosActualizados.getImagen());
        if (datosActualizados.getGithub() != null) proyecto.setGithub(datosActualizados.getGithub());
        if (datosActualizados.getColaboradores() != null) proyecto.setColaboradores(datosActualizados.getColaboradores());
        if (datosActualizados.getTemas() != null) proyecto.setTemas(datosActualizados.getTemas());

        return proyectoRepository.save(proyecto);
    }

    public void eliminar(Long id) {
        proyectoRepository.deleteById(id);
    }
}
