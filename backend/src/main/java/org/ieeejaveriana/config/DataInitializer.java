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
        // 0. Asegurar capítulos iniciales si la BD está limpia
        if (capituloRepository.count() == 0) {
            capituloRepository.save(new Capitulo("IEEE", "IEEE Rama Estudiantil Javeriana", "ieee"));
            capituloRepository.save(new Capitulo("RAS", "Robotics and Automation Society", "ras"));
            capituloRepository.save(new Capitulo("CS", "Computer Society", "cs"));
            capituloRepository.save(new Capitulo("WIE", "Women in Engineering", "wie"));
            capituloRepository.save(new Capitulo("PES", "Power & Energy Society", "pes"));
            capituloRepository.save(new Capitulo("EMBS", "Engineering in Medicine and Biology Society", "embs"));
        }

        // 1. Obtener capítulos existentes desde la base de datos
        Capitulo ieee = capituloRepository.findById("IEEE").orElse(null);
        Capitulo ras = capituloRepository.findById("RAS").orElse(null);

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
                        nuevoUsuario.setBiografia(
                                "Estudiante de Ingeniería Mecatrónica y Presidente del capítulo RAS IEEE Javeriana.");
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

        // Agregar más integrantes con nombres de comida a la Junta Directiva de RAS
        crearMiembroRAS("empanada.perez@javeriana.edu.co", "Empanada Pérez", "Vicepresidente", ras);
        crearMiembroRAS("tamal.garcia@javeriana.edu.co", "Tamal García", "Secretario", ras);
        crearMiembroRAS("arepa.lopez@javeriana.edu.co", "Arepa López", "Webmaster", ras);
        crearMiembroRAS("ajiaco.ramirez@javeriana.edu.co", "Ajiaco Ramírez", "Tesorero", ras);

        // Integrantes de la Junta Directiva General de IEEE
        crearMiembroRAS("diana.suarez@javeriana.edu.co", "Diana Suárez", "Presidenta de Rama", ieee);
        crearMiembroRAS("santiago.gomez@javeriana.edu.co", "Santiago Gómez", "Vicepresidente de Rama", ieee);

        // Limpiar a Sancocho para probar simetría de 5
        usuarioRepository.findByCorreo("sancocho.diaz@javeriana.edu.co").ifPresent(u -> {
            usuarioCapituloRepository.findByUsuarioIdUsuario(u.getIdUsuario())
                    .forEach(usuarioCapituloRepository::delete);
            usuarioRepository.delete(u);
        });

        // 3. Inicializar 10 Proyectos fijos si no hay suficientes
        if (proyectoRepository.count() < 10) {
            java.util.List<Usuario> allUsers = new java.util.ArrayList<>();
            usuarioRepository.findAll().forEach(allUsers::add);

            String[][] projectIdeas = {
                    { "Robot Sumo Autónomo",
                            "Desarrollo de un robot autónomo para competencias de sumo robótico estudiantil." },
                    { "Brazo Robótico Háptico",
                            "Sistema de retroalimentación de fuerza para control preciso en entornos virtuales." },
                    { "Visión Computacional para Drones",
                            "Algoritmos de detección de obstáculos en tiempo real usando YOLOv8." },
                    { "Simulador de Laberintos",
                            "Plataforma virtual en ROS2 para validar algoritmos de resolución de laberintos." },
                    { "Prótesis Biónica de Mano", "Diseño e impresión 3D de una prótesis controlada por señales EMG." },
                    { "Vehículo de Exploración Terrestre",
                            "Rover diseñado para terrenos accidentados con recolección de datos sensoriales." },
                    { "Sistema de Navegación SLAM",
                            "Mapeo y localización simultánea en interiores usando sensores LiDAR 2D." },
                    { "Plataforma de Telepresencia",
                            "Robot móvil controlado por VR para asistencia remota y telemetría médica." },
                    { "Control Difuso para Péndulo",
                            "Implementación de lógica difusa para estabilizar un péndulo invertido sobre un carro." },
                    { "Clasificador Neural de Basura",
                            "Banda transportadora inteligente que separa residuos usando visión por computadora." }
            };

            java.util.Random random = new java.util.Random();

            for (String[] idea : projectIdeas) {
                // Verificar si ya existe un proyecto con este nombre
                boolean exists = ((java.util.List<Proyecto>) proyectoRepository.findAll()).stream()
                        .anyMatch(p -> p.getNombre().equals(idea[0]));
                if (!exists) {
                    Proyecto p = new Proyecto();
                    p.setNombre(idea[0]);
                    p.setDescripcion(idea[1]);
                    p.setEstado(random.nextBoolean() ? "En Desarrollo" : "Completado");
                    p.setFecha(LocalDateTime.now().minusDays(random.nextInt(365)));
                    p.setGithub("https://github.com/IEEE-Javeriana/" + idea[0].toLowerCase().replace(" ", "-"));
                    p.setImagen("/src/assets/placeholder-proyecto.png");

                    // Asignar 1 a 3 colaboradores de los usuarios existentes
                    int numColab = 1 + random.nextInt(3);
                    java.util.Collections.shuffle(allUsers, random);
                    p.setColaboradores(allUsers.stream().limit(numColab).collect(java.util.stream.Collectors.toSet()));

                    proyectoRepository.save(p);
                }
            }
        }

        // 4. Inicializar Evento si no existen eventos
        if (eventoRepository.count() == 0) {
            Evento evento = new Evento();
            evento.setTitulo("Taller de Robótica Autónoma 2026");
            evento.setDescripcion(
                    "Aprende las bases de ROS 2, cinemática directa y simulación física en Gazebo con el equipo RAS.");
            evento.setFecha(LocalDateTime.now().plusDays(10));
            evento.setLugar("Laboratorio de Mecatrónica - Edificio José Gabriel Maldonado");
            evento.setCapitulo(ras);
            eventoRepository.save(evento);
        }

        // 5. Inicializar Logros de la Rama
        if (logroRepository.count() < 6) {
            logroRepository.deleteAll();

            Capitulo cs = capituloRepository.findById("CS").orElse(null);
            Capitulo wie = capituloRepository.findById("WIE").orElse(null);
            Capitulo pes = capituloRepository.findById("PES").orElse(null);
            Capitulo embs = capituloRepository.findById("EMBS").orElse(null);

            Usuario diana = usuarioRepository.findByCorreo("diana.suarez@javeriana.edu.co").orElse(null);
            Usuario santiago = usuarioRepository.findByCorreo("santiago.gomez@javeriana.edu.co").orElse(null);
            Usuario empanada = usuarioRepository.findByCorreo("empanada.perez@javeriana.edu.co").orElse(null);

            // LOG01
            Logro l1 = new Logro();
            l1.setIdLogro("LOG01");
            l1.setCapitulo(ras != null ? ras : ieee);
            l1.setTitulo("1er Lugar Nacional en Competencia de Robótica IEEE");
            l1.setDescripcion("El capítulo RAS IEEE Javeriana obtuvo el primer puesto en la categoría de robots autónomos de exploración en el Torneo Nacional 2025.");
            l1.setImagen("/src/assets/placeholder-logro.png");
            if (migao != null) l1.setContribuyentes(List.of(migao, empanada != null ? empanada : migao));
            logroRepository.save(l1);

            // LOG02
            Logro l2 = new Logro();
            l2.setIdLogro("LOG02");
            l2.setCapitulo(cs != null ? cs : ieee);
            l2.setTitulo("Ganadores del Hackathon Internacional de Software");
            l2.setDescripcion("Reconocimiento de primer lugar por desarrollar la plataforma de ayuda humanitaria y distribución eficiente de recursos.");
            l2.setImagen("/src/assets/placeholder-logro.png");
            if (diana != null) l2.setContribuyentes(List.of(diana));
            logroRepository.save(l2);

            // LOG03
            Logro l3 = new Logro();
            l3.setIdLogro("LOG03");
            l3.setCapitulo(wie != null ? wie : ieee);
            l3.setTitulo("Premio al Ejemplo de Liderazgo Estudiantil en STEM");
            l3.setDescripcion("Galardón recibido por la iniciativa de talleres y mentorías en ciencia y tecnología para jóvenes mujeres de colegios.");
            l3.setImagen("/src/assets/placeholder-logro.png");
            if (diana != null) l3.setContribuyentes(List.of(diana));
            logroRepository.save(l3);

            // LOG04
            Logro l4 = new Logro();
            l4.setIdLogro("LOG04");
            l4.setCapitulo(pes != null ? pes : ieee);
            l4.setTitulo("Mejor Proyecto de Energías Renovables R9 Latam");
            l4.setDescripcion("Premio latinoamericano por el diseño e implementación del microgrid solar experimental en el campus javeriano.");
            l4.setImagen("/src/assets/placeholder-logro.png");
            if (santiago != null) l4.setContribuyentes(List.of(santiago));
            logroRepository.save(l4);

            // LOG05
            Logro l5 = new Logro();
            l5.setIdLogro("LOG05");
            l5.setCapitulo(embs != null ? embs : ieee);
            l5.setTitulo("Publicación Destacada en Conferencia IEEE EMBC");
            l5.setDescripcion("Aceptación y presentación del artículo científico sobre procesamiento de señales ECG con modelos de IA embebidos.");
            l5.setImagen("/src/assets/placeholder-logro.png");
            if (migao != null) l5.setContribuyentes(List.of(migao));
            logroRepository.save(l5);

            // LOG06
            Logro l6 = new Logro();
            l6.setIdLogro("LOG06");
            l6.setCapitulo(ieee);
            l6.setTitulo("Capítulo Estudiantil Ejemplo de la Región 9 IEEE");
            l6.setDescripcion("Premio a la excelencia de la Rama Estudiantil IEEE Javeriana por su alto impacto académico y crecimiento comunitario.");
            l6.setImagen("/src/assets/placeholder-logro.png");
            if (diana != null && santiago != null) l6.setContribuyentes(List.of(diana, santiago));
            logroRepository.save(l6);
        }
    }

    private void crearMiembroRAS(String correo, String nombre, String rol, Capitulo capitulo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseGet(() -> {
                    try {
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setNombre(nombre);
                        nuevoUsuario.setCorreo(correo);
                        nuevoUsuario.setContraseniaHash("123456");
                        nuevoUsuario.setBiografia("Miembro de la junta directiva de RAS - Rol: " + rol);
                        return usuarioService.registrar(nuevoUsuario);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al registrar " + nombre, e);
                    }
                });

        if (usuarioCapituloRepository.findByUsuarioIdUsuario(usuario.getIdUsuario()).isEmpty()) {
            UsuarioCapitulo uc = new UsuarioCapitulo();
            uc.setUsuario(usuario);
            uc.setCapitulo(capitulo);
            uc.setRol(rol);
            uc.setAdmin(false);
            usuarioCapituloRepository.save(uc);
        }
    }
}
