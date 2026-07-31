package org.ieeejaveriana.config;

import org.ieeejaveriana.model.*;
import org.ieeejaveriana.repository.*;
import org.ieeejaveriana.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CapituloRepository capituloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioCapituloRepository usuarioCapituloRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private LogroRepository logroRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // 1. Inicializar / Actualizar Capítulos
        Capitulo ras = capituloRepository.findById("RAS")
                .map(c -> {
                    c.setLogo("/src/assets/logo-ras.png");
                    return capituloRepository.save(c);
                })
                .orElseGet(() -> {
                    Capitulo nuevoCapitulo = new Capitulo();
                    nuevoCapitulo.setIdCapitulo("RAS");
                    nuevoCapitulo.setNombre("Robotics & Automation Society");
                    nuevoCapitulo.setLogo("/src/assets/logo-ras.png");
                    return capituloRepository.save(nuevoCapitulo);
                });

        Capitulo cs = capituloRepository.findById("CS")
                .map(c -> {
                    c.setLogo("/src/assets/logo-cs.png");
                    return capituloRepository.save(c);
                })
                .orElseGet(() -> {
                    Capitulo nuevoCapitulo = new Capitulo();
                    nuevoCapitulo.setIdCapitulo("CS");
                    nuevoCapitulo.setNombre("Computer Society Javeriana");
                    nuevoCapitulo.setLogo("/src/assets/logo-cs.png");
                    return capituloRepository.save(nuevoCapitulo);
                });

        // 2. Inicializar / Actualizar Integrante de Equipo (Junta Directiva)
        Usuario migao = usuarioRepository.findByCorreo("carlos.mendoza@javeriana.edu.co")
                .or(() -> usuarioRepository.findByCorreo("migao.mendoza@javeriana.edu.co"))
                .map(u -> {
                    u.setNombre("Migao Mendoza");
                    u.setCorreo("migao.mendoza@javeriana.edu.co");
                    u.setGithub("https://github.com/migaomendoza");
                    u.setLinkedin("https://linkedin.com/in/migaomendoza");
                    return usuarioRepository.save(u);
                })
                .orElseGet(() -> {
                    try {
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre("Migao Mendoza");
                        nuevoUsuario.setCorreo("migao.mendoza@javeriana.edu.co");
                        nuevoUsuario.setContraseniaHash("111111");
                        nuevoUsuario.setGithub("https://github.com/migaomendoza");
                        nuevoUsuario.setLinkedin("https://linkedin.com/in/migaomendoza");
                        nuevoUsuario.setBiografia("Estudiante de Ingeniería Mecatrónica y Presidente del capítulo RAS IEEE Javeriana.");
                        return usuarioService.registrar(nuevoUsuario);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al registrar usuario inicial", e);
                    }
                });

        // Vincular a Migao Mendoza como integrante del capítulo RAS
        if (usuarioCapituloRepository.findByUsuarioIdUsuario(migao.getIdUsuario()).isEmpty()) {
            UsuarioCapitulo uc = new UsuarioCapitulo();
            uc.setUsuario(migao);
            uc.setCapitulo(ras);
            uc.setRol("Presidente RAS");
            uc.setAdmin(true);
            usuarioCapituloRepository.save(uc);
        }

        // 3. Inicializar Proyecto si no existen proyectos
        if (proyectoRepository.count() == 0) {
            Proyecto proyecto = new Proyecto();
            proyecto.setNombre("LeRobot Arm Simulation");
            proyecto.setDescripcion("Simulación física en ROS 2 y Gazebo del brazo robótico articulado LeRobot para aplicaciones de teleoperación.");
            proyecto.setEstado("En Desarrollo");
            proyecto.setFecha(LocalDateTime.now());
            proyecto.setGithub("https://github.com/IEEE-Javeriana/lerobot-ros2");
            proyecto.setImagen("/src/assets/placeholder-proyecto.png");
            proyecto.setColaboradores(List.of(migao));
            proyectoRepository.save(proyecto);
        }

        // 4. Inicializar Evento si no existen eventos
        if (eventoRepository.count() == 0) {
            Evento evento = new Evento();
            evento.setTitulo("Taller de Robótica Autónoma 2026");
            evento.setDescripcion("Aprende las bases de ROS 2, cinemática directa y simulación física en Gazebo con el equipo RAS.");
            evento.setFecha(LocalDateTime.now().plusDays(10));
            evento.setLugar("Laboratorio de Mecatrónica - Edificio José Gabriel Maldonado");
            evento.setCapitulo(ras);
            eventoRepository.save(evento);
        }

        // 5. Inicializar Logro si no existen logros
        if (logroRepository.count() == 0) {
            Logro logro = new Logro();
            logro.setIdLogro("LOG01");
            logro.setCapitulo(ras);
            logro.setTitulo("1er Lugar Nacional en Competencia de Robótica IEEE");
            logro.setDescripcion("El capítulo RAS IEEE Javeriana obtuvo el primer puesto en la categoría de robots autónomos de exploración.");
            logro.setImagen("/src/assets/placeholder-logro.png");
            logro.setContribuyentes(List.of(migao));
            logroRepository.save(logro);
        }
    }
}
