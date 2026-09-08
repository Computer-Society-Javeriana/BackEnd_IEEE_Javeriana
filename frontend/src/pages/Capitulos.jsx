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

    // Animación al hacer scroll
    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) entry.target.classList.add("reveal-active");
            });
        }, { threshold: 0.1 });
        const elements = document.querySelectorAll(".reveal-on-scroll");
        elements.forEach(el => observer.observe(el));
        return () => observer.disconnect();
    }, [capitulos]);

    return (
        <div className="page-container animate-fade-in">
            <div className="page-header">
                <h1 style={{ color: "var(--color-primary-dark)" }}>Capítulos IEEE</h1>
                <p>Conoce todos los semilleros y capítulos que forman parte de la rama IEEE en la universidad</p>
            </div>

            {loading ? (
                <p className="loading">Cargando capítulos...</p>
            ) : error ? (
                <div style={{ textAlign: "center", padding: "2rem" }}>
                    <p style={{ color: "var(--color-primary-dark)", marginBottom: "1rem" }}>{error}</p>
                    <button onClick={cargarCapitulos} className="btn btn-primary btn-sm" style={{ backgroundColor: "var(--color-primary)" }}>Reintentar</button>
                </div>
            ) : capitulos.length === 0 ? (
                <div style={{ textAlign: "center", padding: "2rem" }}>
                    <p style={{ color: "var(--color-text-secondary)" }}>No hay capítulos disponibles en este momento.</p>
                </div>
            ) : (
                <div className="grid-3">
                    {capitulos.map((cap) => (
                        <Link to={`/capitulos/${cap.idCapitulo}`} key={cap.idCapitulo} className="card reveal-on-scroll">
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
                                <h3 style={{ color: "var(--color-primary-dark)" }}>{cap.nombre}</h3>
                                <p>Capítulo {cap.idCapitulo}</p>
                                <span className="btn btn-outline btn-sm" style={{ marginTop: "0.75rem", borderColor: "var(--color-primary-dark)", color: "var(--color-primary-dark)" }}>
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
