import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUsuarios } from "../services/api";
import { getAvatarUrl } from "../utils/avatar";

export default function Equipo() {
    const [miembros, setMiembros] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getUsuarios()
            .then((data) => {
                const safeData = Array.isArray(data) ? data : [];
                const membersOfIEEE = safeData.filter((miembro) =>
                    miembro.roles?.some((r) => r.capitulo?.idCapitulo === "IEEE")
                );
                setMiembros(membersOfIEEE);
            })
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
    }, [miembros]);

    return (
        <div className="page-container animate-fade-in">
            <div className="page-header">
                <h1 style={{ color: "var(--color-primary-dark)" }}>Junta Directiva</h1>
                <p>Conoce a las personas que dirigen actualmente el semillero</p>
            </div>

            {loading ? (
                <p className="loading">Cargando equipo...</p>
            ) : miembros.length === 0 ? (
                <div className="reveal-on-scroll" style={{ textAlign: "center", padding: "4rem 1.5rem", background: "var(--color-bg-card)", borderRadius: "12px", border: "1px solid var(--color-border)" }}>
                    <h3 style={{ color: "var(--color-primary-dark)", marginBottom: "0.5rem" }}>Aún no hay miembros en la Junta Directiva</h3>
                    <p style={{ color: "var(--color-text-secondary)", maxWidth: "500px", margin: "0 auto" }}>
                        Los usuarios registrados deben tener un capítulo y rol asignado para figurar en el equipo directivo.
                    </p>
                </div>
            ) : (
                <div className="flex-team-grid">
                    {miembros.map((miembro) => (
                        <Link to={`/perfil/${miembro.idUsuario}`} key={miembro.idUsuario} className="card reveal-on-scroll">
                            <img
                                src={getAvatarUrl(miembro)}
                                alt={miembro.nombre}
                                onError={(e) => {
                                    e.target.onerror = null;
                                    e.target.src = getAvatarUrl(miembro);
                                }}
                                className="card-image"
                                style={{ height: "auto", aspectRatio: "3/4", objectFit: "cover", borderBottom: "none" }}
                            />
                            <div className="card-body" style={{ textAlign: "center", padding: "1.5rem" }}>
                                <h3 style={{ color: "var(--color-primary-dark)" }}>{miembro.nombre}</h3>
                                {miembro.roles && miembro.roles.length > 0 ? (
                                    <div style={{ marginBottom: "0.5rem" }}>
                                        {miembro.roles.map(r => (
                                            <span key={r.idUsuarioCapitulo} className="badge" style={{ display: "inline-block", margin: "2px", fontSize: "0.75rem", backgroundColor: "var(--color-primary)", color: "#FFF" }}>
                        {r.rol} - {r.capitulo?.idCapitulo}
                      </span>
                                        ))}
                                    </div>
                                ) : (
                                    <p style={{ color: "var(--color-accent)", fontSize: "0.85rem", marginBottom: "0.5rem" }}>
                                        Miembro IEEE
                                    </p>
                                )}
                                <p style={{ display: "-webkit-box", WebkitLineClamp: 3, WebkitBoxOrient: "vertical", overflow: "hidden", color: "var(--color-text-secondary)" }}>{miembro.biografia || "Sin descripción"}</p>
                            </div>
                        </Link>
                    ))}
                </div>
            )}
        </div>
    );
}