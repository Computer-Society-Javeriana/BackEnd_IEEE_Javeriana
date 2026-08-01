import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getProyectos } from "../services/api";

import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function Proyectos() {
  const [proyectos, setProyectos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getProyectos()
      .then((data) => setProyectos(Array.isArray(data) ? data : []))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Proyectos</h1>
        <p>Catálogo de todos los proyectos realizados en los semilleros IEEE</p>
      </div>

      {loading ? (
        <p className="loading">Cargando proyectos...</p>
      ) : (
        <div className="grid-3">
          {proyectos.map((proy) => (
            <Link to={`/proyectos/${proy.idProyecto}`} key={proy.idProyecto} className="card">
              <img
                src={proy.imagen && !proy.imagen.startsWith("/src/") ? proy.imagen : getChapterLogoUrl(null, proy.idProyecto, proy.nombre)}
                alt={proy.nombre}
                className="card-image"
              />
              <div className="card-body">
                <div style={{ display: "flex", gap: "0.5rem", marginBottom: "0.5rem" }}>
                  <span className={`badge ${proy.estado === "Finalizado" ? "badge-success" : "badge-warning"}`}>
                    {proy.estado || "En curso"}
                  </span>
                </div>
                <h3>{proy.nombre}</h3>
                <p>{proy.descripcion}</p>
                {proy.colaboradores && (
                  <p style={{ marginTop: "0.5rem", fontSize: "0.8rem", color: "var(--color-text-muted)" }}>
                    👥 {proy.colaboradores.length} colaboradores
                  </p>
                )}
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
