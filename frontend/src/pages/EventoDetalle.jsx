import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getEvento } from "../services/api";

export default function EventoDetalle() {
  const { id } = useParams();
  const [evento, setEvento] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getEvento(id)
        .then(setEvento)
        .catch(() => {})
        .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <p className="loading">Cargando...</p>;
  if (!evento) return <div className="page-container"><p>Evento no encontrado</p></div>;

  const esProximo = evento.fecha && new Date(evento.fecha) >= new Date();
  const mapQuery = encodeURIComponent(`${evento.lugar || "Pontificia Universidad Javeriana"}, Bogota`);

  return (
      <div className="page-container animate-fade-in">
        <div className="detail-content" style={{ marginTop: "2rem" }}>
          <div style={{ display: "flex", gap: "0.5rem", alignItems: "center", marginBottom: "1rem" }}>
          <span className="badge" style={{ backgroundColor: esProximo ? "var(--color-accent)" : "var(--color-primary)", color: esProximo ? "var(--color-primary-dark)" : "#FFF" }}>
            {esProximo ? "Próximo Evento" : "Evento Realizado"}
          </span>
            {evento.capitulo && (
                <span className="badge" style={{ backgroundColor: "var(--color-accent-light)", color: "var(--color-primary-dark)" }}>
              Capítulo: {evento.capitulo.nombre || evento.capitulo.idCapitulo}
            </span>
            )}
          </div>

          <h1 style={{ color: "var(--color-primary-dark)" }}>{evento.titulo}</h1>

          <div className="detail-meta" style={{ marginTop: "1rem", marginBottom: "1.5rem", fontSize: "1rem", color: "var(--color-text-secondary)" }}>
            {evento.fecha && (
                <span style={{ marginRight: "1.5rem" }}>
              📅 {new Date(evento.fecha).toLocaleDateString("es-CO", { day: "numeric", month: "long", year: "numeric", hour: "2-digit", minute: "2-digit" })}
            </span>
            )}
            {evento.lugar && (
                <span>📍 {evento.lugar}</span>
            )}
          </div>

          <p style={{ fontSize: "1.1rem", lineHeight: "1.8", color: "var(--color-text-main)" }}>
            {evento.descripcion || "Sin descripción disponible para este evento."}
          </p>

          {evento.lugar && (
              <div style={{ marginTop: "2.5rem" }}>
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "1rem", flexWrap: "wrap", gap: "0.5rem" }}>
                  <h2 style={{ fontSize: "1.3rem", margin: 0, color: "var(--color-primary-dark)" }}>📍 Ubicación en Mapa</h2>
                  <a
                      href={`https://www.google.com/maps/search/?api=1&query=${mapQuery}`}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="btn btn-outline btn-sm"
                      style={{ display: "inline-flex", alignItems: "center", gap: "0.4rem", borderColor: "var(--color-primary)", color: "var(--color-primary)" }}
                  >
                    Abrir en Google Maps ↗
                  </a>
                </div>

                <div style={{
                  borderRadius: "16px",
                  overflow: "hidden",
                  border: "1px solid var(--color-border)",
                  boxShadow: "0 4px 12px rgba(37, 58, 122, 0.1)",
                  height: "380px",
                  width: "100%"
                }}>
                  <iframe title="Ubicación Google Maps" width="100%" height="100%" style={{ border: 0 }} loading="lazy" allowFullScreen src={`https://maps.google.com/maps?q=${mapQuery}&t=&z=16&ie=UTF8&iwloc=&output=embed`} />
                </div>
              </div>
          )}

          <div style={{ marginTop: "3rem", display: "flex", gap: "1rem" }}>
            <Link to="/eventos" className="btn btn-outline" style={{ borderColor: "var(--color-primary-dark)", color: "var(--color-primary-dark)" }}>← Volver a eventos</Link>
            {evento.capitulo && (
                <Link to={`/capitulos/${evento.capitulo.idCapitulo}`} className="btn btn-primary" style={{ backgroundColor: "var(--color-primary)", color: "#FFF" }}>
                  Ver Capítulo ({evento.capitulo.idCapitulo})
                </Link>
            )}
          </div>
        </div>
      </div>
  );
}
