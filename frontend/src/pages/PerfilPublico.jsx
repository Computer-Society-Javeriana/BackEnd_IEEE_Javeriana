import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getPerfilPublico } from "../services/api";
import { getAvatarUrl } from "../utils/avatar";

export default function PerfilPublico() {
  const { id } = useParams();
  const [usuario, setUsuario] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getPerfilPublico(id)
      .then(setUsuario)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p className="loading">Cargando...</p>;
  if (!usuario) return <div className="page-container"><p>Usuario no encontrado</p></div>;

  return (
    <div className="page-container">
      <div style={{ maxWidth: "700px", margin: "0 auto" }}>
        <div style={{ textAlign: "center", marginBottom: "2rem" }}>
          <img
            src={getAvatarUrl(usuario)}
            alt={usuario.nombre}
            onError={(e) => {
              e.target.onerror = null;
              e.target.src = getAvatarUrl(usuario);
            }}
            style={{
              width: "140px",
              height: "140px",
              borderRadius: "50%",
              objectFit: "cover",
              border: "4px solid var(--color-primary)",
              margin: "0 auto 1.5rem",
              background: "var(--color-surface)",
            }}
          />
          <h1 style={{ fontSize: "2rem" }}>{usuario.nombre}</h1>
          <p style={{ color: "var(--color-text-secondary)", marginTop: "0.5rem" }}>{usuario.correo}</p>
        </div>

        {usuario.biografia && (
          <div style={{ marginBottom: "2rem" }}>
            <h2 style={{ fontSize: "1.2rem", marginBottom: "0.75rem" }}>Biografía</h2>
            <p style={{ lineHeight: "1.8" }}>{usuario.biografia}</p>
          </div>
        )}

        <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", justifyContent: "center" }}>
          {usuario.github && (
            <a href={usuario.github} target="_blank" rel="noreferrer" className="btn btn-outline btn-sm">
              🔗 GitHub
            </a>
          )}
          {usuario.linkedin && (
            <a href={usuario.linkedin} target="_blank" rel="noreferrer" className="btn btn-outline btn-sm">
              🔗 LinkedIn
            </a>
          )}
        </div>

        <div style={{ textAlign: "center", marginTop: "2rem" }}>
          <Link to="/equipo" className="btn btn-outline">← Volver al equipo</Link>
        </div>
      </div>
    </div>
  );
}
