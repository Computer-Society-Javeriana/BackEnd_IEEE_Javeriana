import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getCapitulo, getUsuarios, getEventos, getLogros, getProyectos } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";
import { getAvatarUrl } from "../utils/avatar";
import ProyectosGrafo from "../components/ProyectosGrafo";

export default function CapituloDetalle() {
    const { id } = useParams();
    const [capitulo, setCapitulo] = useState(null);
    const [junta, setJunta] = useState([]);
    const [eventos, setEventos] = useState([]);
    const [logros, setLogros] = useState([]);
    const [proyectos, setProyectos] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        Promise.all([
            getCapitulo(id),
            getUsuarios(id),
            getEventos(),
            getLogros(),
            getProyectos()
        ])
            .then(([capData, usuariosData, eventosData, logrosData, proyectosData]) => {
                setCapitulo(capData);
                const safeUsuarios = Array.isArray(usuariosData) ? usuariosData : [];
                const safeEventos = Array.isArray(eventosData) ? eventosData : [];
                const safeLogros = Array.isArray(logrosData) ? logrosData : [];
                const safeProyectos = Array.isArray(proyectosData) ? proyectosData : [];
                setJunta(safeUsuarios);
                setEventos(safeEventos.filter(e => e.capitulo?.idCapitulo === id));
                setLogros(safeLogros.filter(l => l.capitulo?.idCapitulo === id));

                const juntaIds = safeUsuarios.map(u => u.idUsuario);
                setProyectos(safeProyectos.filter(p =>
                    p.colaboradores?.some(c => juntaIds.includes(c.idUsuario))
                ));
            })
            .catch(() => {})
            .finally(() => setLoading(false));
    }, [id]);

    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) entry.target.classList.add("reveal-active");
            });
        }, { threshold: 0.1 });
        document.querySelectorAll(".reveal-on-scroll").forEach(el => observer.observe(el));
        return () => observer.disconnect();
    }, [junta, eventos, logros, proyectos]);

    if (loading) return <p className="loading">Cargando...</p>;
    if (!capitulo) return <div className="page-container"><p>Capítulo no encontrado</p></div>;

    return (
        <div className="page-container animate-fade-in">
            <div className="detail-header" style={{ borderBottom: "4px solid var(--color-accent)" }}>
                <img
                    src={getChapterLogoUrl(capitulo.logo, capitulo.idCapitulo, capitulo.nombre)}
                    alt={capitulo.nombre}
                    className="detail-banner"
                    onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = getChapterLogoUrl(null, capitulo.idCapitulo, capitulo.nombre);
                    }}
                />
            </div>
            <div className="detail-content">
                <h1 style={{ color: "var(--color-primary-dark)" }}>{capitulo.nombre}</h1>
                <div className="detail-meta">
                    <span className="badge" style={{ backgroundColor: "var(--color-primary)", color: "#FFF" }}>ID: {capitulo.idCapitulo}</span>
                </div>
                <p style={{ fontSize: '1.1rem', marginBottom: '2rem', color: 'var(--color-text-secondary)' }}>
                    Información detallada del capítulo {capitulo.nombre} de IEEE Javeriana.
                </p>

                {/* Junta Directiva */}
                <section className="reveal-on-scroll" style={{ marginBottom: "3rem" }}>
                    <h2 style={{ borderBottom: "2px solid var(--color-border)", paddingBottom: "0.5rem", marginBottom: "1.5rem", color: "var(--color-primary-dark)" }}>Junta Directiva</h2>
                    {junta.length > 0 ? (
                        <div className="flex-team-grid">
                            {junta.map(miembro => (
                                <Link to={`/perfil/${miembro.idUsuario}`} key={miembro.idUsuario} className="card reveal-on-scroll">
                                    <img src={getAvatarUrl(miembro)} alt={miembro.nombre} onError={(e) => { e.target.onerror = null; e.target.src = getAvatarUrl(miembro); }} className="card-image" style={{ height: "auto", aspectRatio: "3/4", objectFit: "cover", borderBottom: "none" }} />
                                    <div className="card-body" style={{ textAlign: "center", padding: "1.5rem" }}>
                                        <h3 style={{ fontSize: "1.1rem", color: "var(--color-primary-dark)" }}>{miembro.nombre}</h3>
                                        <p style={{ fontSize: "0.85rem", color: "var(--color-text-secondary)" }}>{miembro.correo}</p>
                                        {miembro.roles && miembro.roles.length > 0 && (
                                            <div style={{ marginTop: "0.5rem" }}>
                                                {miembro.roles.map(r => (
                                                    <span key={r.idUsuarioCapitulo} className="badge" style={{ display: "inline-block", margin: "2px", fontSize: "0.7rem", padding: "2px 6px", backgroundColor: "var(--color-primary)", color: "#FFF" }}>
                            {r.rol} ({r.capitulo?.idCapitulo})
                          </span>
                                                ))}
                                            </div>
                                        )}
                                    </div>
                                </Link>
                            ))}
                        </div>
                    ) : (
                        <p style={{ color: "var(--color-text-secondary)" }}>No hay miembros de la junta registrados para este capítulo.</p>
                    )}
                </section>

                {/* Red de Colaboración */}
                {(proyectos.length > 0 || junta.length > 0) && (
                    <section className="reveal-on-scroll" style={{ marginBottom: "3rem" }}>
                        <ProyectosGrafo proyectos={proyectos} junta={junta} />
                    </section>
                )}

                {/* Proyectos */}
                <section className="reveal-on-scroll" style={{ marginBottom: "3rem" }}>
                    <h2 style={{ borderBottom: "2px solid var(--color-border)", paddingBottom: "0.5rem", marginBottom: "1.5rem", color: "var(--color-primary-dark)" }}>Proyectos</h2>
                    {proyectos.length > 0 ? (
                        <div className="grid-3">
                            {proyectos.map(proyecto => (
                                <Link to={`/proyectos/${proyecto.idProyecto}`} key={proyecto.idProyecto} className="card reveal-on-scroll">
                                    <img src={proyecto.imagen || "/src/assets/placeholder-proyecto.png"} alt={proyecto.nombre} className="card-image" style={{height: "140px"}} />
                                    <div className="card-body">
                                        <span className="badge" style={{ backgroundColor: "var(--color-accent-light)", color: "var(--color-primary-dark)" }}>{proyecto.estado}</span>
                                        <h3 style={{ fontSize: "1.1rem", color: "var(--color-primary-dark)" }}>{proyecto.nombre}</h3>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    ) : (
                        <p style={{ color: "var(--color-text-secondary)" }}>No hay proyectos asociados.</p>
                    )}
                </section>

                {/* Eventos */}
                <section className="reveal-on-scroll" style={{ marginBottom: "3rem" }}>
                    <h2 style={{ borderBottom: "2px solid var(--color-border)", paddingBottom: "0.5rem", marginBottom: "1.5rem", color: "var(--color-primary-dark)" }}>Eventos</h2>
                    {eventos.length > 0 ? (
                        <div className="grid-3">
                            {eventos.map(evento => (
                                <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card reveal-on-scroll">
                                    <div className="card-body">
                                        <span className="badge" style={{ backgroundColor: "var(--color-accent)", color: "var(--color-primary-dark)" }}>Evento</span>
                                        <h3 style={{ fontSize: "1.1rem", color: "var(--color-primary-dark)" }}>{evento.titulo}</h3>
                                        <p style={{ fontSize: "0.85rem", color: "var(--color-primary)", marginTop: "0.5rem" }}>
                                            📅 {evento.fecha ? new Date(evento.fecha).toLocaleDateString("es-CO") : ""}
                                        </p>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    ) : (
                        <p style={{ color: "var(--color-text-secondary)" }}>No hay eventos programados.</p>
                    )}
                </section>

                {/* Logros */}
                <section className="reveal-on-scroll" style={{ marginBottom: "3rem" }}>
                    <h2 style={{ borderBottom: "2px solid var(--color-border)", paddingBottom: "0.5rem", marginBottom: "1.5rem", color: "var(--color-primary-dark)" }}>Logros</h2>
                    {logros.length > 0 ? (
                        <div className="grid-3">
                            {logros.map(logro => (
                                <Link to={`/logros/${logro.idLogro}`} key={logro.idLogro} className="card reveal-on-scroll">
                                    <img src={logro.imagen || "/src/assets/placeholder-logro.png"} alt={logro.titulo} className="card-image" style={{height: "140px"}} />
                                    <div className="card-body">
                                        <span className="badge" style={{ backgroundColor: "var(--color-primary)", color: "#FFF" }}>Logro</span>
                                        <h3 style={{ fontSize: "1.1rem", color: "var(--color-primary-dark)" }}>{logro.titulo}</h3>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    ) : (
                        <p style={{ color: "var(--color-text-secondary)" }}>No hay logros registrados.</p>
                    )}
                </section>

                <div style={{ marginTop: "2rem", textAlign: "center" }}>
                    <Link to="/capitulos" className="btn btn-outline" style={{ borderColor: "var(--color-primary-dark)", color: "var(--color-primary-dark)" }}>← Volver a capítulos</Link>
                </div>
            </div>
        </div>
    );
}
