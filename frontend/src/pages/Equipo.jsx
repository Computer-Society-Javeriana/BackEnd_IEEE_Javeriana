import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUsuarios } from "../services/api";
import { getAvatarUrl } from "../utils/avatar";

export default function Equipo() {
  const [miembros, setMiembros] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getUsuarios()
      .then(setMiembros)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Junta Directiva</h1>
        <p>Conoce a las personas que dirigen actualmente el semillero</p>
      </div>

      {loading ? (
        <p className="loading">Cargando equipo...</p>
      ) : miembros.length === 0 ? (
        <div style={{ textAlign: "center", padding: "4rem 1.5rem", background: "var(--color-bg-card)", borderRadius: "var(--radius-lg)", border: "1px solid var(--color-border)" }}>
          <h3 style={{ color: "var(--ieee-blue-dark)", marginBottom: "0.5rem" }}>Aún no hay miembros en la Junta Directiva</h3>
          <p style={{ color: "var(--color-text-secondary)", maxWidth: "500px", margin: "0 auto" }}>
            Los usuarios registrados deben tener un capítulo y rol asignado para figurar en el equipo directivo.
          </p>
        </div>
      ) : (
        <div className="grid-3">
          {miembros.map((miembro) => (
            <Link to={`/perfil/${miembro.idUsuario}`} key={miembro.idUsuario} className="card">
              <div className="card-body" style={{ textAlign: "center", padding: "2rem 1.5rem" }}>
                <img
                  src={getAvatarUrl(miembro)}
                  alt={miembro.nombre}
                  onError={(e) => {
                    e.target.onerror = null;
                    e.target.src = getAvatarUrl(miembro);
                  }}
                  style={{
                    width: "100px",
                    height: "100px",
                    borderRadius: "50%",
                    objectFit: "cover",
                    margin: "0 auto 1rem",
                    border: "3px solid var(--color-primary)",
                    background: "var(--color-surface)",
                  }}
                />
                <h3>{miembro.nombre}</h3>
                <p style={{ color: "var(--color-accent)", fontSize: "0.85rem", marginBottom: "0.5rem" }}>
                  Miembro IEEE
                </p>
                <p>{miembro.biografia || "Sin descripción"}</p>
                <span className="btn btn-outline btn-sm" style={{ marginTop: "1rem" }}>
                  Ver detalles →
                </span>
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
