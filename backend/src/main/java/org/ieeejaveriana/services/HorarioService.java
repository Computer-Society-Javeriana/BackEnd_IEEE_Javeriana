package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Horario;
import org.ieeejaveriana.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }

    public List<Horario> listarPorCapitulo(String idCapitulo) {
        return horarioRepository.findByCapituloIdCapitulo(idCapitulo);
    }

    public Optional<Horario> buscarPorId(Long id) {
        return horarioRepository.findById(id);
    }

    public Horario crear(Horario horario) {
        return horarioRepository.save(horario);
    }

    public Horario actualizar(Long id, Horario datosActualizados) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        if (datosActualizados.getTemaClase() != null) horario.setTemaClase(datosActualizados.getTemaClase());
        if (datosActualizados.getProfesor() != null) horario.setProfesor(datosActualizados.getProfesor());
        if (datosActualizados.getLugar() != null) horario.setLugar(datosActualizados.getLugar());
        if (datosActualizados.getRecomendaciones() != null) horario.setRecomendaciones(datosActualizados.getRecomendaciones());
        if (datosActualizados.getFechaHora() != null) horario.setFechaHora(datosActualizados.getFechaHora());

        return horarioRepository.save(horario);
    }

    public void eliminar(Long id) {
        horarioRepository.deleteById(id);
    }
}
