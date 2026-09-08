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
      <div className="page-container animate-fade-in">
        <div style={{ maxWidth: "700px", margin: "0 auto", background: "var(--color-bg-main)", padding: "3rem", borderRadius: "12px", boxShadow: "0 10px 30px rgba(37, 58, 122, 0.08)", border: "1px solid var(--color-border)" }}>
          <div style={{ textAlign: "center", marginBottom: "2rem" }}>
            <img
                src={getAvatarUrl(usuario)}
                alt={usuario.nombre}
                onError={(e) => { e.target.onerror = null; e.target.src = getAvatarUrl(usuario); }}
                style={{ width: "140px", height: "140px", borderRadius: "50%", objectFit: "cover", border: "4px solid var(--color-accent)", margin: "0 auto 1.5rem", background: "var(--color-bg-card)", boxShadow: "0 4px 10px rgba(0,0,0,0.1)" }}
            />
            <h1 style={{ fontSize: "2rem", color: "var(--color-primary-dark)" }}>{usuario.nombre}</h1>
            <p style={{ color: "var(--color-primary)", marginTop: "0.5rem", fontWeight: "500" }}>{usuario.correo}</p>
          </div>

          {usuario.biografia && (
              <div style={{ marginBottom: "2rem", background: "var(--color-bg-card)", padding: "1.5rem", borderRadius: "8px", border: "1px solid var(--color-border)" }}>
                <h2 style={{ fontSize: "1.2rem", marginBottom: "0.75rem", color: "var(--color-primary-dark)" }}>Biografía</h2>
                <p style={{ lineHeight: "1.8", color: "var(--color-text-secondary)" }}>{usuario.biografia}</p>
              </div>
          )}

          <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", justifyContent: "center" }}>
            {usuario.github && (
                <a href={usuario.github} target="_blank" rel="noreferrer" className="btn btn-outline btn-sm" style={{ borderColor: "var(--color-primary-dark)", color: "var(--color-primary-dark)" }}>
                  🔗 GitHub
                </a>
            )}
            {usuario.linkedin && (
                <a href={usuario.linkedin} target="_blank" rel="noreferrer" className="btn btn-outline btn-sm" style={{ borderColor: "var(--color-primary)", color: "var(--color-primary)" }}>
                  🔗 LinkedIn
                </a>
            )}
          </div>

          <div style={{ textAlign: "center", marginTop: "3rem" }}>
            <Link to="/equipo" className="btn btn-outline" style={{ borderColor: "var(--color-text-secondary)", color: "var(--color-text-secondary)" }}>← Volver al equipo</Link>
          </div>
        </div>
      </div>
  );
}