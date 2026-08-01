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

    //METEXPL: metodo para verificar si el nombre de un proyecto existe en la base de datos
    public String encontrar_nombre_proyecto(String NombreProyecto) {
        for (Proyecto ProyectoTemporal : proyectoRepository.findAll()) {
            if (ProyectoTemporal.getNombre().equalsIgnoreCase(NombreProyecto)) {
                return ProyectoTemporal.getNombre();
            }
        }
        return null;
    }

    //METEXPL: metodo para guardar un proyecto nuevo validando que el nombre no exista
    public Proyecto crear(Proyecto ProyectoNuevo) {
        if (encontrar_nombre_proyecto(ProyectoNuevo.getNombre()) != null) {
            throw new RuntimeException("El nombre del proyecto ya está en uso");
        }
        return proyectoRepository.save(ProyectoNuevo);
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
