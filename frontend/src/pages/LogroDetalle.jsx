import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getLogro } from "../services/api";

export default function LogroDetalle() {
  const { id } = useParams();
  const [logro, setLogro] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getLogro(id)
      .then(setLogro)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p className="loading">Cargando...</p>;
  if (!logro) return <div className="page-container"><p>Logro no encontrado</p></div>;

  return (
    <div className="page-container">
      <div className="detail-header">
        <img
          src={logro.imagen || "/src/assets/placeholder-logro.png"}
          alt={logro.titulo}
          className="detail-banner"
        />
      </div>
      <div className="detail-content">
        <span className="badge badge-success">Logro</span>
        <h1>{logro.titulo}</h1>
        <p style={{ fontSize: "1.1rem", lineHeight: "1.8", marginTop: "1rem" }}>{logro.descripcion}</p>

        {logro.contribuyentes && logro.contribuyentes.length > 0 && (
          <div style={{ marginTop: "2rem" }}>
            <h2 className="section-title" style={{ fontSize: "1.5rem" }}>Contribuyentes</h2>
            <div className="grid-4" style={{ marginTop: "1rem" }}>
              {logro.contribuyentes.map((user) => (
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
          <Link to="/logros" className="btn btn-outline">← Volver a logros</Link>
        </div>
      </div>
    </div>
  );
}
