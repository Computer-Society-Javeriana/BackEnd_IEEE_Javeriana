import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getLogros } from "../services/api";

export default function Logros() {
  const [logros, setLogros] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getLogros()
      .then((data) => setLogros(Array.isArray(data) ? data : []))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Logros y Reconocimientos</h1>
        <p>Línea de tiempo de todos los logros obtenidos por la rama IEEE desde su creación</p>
      </div>

      {loading ? (
        <p className="loading">Cargando logros...</p>
      ) : (
        <div className="timeline">
          {logros.map((logro) => (
            <div className="timeline-item" key={logro.idLogro}>
              <div className="card" style={{ cursor: "default" }}>
                <div style={{ display: "flex", gap: "1.25rem", alignItems: "center", flexWrap: "wrap" }}>
                  <img
                    src={logro.imagen || "/src/assets/placeholder-logro.png"}
                    alt={logro.titulo}
                    style={{ width: "130px", height: "95px", objectFit: "cover", borderRadius: "12px", background: "var(--color-surface)" }}
                  />
                  <div className="card-body" style={{ padding: "0.5rem 0", flex: 1 }}>
                    <div style={{ display: "flex", gap: "0.5rem", alignItems: "center", marginBottom: "0.4rem" }}>
                      <span className="badge badge-success">Logro</span>
                      {logro.capitulo && (
                        <span className="badge badge-primary">
                          {logro.capitulo.nombre || logro.capitulo.idCapitulo}
                        </span>
                      )}
                    </div>
                    <h3 style={{ fontSize: "1.15rem", marginBottom: "0.4rem" }}>{logro.titulo}</h3>
                    <p style={{ color: "var(--color-text-secondary)", fontSize: "0.95rem" }}>{logro.descripcion}</p>
                    <Link to={`/logros/${logro.idLogro}`} className="btn btn-outline btn-sm" style={{ marginTop: "0.75rem", display: "inline-block" }}>
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
