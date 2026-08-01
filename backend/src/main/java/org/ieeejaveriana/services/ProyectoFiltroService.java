package org.ieeejaveriana.services;

import org.ieeejaveriana.model.Proyecto;
import org.ieeejaveriana.model.Tema;
import org.ieeejaveriana.model.Usuario;
import org.ieeejaveriana.model.UsuarioCapitulo;
import org.ieeejaveriana.repository.ProyectoRepository;
import org.ieeejaveriana.repository.UsuarioRepository;
import org.ieeejaveriana.repository.UsuarioCapituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoFiltroService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioCapituloRepository usuarioCapituloRepository;

    //METEXPL: metodo para verificar si un autor (colaborador) existe en la base de datos por su nombre
    public Long encontrar_autor_proyecto(String NombreAutor) {
        Optional<Usuario> UsuarioTemporal = usuarioRepository.findByNombre(NombreAutor);
        return UsuarioTemporal.map(Usuario::getIdUsuario).orElse(null);
    }

    //METXPL: metodo encontrar proyecto por nombre
    public List<Proyecto> encontrar_nombre_proyecto(String NombreProyecto) {
        List<Proyecto> ProyectosEncontrados = new ArrayList<>();
        for (Proyecto ProyectoTemporal : proyectoRepository.findAll()) {
            if (ProyectoTemporal.getNombre().equalsIgnoreCase(NombreProyecto)) {
                ProyectosEncontrados.add(ProyectoTemporal);
            }
        }
        return ProyectosEncontrados;
    }

    //METEXPL: metodo para encontrar los proyectos de un colaborador especifico (por nombre)
    public List<Proyecto> encontrar_proyectos_por_autor(String NombreAutor) {
        Long IdAutor = encontrar_autor_proyecto(NombreAutor);
        if (IdAutor == null) {
            return new ArrayList<>();
        }

        List<Proyecto> ProyectosEncontrados = new ArrayList<>();

        for (Proyecto ProyectoTemporal : proyectoRepository.findAll()) {
            if (ProyectoTemporal.getColaboradores() != null) {
                for (Usuario Colaborador : ProyectoTemporal.getColaboradores()) {
                    if (Colaborador.getIdUsuario().equals(IdAutor)) {
                        ProyectosEncontrados.add(ProyectoTemporal);
                        break;
                    }
                }
            }
        }

        return ProyectosEncontrados;
    }

    //METEXPL: metodo para encontrar proyectos por palabras clave en su descripcion o temas
    public List<Proyecto> encontrar_proyecto_por_palabras_clave(String PalabrasClave) {
        List<Proyecto> ProyectosEncontrados = new ArrayList<>();
        String[] PalabrasBuscadas = PalabrasClave.trim().toLowerCase().split("\\s+");

        for (Proyecto ProyectoTemporal : proyectoRepository.findAll()) {
            boolean ProyectoAgregado = false;
            String DescripcionProyecto = ProyectoTemporal.getDescripcion() != null ? ProyectoTemporal.getDescripcion().toLowerCase() : "";
            String[] PalabrasDescripcion = DescripcionProyecto.split("\\s+");

            for (String PalabraBusqueda : PalabrasBuscadas) {
                for (String PalabraDescripcion : PalabrasDescripcion) {
                    if (PalabraBusqueda.equals(PalabraDescripcion)) {
                        ProyectosEncontrados.add(ProyectoTemporal);
                        ProyectoAgregado = true;
                        break;
                    }
                }
                if (ProyectoAgregado) break;

                if (ProyectoTemporal.getTemas() != null) {
                    for (Tema TemaTemporal : ProyectoTemporal.getTemas()) {
                        String[] PalabrasTema = TemaTemporal.getNombreTema().toLowerCase().split("\\s+");
                        for (String PalabraTema : PalabrasTema) {
                            if (PalabraBusqueda.equals(PalabraTema)) {
                                ProyectosEncontrados.add(ProyectoTemporal);
                                ProyectoAgregado = true;
                                break;
                            }
                        }
                        if (ProyectoAgregado) break;
                    }
                }
                if (ProyectoAgregado) break;
            }
        }

        return ProyectosEncontrados;
    }

    //METEXPL: Encuentra todos los proyectos de un capitulo especifico
    public List<Proyecto> encontrar_proyectos_por_capitulo(String NombreCapitulo) {
        List<Proyecto> ProyectosEncontrados = new ArrayList<>();

        for (Proyecto ProyectoTemporal : proyectoRepository.findAll()) {
            boolean Agregado = false;

            if (ProyectoTemporal.getColaboradores() != null) {
                for (Usuario Colaborador : ProyectoTemporal.getColaboradores()) {

                    if (Colaborador.getRoles() != null) {
                        for (UsuarioCapitulo Rol : Colaborador.getRoles()) {
                            if (Rol.getCapitulo().getNombre().equals(NombreCapitulo)) {
                                ProyectosEncontrados.add(ProyectoTemporal);
                                Agregado = true;
                                break;
                            }
                        }
                    }

                    if (Agregado) {
                        break;
                    }
                }
            }
        }

        return ProyectosEncontrados;
    }
}
