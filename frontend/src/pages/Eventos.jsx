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

  useEffect(() => {
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) entry.target.classList.add("reveal-active");
      });
    }, { threshold: 0.1 });
    document.querySelectorAll(".reveal-on-scroll").forEach(el => observer.observe(el));
    return () => observer.disconnect();
  }, [eventos]);

  const ahora = new Date();
  const proximos = eventos.filter((e) => new Date(e.fecha) >= ahora);
  const realizados = eventos.filter((e) => new Date(e.fecha) < ahora);

  return (
      <div className="page-container animate-fade-in">
        <div className="page-header">
          <h1 style={{ color: "var(--color-primary-dark)" }}>Eventos</h1>
          <p>Catálogo completo de eventos realizados y próximos del semillero</p>
        </div>

        {loading ? (
            <p className="loading">Cargando eventos...</p>
        ) : (
            <>
              <h2 className="section-title reveal-on-scroll" style={{ color: "var(--color-primary-dark)" }}>Próximos Eventos</h2>
              <p className="section-subtitle reveal-on-scroll">Lo que viene en el semillero</p>
              {proximos.length > 0 ? (
                  <div className="grid-3" style={{ marginBottom: "3rem" }}>
                    {proximos.map((evento) => (
                        <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card reveal-on-scroll">
                          <div className="card-body">
                            <span className="badge" style={{ backgroundColor: "var(--color-accent)", color: "var(--color-primary-dark)" }}>Próximo</span>
                            <h3 style={{ color: "var(--color-primary-dark)" }}>{evento.titulo}</h3>
                            <p>{evento.descripcion}</p>
                            <p style={{ marginTop: "0.5rem", fontSize: "0.85rem", color: "var(--color-primary)" }}>
                              📍 {evento.lugar || "Por definir"} &bull; 📅 {new Date(evento.fecha).toLocaleDateString("es-CO", { day: "numeric", month: "long", year: "numeric" })}
                            </p>
                          </div>
                        </Link>
                    ))}
                  </div>
              ) : (
                  <p className="reveal-on-scroll" style={{ color: "var(--color-text-secondary)", marginBottom: "3rem" }}>No hay eventos próximos por el momento.</p>
              )}

              <h2 className="section-title reveal-on-scroll" style={{ color: "var(--color-primary-dark)" }}>Eventos Realizados</h2>
              <p className="section-subtitle reveal-on-scroll">Historial de eventos completados</p>
              {realizados.length > 0 ? (
                  <div className="grid-3">
                    {realizados.map((evento) => (
                        <Link to={`/eventos/${evento.idEvento}`} key={evento.idEvento} className="card reveal-on-scroll">
                          <div className="card-body">
                            <span className="badge" style={{ backgroundColor: "var(--color-primary)", color: "#FFF" }}>Realizado</span>
                            <h3 style={{ color: "var(--color-primary-dark)" }}>{evento.titulo}</h3>
                            <p>{evento.descripcion}</p>
                            <p style={{ marginTop: "0.5rem", fontSize: "0.85rem", color: "var(--color-text-secondary)" }}>
                              📍 {evento.lugar || "—"} &bull; 📅 {new Date(evento.fecha).toLocaleDateString("es-CO", { day: "numeric", month: "long", year: "numeric" })}
                            </p>
                          </div>
                        </Link>
                    ))}
                  </div>
              ) : (
                  <p className="reveal-on-scroll" style={{ color: "var(--color-text-secondary)" }}>No hay eventos registrados aún.</p>
              )}
            </>
        )}
      </div>
  );
}