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
    <div className="page-container">
      <div style={{ maxWidth: "600px", margin: "0 auto" }}>
        <div className="page-header">
          <h1>Mi Perfil</h1>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div style={{ textAlign: "center", marginBottom: "2rem" }}>
          <img
            src={getAvatarUrl(usuario)}
            alt={usuario?.nombre}
            onError={(e) => {
              e.target.onerror = null;
              e.target.src = getAvatarUrl(usuario);
            }}
            style={{
              width: "120px", height: "120px", borderRadius: "50%", objectFit: "cover",
              border: "4px solid var(--color-primary)", background: "var(--color-surface)",
            }}
          />
        </div>

        {editando ? (
          <>
            <div className="form-group">
              <label>Nombre</label>
              <input name="nombre" value={form.nombre || ""} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label>Biografía</label>
              <textarea name="biografia" value={form.biografia || ""} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label>GitHub</label>
              <input name="github" value={form.github || ""} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label>LinkedIn</label>
              <input name="linkedin" value={form.linkedin || ""} onChange={handleChange} />
            </div>
            <div style={{ display: "flex", gap: "1rem" }}>
              <button onClick={handleSave} className="btn btn-primary">Guardar cambios</button>
              <button onClick={() => setEditando(false)} className="btn btn-outline">Cancelar</button>
            </div>
          </>
        ) : (
          <>
            <div style={{ background: "var(--color-bg-card)", borderRadius: "var(--radius-md)", padding: "1.5rem", border: "1px solid var(--color-border)" }}>
              <p><strong>Nombre:</strong> {usuario?.nombre}</p>
              <p style={{ marginTop: "0.5rem" }}><strong>Correo:</strong> {usuario?.correo}</p>
              <p style={{ marginTop: "0.5rem" }}><strong>Biografía:</strong> {usuario?.biografia || "Sin descripción"}</p>
              <p style={{ marginTop: "0.5rem" }}><strong>GitHub:</strong> {usuario?.github || "—"}</p>
              <p style={{ marginTop: "0.5rem" }}><strong>LinkedIn:</strong> {usuario?.linkedin || "—"}</p>
            </div>
            <div style={{ display: "flex", gap: "1rem", marginTop: "1.5rem" }}>
              <button onClick={() => setEditando(true)} className="btn btn-primary">Editar perfil</button>
              <button onClick={handleLogout} className="btn btn-danger">Cerrar sesión</button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
