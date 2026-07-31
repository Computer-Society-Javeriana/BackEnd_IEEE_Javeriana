import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getCapitulos } from "../services/api";
import { getChapterLogoUrl } from "../utils/chapterLogo";

export default function Capitulos() {
  const [capitulos, setCapitulos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargarCapitulos = () => {
    setLoading(true);
    setError(null);
    getCapitulos()
      .then((data) => {
        const sorted = (Array.isArray(data) ? data : []).sort((a, b) =>
          (a.nombre || a.idCapitulo || "").localeCompare(b.nombre || b.idCapitulo || "", "es", { sensitivity: "base" })
        );
        setCapitulos(sorted);
      })
      .catch((err) => {
        console.error("Error al obtener capítulos:", err);
        setError("No se pudieron cargar los capítulos. Por favor intenta de nuevo.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    cargarCapitulos();
  }, []);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Capítulos IEEE</h1>
        <p>Conoce todos los semilleros y capítulos que forman parte de la rama IEEE en la universidad</p>
      </div>

      {loading ? (
        <p className="loading">Cargando capítulos...</p>
      ) : error ? (
        <div style={{ textAlign: "center", padding: "2rem" }}>
          <p style={{ color: "var(--color-danger)", marginBottom: "1rem" }}>{error}</p>
          <button onClick={cargarCapitulos} className="btn btn-primary btn-sm">Reintentar</button>
        </div>
      ) : capitulos.length === 0 ? (
        <div style={{ textAlign: "center", padding: "2rem" }}>
          <p style={{ color: "var(--color-text-secondary)" }}>No hay capítulos disponibles en este momento.</p>
        </div>
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
