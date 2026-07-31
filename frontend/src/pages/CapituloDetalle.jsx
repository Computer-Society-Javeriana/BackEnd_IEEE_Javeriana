import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getCapitulo } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function CapituloDetalle() {
  const { id } = useParams();
  const [capitulo, setCapitulo] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCapitulo(id)
      .then(setCapitulo)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p className="loading">Cargando...</p>;
  if (!capitulo) return <div className="page-container"><p>Capítulo no encontrado</p></div>;

  return (
    <div className="page-container">
      <div className="detail-header">
        <img
          src={getChapterLogoUrl(capitulo.logo, capitulo.idCapitulo, capitulo.nombre)}
          alt={capitulo.nombre}
          className="detail-banner"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src = getChapterLogoUrl(null, capitulo.idCapitulo, capitulo.nombre);
          }}
        />
      </div>
      <div className="detail-content">
        <h1>{capitulo.nombre}</h1>
        <div className="detail-meta">
          <span>ID: {capitulo.idCapitulo}</span>
        </div>
        <p>Información detallada del capítulo {capitulo.nombre} de IEEE Javeriana.</p>

        <div style={{ marginTop: "2rem" }}>
          <Link to="/capitulos" className="btn btn-outline">← Volver a capítulos</Link>
        </div>
      </div>
    </div>
  );
}
