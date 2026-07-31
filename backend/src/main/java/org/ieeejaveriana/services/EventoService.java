package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Evento;
import org.ieeejaveriana.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> listarPorCapitulo(String idCapitulo) {
        return eventoRepository.findByCapituloIdCapitulo(idCapitulo);
    }

    public List<Evento> listarProximos(String idCapitulo) {
        return eventoRepository.findByCapituloIdCapituloAndFechaAfter(idCapitulo, LocalDateTime.now());
    }

    public List<Evento> listarPasados(String idCapitulo) {
        return eventoRepository.findByCapituloIdCapituloAndFechaBefore(idCapitulo, LocalDateTime.now());
    }

    public Evento crear(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento actualizar(Long id, Evento datosActualizados) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        if (datosActualizados.getTitulo() != null) evento.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getDescripcion() != null) evento.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getFecha() != null) evento.setFecha(datosActualizados.getFecha());
        if (datosActualizados.getLugar() != null) evento.setLugar(datosActualizados.getLugar());
        if (datosActualizados.getCapitulo() != null) evento.setCapitulo(datosActualizados.getCapitulo());

        return eventoRepository.save(evento);
    }

    public void eliminar(Long id) {
        eventoRepository.deleteById(id);
    }
}
