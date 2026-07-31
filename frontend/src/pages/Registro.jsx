import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { crearCuenta } from "../services/api";

export default function Registro() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    nombre: "",
    correo: "",
    contraseniaHash: "",
    github: "",
    linkedin: "",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

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
    <div className="page-container">
      <div className="auth-container">
        <h1>Crear Cuenta</h1>
        <p className="subtitle">Únete a la comunidad IEEE Javeriana</p>

        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="nombre">Nombre completo</label>
            <input id="nombre" name="nombre" value={form.nombre} onChange={handleChange} placeholder="Tu nombre" required />
          </div>
          <div className="form-group">
            <label htmlFor="correo">Correo electrónico</label>
            <input id="correo" name="correo" type="email" value={form.correo} onChange={handleChange} placeholder="tu@correo.edu.co" required />
          </div>
          <div className="form-group">
            <label htmlFor="contraseniaHash">Contraseña</label>
            <input id="contraseniaHash" name="contraseniaHash" type="password" value={form.contraseniaHash} onChange={handleChange} placeholder="••••••••" required />
          </div>
          <div className="form-group">
            <label htmlFor="github">GitHub (opcional)</label>
            <input id="github" name="github" value={form.github} onChange={handleChange} placeholder="https://github.com/usuario" />
          </div>
          <div className="form-group">
            <label htmlFor="linkedin">LinkedIn (opcional)</label>
            <input id="linkedin" name="linkedin" value={form.linkedin} onChange={handleChange} placeholder="https://linkedin.com/in/usuario" />
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? "Creando cuenta..." : "Crear cuenta"}
          </button>
        </form>

        <div className="auth-footer">
          ¿Ya tienes cuenta? <Link to="/login">Iniciar sesión</Link>
        </div>
      </div>
    </div>
  );
}
