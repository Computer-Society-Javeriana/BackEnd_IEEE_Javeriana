import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getLogros } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function Logros() {
  const [logros, setLogros] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getLogros()
        .then((data) => setLogros(Array.isArray(data) ? data : []))
        .catch(() => {})
        .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) entry.target.classList.add("reveal-active");
      });
    }, { threshold: 0.1 });
    document.querySelectorAll(".reveal-on-scroll").forEach(el => observer.observe(el));
    return () => observer.disconnect();
  }, [logros]);

  return (
      <div className="page-container animate-fade-in">
        <div className="page-header">
          <h1 style={{ color: "var(--color-primary-dark)" }}>Logros y Reconocimientos</h1>
          <p>Línea de tiempo de todos los logros obtenidos por la rama IEEE desde su creación</p>
        </div>

        {loading ? (
            <p className="loading">Cargando logros...</p>
        ) : (
            <div className="timeline">
              {logros.map((logro) => (
                  <div className="timeline-item reveal-on-scroll" key={logro.idLogro}>
                    <div className="card" style={{ cursor: "default" }}>
                      <div style={{ display: "flex", gap: "1.25rem", alignItems: "center", flexWrap: "wrap" }}>
                        <img
                            src={logro.imagen && !logro.imagen.startsWith("/src/") ? logro.imagen : getChapterLogoUrl(null, logro.idLogro, logro.titulo)}
                            alt={logro.titulo}
                            style={{ width: "130px", height: "95px", objectFit: "cover", borderRadius: "8px", background: "var(--color-bg-card)" }}
                        />
                        <div className="card-body" style={{ padding: "0.5rem 0", flex: 1 }}>
                          <div style={{ display: "flex", gap: "0.5rem", alignItems: "center", marginBottom: "0.4rem" }}>
                            <span className="badge" style={{ backgroundColor: "var(--color-primary)", color: "#FFF" }}>Logro</span>
                            {logro.capitulo && (
                                <span className="badge" style={{ backgroundColor: "var(--color-accent-light)", color: "var(--color-primary-dark)" }}>
                          {logro.capitulo.nombre || logro.capitulo.idCapitulo}
                        </span>
                            )}
                          </div>
                          <h3 style={{ fontSize: "1.15rem", marginBottom: "0.4rem", color: "var(--color-primary-dark)" }}>{logro.titulo}</h3>
                          <p style={{ color: "var(--color-text-secondary)", fontSize: "0.95rem" }}>{logro.descripcion}</p>
                          <Link to={`/logros/${logro.idLogro}`} className="btn btn-outline btn-sm" style={{ marginTop: "0.75rem", display: "inline-block", borderColor: "var(--color-primary)", color: "var(--color-primary)" }}>
                            Ver detalles →
                          </Link>
                        </div>
                      </div>
                    </div>
                  </div>
              ))}
            </div>
        )}
      </div>
  );
}