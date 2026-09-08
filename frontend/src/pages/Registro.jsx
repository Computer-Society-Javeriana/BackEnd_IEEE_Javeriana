import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { crearCuenta } from "../services/api";

export default function Registro() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ nombre: "", correo: "", contraseniaHash: "", github: "", linkedin: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    if (form.github && !/^https:\/\/(www\.)?github\.com\/[A-Za-z0-9_.-]+\/?$/.test(form.github.trim())) {
      setError("El enlace de GitHub debe tener la estructura https://github.com/usuario");
      setLoading(false);
      return;
    }
    if (form.linkedin && !/^https:\/\/(www\.|[a-z]{2}\.)?linkedin\.com\/in\/[A-Za-z0-9_.-]+\/?$/.test(form.linkedin.trim())) {
      setError("El enlace de LinkedIn debe tener la estructura https://linkedin.com/in/usuario");
      setLoading(false);
      return;
    }
    try {
      await crearCuenta(form);
      navigate("/login");
    } catch (err) {
      setError(err.message || "Error al crear la cuenta");
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="page-container animate-fade-in">
        <div className="auth-container" style={{ background: "var(--color-bg-main)", border: "1px solid var(--color-border)", boxShadow: "0 10px 30px rgba(37, 58, 122, 0.1)", borderRadius: "12px" }}>
          <h1 style={{ color: "var(--color-primary-dark)" }}>Crear Cuenta</h1>
          <p className="subtitle" style={{ color: "var(--color-primary)" }}>Únete a la comunidad IEEE Javeriana</p>

          {error && <div className="error-message">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="nombre" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>Nombre completo</label>
              <input id="nombre" name="nombre" value={form.nombre} onChange={handleChange} placeholder="Tu nombre" required style={{ borderColor: "var(--color-border)" }} />
            </div>
            <div className="form-group">
              <label htmlFor="correo" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>Correo electrónico</label>
              <input id="correo" name="correo" type="email" value={form.correo} onChange={handleChange} placeholder="tu@correo.edu.co" required style={{ borderColor: "var(--color-border)" }} />
            </div>
            <div className="form-group">
              <label htmlFor="contraseniaHash" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>Contraseña</label>
              <input id="contraseniaHash" name="contraseniaHash" type="password" value={form.contraseniaHash} onChange={handleChange} placeholder="••••••••" required style={{ borderColor: "var(--color-border)" }} />
            </div>
            <div className="form-group">
              <label htmlFor="github" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>GitHub (opcional)</label>
              <input id="github" name="github" value={form.github} onChange={handleChange} placeholder="https://github.com/usuario" style={{ borderColor: "var(--color-border)" }} />
            </div>
            <div className="form-group">
              <label htmlFor="linkedin" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>LinkedIn (opcional)</label>
              <input id="linkedin" name="linkedin" value={form.linkedin} onChange={handleChange} placeholder="https://linkedin.com/in/usuario" style={{ borderColor: "var(--color-border)" }} />
            </div>
            <button type="submit" className="btn btn-primary" disabled={loading} style={{ backgroundColor: "var(--color-primary)", width: "100%", marginTop: "1rem" }}>
              {loading ? "Creando cuenta..." : "Crear cuenta"}
            </button>
          </form>

          <div className="auth-footer" style={{ marginTop: "1.5rem" }}>
            ¿Ya tienes cuenta? <Link to="/login" style={{ color: "var(--color-primary-dark)", fontWeight: "bold" }}>Iniciar sesión</Link>
          </div>
        </div>
      </div>
  );
}