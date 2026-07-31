import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getCapitulos } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function Capitulos() {
  const [capitulos, setCapitulos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCapitulos()
      .then(setCapitulos)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Capítulos IEEE</h1>
        <p>Conoce todos los semilleros y capítulos que forman parte de la rama IEEE en la universidad</p>
      </div>

      {loading ? (
        <p className="loading">Cargando capítulos...</p>
      ) : (
        <div className="grid-3">
          {capitulos.map((cap) => (
            <Link to={`/capitulos/${cap.idCapitulo}`} key={cap.idCapitulo} className="card">
              <img
                src={getChapterLogoUrl(cap.logo, cap.idCapitulo, cap.nombre)}
                alt={cap.nombre}
                className="card-image"
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = getChapterLogoUrl(null, cap.idCapitulo, cap.nombre);
                }}
              />
              <div className="card-body">
                <h3>{cap.nombre}</h3>
                <p>Capítulo {cap.idCapitulo}</p>
                <span className="btn btn-outline btn-sm" style={{ marginTop: "0.75rem" }}>
                  Ver más →
                </span>
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
