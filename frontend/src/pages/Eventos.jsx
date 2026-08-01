import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getEventos } from "../services/api";

export default function Eventos() {
  const [eventos, setEventos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getEventos()
      .then((data) => setEventos(Array.isArray(data) ? data : []))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  const ahora = new Date();
  const proximos = eventos.filter((e) => new Date(e.fecha) >= ahora);
  const realizados = eventos.filter((e) => new Date(e.fecha) < ahora);

  return (
    <div className="page-container">
      <div className="page-header">
        <h1>Eventos</h1>
        <p>Catálogo completo de eventos realizados y próximos del semillero</p>
      </div>

      {loading ? (
        <p className="loading">Cargando eventos...</p>
      ) : (
        <>
          {/* Próximos */}
          <h2 className="section-title">Próximos Eventos</h2>
          <p className="section-subtitle">Lo que viene en el semillero</p>
          {proximos.length > 0 ? (
            <div className="grid-3" style={{ marginBottom: "3rem" }}>
              {proximos.map((evento) => (
                <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card">
                  <div className="card-body">
                    <span className="badge badge-warning">Próximo</span>
                    <h3>{evento.titulo}</h3>
                    <p>{evento.descripcion}</p>
                    <p style={{ marginTop: "0.5rem", fontSize: "0.85rem", color: "var(--color-accent)" }}>
                      📍 {evento.lugar || "Por definir"} &bull; 📅{" "}
                      {new Date(evento.fecha).toLocaleDateString("es-CO", { day: "numeric", month: "long", year: "numeric" })}
                    </p>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <p style={{ color: "var(--color-text-muted)", marginBottom: "3rem" }}>No hay eventos próximos por el momento.</p>
          )}

          {/* Realizados */}
          <h2 className="section-title">Eventos Realizados</h2>
          <p className="section-subtitle">Historial de eventos completados</p>
          {realizados.length > 0 ? (
            <div className="grid-3">
              {realizados.map((evento) => (
                <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card">
                  <div className="card-body">
                    <span className="badge badge-primary">Realizado</span>
                    <h3>{evento.titulo}</h3>
                    <p>{evento.descripcion}</p>
                    <p style={{ marginTop: "0.5rem", fontSize: "0.85rem", color: "var(--color-text-muted)" }}>
                      📍 {evento.lugar || "—"} &bull; 📅{" "}
                      {new Date(evento.fecha).toLocaleDateString("es-CO", { day: "numeric", month: "long", year: "numeric" })}
                    </p>
                  </div>
                </Link>
              ))}
            </div>
          ) : (
            <p style={{ color: "var(--color-text-muted)" }}>No hay eventos registrados aún.</p>
          )}
        </>
      )}
    </div>
  );
}
