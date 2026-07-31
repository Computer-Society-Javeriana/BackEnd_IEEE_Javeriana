import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getLogros } from "../services/api";

export default function Logros() {
  const [logros, setLogros] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getLogros()
      .then(setLogros)
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
                <div style={{ display: "flex", gap: "1rem", alignItems: "flex-start" }}>
                  <img
                    src={logro.imagen || "/src/assets/placeholder-logro.png"}
                    alt={logro.titulo}
                    style={{ width: "120px", height: "90px", objectFit: "cover", borderRadius: "var(--radius-sm)", background: "var(--color-surface)" }}
                  />
                  <div className="card-body" style={{ padding: "0.75rem 0" }}>
                    <span className="badge badge-success">Logro</span>
                    <h3>{logro.titulo}</h3>
                    <p>{logro.descripcion}</p>
                    <Link to={`/logros/${logro.idLogro}`} className="btn btn-outline btn-sm" style={{ marginTop: "0.75rem" }}>
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
