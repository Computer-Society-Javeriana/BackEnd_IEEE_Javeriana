import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getMiPerfil, actualizarMiPerfil, logout } from "../services/api";
import { getAvatarUrl } from "../utils/avatar";

export default function MiPerfil() {
  const navigate = useNavigate();
  const [usuario, setUsuario] = useState(null);
  const [editando, setEditando] = useState(false);
  const [form, setForm] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    getMiPerfil()
        .then((data) => {
          setUsuario(data);
          setForm(data);
        })
        .catch(() => navigate("/login"))
        .finally(() => setLoading(false));
  }, [navigate]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSave = async () => {
    setError("");
    if (form.github && !/^https:\/\/(www\.)?github\.com\/[A-Za-z0-9_.-]+\/?$/.test(form.github.trim())) {
      setError("El enlace de GitHub debe tener la estructura https://github.com/usuario");
      return;
    }
    if (form.linkedin && !/^https:\/\/(www\.|[a-z]{2}\.)?linkedin\.com\/in\/[A-Za-z0-9_.-]+\/?$/.test(form.linkedin.trim())) {
      setError("El enlace de LinkedIn debe tener la estructura https://linkedin.com/in/usuario");
      return;
    }
    try {
      const actualizado = await actualizarMiPerfil(form);
      setUsuario(actualizado);
      setEditando(false);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleLogout = async () => {
    await logout();
    navigate("/");
  };

  if (loading) return <p className="loading">Cargando perfil...</p>;

  return (
      <div className="page-container animate-fade-in">
        <div style={{ maxWidth: "600px", margin: "0 auto", background: "var(--color-bg-main)", padding: "2rem", borderRadius: "12px", boxShadow: "0 10px 30px rgba(37, 58, 122, 0.08)", border: "1px solid var(--color-border)" }}>
          <div className="page-header">
            <h1 style={{ color: "var(--color-primary-dark)" }}>Mi Perfil</h1>
          </div>

          {error && <div className="error-message">{error}</div>}

          <div style={{ textAlign: "center", marginBottom: "2rem" }}>
            <img
                src={getAvatarUrl(usuario)}
                alt={usuario?.nombre}
                onError={(e) => { e.target.onerror = null; e.target.src = getAvatarUrl(usuario); }}
                style={{ width: "120px", height: "120px", borderRadius: "50%", objectFit: "cover", border: "4px solid var(--color-accent)", background: "var(--color-bg-card)", boxShadow: "0 4px 10px rgba(0,0,0,0.1)" }}
            />
          </div>

          {editando ? (
              <>
                <div className="form-group">
                  <label style={{ color: "var(--color-primary-dark)" }}>Nombre</label>
                  <input name="nombre" value={form.nombre || ""} onChange={handleChange} style={{ borderColor: "var(--color-border)" }} />
                </div>
                <div className="form-group">
                  <label style={{ color: "var(--color-primary-dark)" }}>Biografía</label>
                  <textarea name="biografia" value={form.biografia || ""} onChange={handleChange} style={{ borderColor: "var(--color-border)" }} />
                </div>
                <div className="form-group">
                  <label style={{ color: "var(--color-primary-dark)" }}>GitHub</label>
                  <input name="github" value={form.github || ""} onChange={handleChange} style={{ borderColor: "var(--color-border)" }} />
                </div>
                <div className="form-group">
                  <label style={{ color: "var(--color-primary-dark)" }}>LinkedIn</label>
                  <input name="linkedin" value={form.linkedin || ""} onChange={handleChange} style={{ borderColor: "var(--color-border)" }} />
                </div>
                <div style={{ display: "flex", gap: "1rem" }}>
                  <button onClick={handleSave} className="btn btn-primary" style={{ backgroundColor: "var(--color-primary)" }}>Guardar cambios</button>
                  <button onClick={() => setEditando(false)} className="btn btn-outline" style={{ borderColor: "var(--color-border)", color: "var(--color-text-secondary)" }}>Cancelar</button>
                </div>
              </>
          ) : (
              <>
                <div style={{ background: "var(--color-bg-card)", borderRadius: "8px", padding: "1.5rem", border: "1px solid var(--color-border)" }}>
                  <p><strong>Nombre:</strong> <span style={{ color: "var(--color-text-secondary)" }}>{usuario?.nombre}</span></p>
                  <p style={{ marginTop: "0.5rem" }}><strong>Correo:</strong> <span style={{ color: "var(--color-text-secondary)" }}>{usuario?.correo}</span></p>
                  <p style={{ marginTop: "0.5rem" }}><strong>Biografía:</strong> <span style={{ color: "var(--color-text-secondary)" }}>{usuario?.biografia || "Sin descripción"}</span></p>
                  <p style={{ marginTop: "0.5rem" }}><strong>GitHub:</strong> <span style={{ color: "var(--color-text-secondary)" }}>{usuario?.github || "—"}</span></p>
                  <p style={{ marginTop: "0.5rem" }}><strong>LinkedIn:</strong> <span style={{ color: "var(--color-text-secondary)" }}>{usuario?.linkedin || "—"}</span></p>
                </div>
                <div style={{ display: "flex", gap: "1rem", marginTop: "1.5rem" }}>
                  <button onClick={() => setEditando(true)} className="btn btn-primary" style={{ backgroundColor: "var(--color-primary)" }}>Editar perfil</button>
                  <button onClick={handleLogout} className="btn btn-outline" style={{ borderColor: "var(--color-primary-dark)", color: "var(--color-primary-dark)" }}>Cerrar sesión</button>
                </div>
              </>
          )}
        </div>
      </div>
  );
}