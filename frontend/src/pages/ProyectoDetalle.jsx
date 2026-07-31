import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getProyecto } from "../services/api";

export default function ProyectoDetalle() {
  const { id } = useParams();
  const [proyecto, setProyecto] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getProyecto(id)
      .then(setProyecto)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p className="loading">Cargando...</p>;
  if (!proyecto) return <div className="page-container"><p>Proyecto no encontrado</p></div>;

  return (
    <div className="page-container">
      <div className="detail-header">
        <img
          src={proyecto.imagen || "/src/assets/placeholder-proyecto.png"}
          alt={proyecto.nombre}
          className="detail-banner"
        />
      </div>
      <div className="detail-content">
        <span className={`badge ${proyecto.estado === "Finalizado" ? "badge-success" : "badge-warning"}`}>
          {proyecto.estado || "En curso"}
        </span>
        <h1>{proyecto.nombre}</h1>
        <div className="detail-meta">
          {proyecto.fecha && <span>📅 {new Date(proyecto.fecha).toLocaleDateString("es-CO")}</span>}
          {proyecto.github && <a href={proyecto.github} target="_blank" rel="noreferrer">🔗 GitHub</a>}
        </div>
        <p style={{ fontSize: "1.05rem", lineHeight: "1.8" }}>{proyecto.descripcion}</p>

        {/* Temas */}
        {proyecto.temas && proyecto.temas.length > 0 && (
          <div style={{ marginTop: "2rem" }}>
            <h2 style={{ fontSize: "1.3rem", marginBottom: "0.75rem" }}>Categorías</h2>
            <div style={{ display: "flex", gap: "0.5rem", flexWrap: "wrap" }}>
              {proyecto.temas.map((tema) => (
                <span key={tema.idTema} className="badge badge-primary">{tema.nombreTema}</span>
              ))}
            </div>
          </div>
        )}

        {/* Colaboradores */}
        {proyecto.colaboradores && proyecto.colaboradores.length > 0 && (
          <div style={{ marginTop: "2rem" }}>
            <h2 style={{ fontSize: "1.3rem", marginBottom: "0.75rem" }}>Colaboradores</h2>
            <div className="grid-4">
              {proyecto.colaboradores.map((user) => (
                <Link to={`/perfil/${user.idUsuario}`} key={user.idUsuario} className="card">
                  <div className="card-body" style={{ textAlign: "center" }}>
                    <img
                      src={"/src/assets/placeholder-avatar.png"}
                      alt={user.nombre}
                      style={{ width: "64px", height: "64px", borderRadius: "50%", objectFit: "cover", margin: "0 auto 0.75rem", background: "var(--color-surface)" }}
                    />
                    <h3 style={{ fontSize: "0.95rem" }}>{user.nombre}</h3>
                  </div>
                </Link>
              ))}
            </div>
          </div>
        )}

        <div style={{ marginTop: "2rem" }}>
          <Link to="/proyectos" className="btn btn-outline">← Volver a proyectos</Link>
        </div>
      </div>
    </div>
  );
}
