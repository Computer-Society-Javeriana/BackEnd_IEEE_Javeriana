import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { getCapitulos, getLogros, getEventos } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function Home() {
  const [capitulos, setCapitulos] = useState([]);
  const [logros, setLogros] = useState([]);
  const [eventos, setEventos] = useState([]);
  const [loadingCapitulos, setLoadingCapitulos] = useState(true);
  const [scrollY, setScrollY] = useState(0);

  useEffect(() => {
    getCapitulos()
      .then((data) => setCapitulos(Array.isArray(data) ? data : []))
      .catch(() => { })
      .finally(() => setLoadingCapitulos(false));
    getLogros().then((data) => setLogros(Array.isArray(data) ? data.slice(0, 3) : [])).catch(() => { });
    getEventos().then((data) => setEventos(Array.isArray(data) ? data.slice(0, 3) : [])).catch(() => { });

    const handleScroll = () => {
      setScrollY(window.scrollY);
    };

    window.addEventListener("scroll", handleScroll, { passive: true });
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Intersection Observer for scroll reveal animations on sections
  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("reveal-active");
          }
        });
      },
      { threshold: 0.12 }
    );

    const elements = document.querySelectorAll(".reveal-on-scroll");
    elements.forEach((el) => observer.observe(el));

    return () => observer.disconnect();
  }, [capitulos, logros, eventos]);

  const heroTranslateY = Math.min(scrollY * 0.35, 140);
  const heroOpacity = Math.max(0, 1 - scrollY / 380);
  const heroScale = Math.max(0.88, 1 - scrollY / 1500);

  return (
    <>
      {/* Hero Section with Dynamic Scroll Parallax & Fade */}
      <section className="hero">
        <div
          className="hero-content"
          style={{
            transform: `translate3d(0, ${heroTranslateY}px, 0) scale(${heroScale})`,
            opacity: heroOpacity,
            willChange: "transform, opacity",
          }}
        >
          <h1>IEEE Rama Estudiantil Javeriana</h1>
          <p>
            Impulsamos la innovación tecnológica y el desarrollo profesional en la
            Pontificia Universidad Javeriana a través de nuestros capítulos y
            semilleros de investigación.
          </p>
          <div className="hero-buttons">
            <Link to="/capitulos" className="btn btn-primary">Explorar Capítulos</Link>
            <Link to="/proyectos" className="btn btn-outline">Ver Proyectos</Link>
          </div>
        </div>
      </section>

      {/* Capítulos (US-001 IEEE) */}
      <section className="section section-tech-bg reveal-on-scroll">
        <div className="page-container">
          <h2 className="section-title">Nuestros Capítulos</h2>
          <p className="section-subtitle">Conoce los semilleros que conforman la rama IEEE</p>
          {loadingCapitulos ? (
            <p className="loading">Cargando capítulos...</p>
          ) : capitulos.length > 0 ? (
            <div className="grid-3">
              {capitulos.map((cap) => (
                <Link to={`/capitulos/${cap.idCapitulo}`} key={cap.idCapitulo} className="card reveal-on-scroll">
                  <img
                    src={getChapterLogoUrl(cap.logo, cap.idCapitulo, cap.nombre)}
                    alt={cap.nombre}
                    className="card-image"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = getChapterLogoUrl(null, cap.idCapitulo, cap.nombre);
                    }}
                  />
                  <div className="card-body">
                    <h3>{cap.nombre}</h3>
                    <p>Capítulo {cap.idCapitulo}</p>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <p style={{ textAlign: "center", color: "var(--color-text-secondary)" }}>
              No hay capítulos disponibles en este momento.
            </p>
          )}
        </div>
      </section>

      {/* Logros Destacados (US-006 IEEE) */}
      <section className="section reveal-on-scroll" style={{ background: "var(--color-bg-card)" }}>
        <div className="page-container">
          <h2 className="section-title">Logros Destacados</h2>
          <p className="section-subtitle">Los logros más relevantes de nuestros semilleros</p>
          <div className="grid-3">
            {logros.map((logro) => (
              <Link to={`/logros/${logro.idLogro}`} key={logro.idLogro} className="card reveal-on-scroll">
                <img
                  src={logro.imagen || "/src/assets/placeholder-logro.png"}
                  alt={logro.titulo}
                  className="card-image"
                />
                <div className="card-body">
                  <span className="badge badge-success">Logro</span>
                  <h3>{logro.titulo}</h3>
                  <p>{logro.descripcion}</p>
                </div>
              </Link>
            ))}
          </div>
          <div style={{ textAlign: "center", marginTop: "2rem" }}>
            <Link to="/logros" className="btn btn-outline">Ver todos los logros</Link>
          </div>
        </div>
      </section>

      {/* Próximos Eventos (US-005 IEEE) */}
      <section className="section reveal-on-scroll">
        <div className="page-container">
          <h2 className="section-title">Próximos Eventos</h2>
          <p className="section-subtitle">No te pierdas lo que viene</p>
          <div className="grid-3">
            {eventos.map((evento) => (
              <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card reveal-on-scroll">
                <div className="card-body">
                  <span className="badge badge-warning">Evento</span>
                  <h3>{evento.titulo}</h3>
                  <p>{evento.descripcion}</p>
                  <p style={{ marginTop: "0.5rem", fontSize: "0.85rem", color: "var(--color-accent)" }}>
                    📍 {evento.lugar || "Por definir"} &bull; 📅 {evento.fecha ? new Date(evento.fecha).toLocaleDateString("es-CO") : ""}
                  </p>
                </div>
              </Link>
            ))}
          </div>
          <div style={{ textAlign: "center", marginTop: "2rem" }}>
            <Link to="/eventos" className="btn btn-outline">Ver todos los eventos</Link>
          </div>
        </div>
      </section>
    </>
  );
}
