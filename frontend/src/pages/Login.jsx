import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login as loginApi } from "../services/api";

export default function Login() {
  const navigate = useNavigate();
  const [correo, setCorreo] = useState("");
  const [contrasenia, setContrasenia] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await loginApi(correo, contrasenia);
      navigate("/mi-perfil");
    } catch (err) {
      setError(err.message || "Error al iniciar sesión");
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="page-container animate-fade-in">
        <div className="auth-container" style={{ background: "var(--color-bg-main)", border: "1px solid var(--color-border)", boxShadow: "0 10px 30px rgba(37, 58, 122, 0.1)", borderRadius: "12px" }}>
          <h1 style={{ color: "var(--color-primary-dark)" }}>Iniciar Sesión</h1>
          <p className="subtitle" style={{ color: "var(--color-primary)" }}>Ingresa a tu cuenta IEEE Javeriana</p>

          {error && <div className="error-message">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="correo" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>Correo electrónico</label>
              <input id="correo" type="email" value={correo} onChange={(e) => setCorreo(e.target.value)} placeholder="tu@correo.edu.co" required style={{ borderColor: "var(--color-border)" }} />
            </div>
            <div className="form-group">
              <label htmlFor="contrasenia" style={{ color: "var(--color-primary-dark)", fontWeight: "500" }}>Contraseña</label>
              <input id="contrasenia" type="password" value={contrasenia} onChange={(e) => setContrasenia(e.target.value)} placeholder="••••••••" required style={{ borderColor: "var(--color-border)" }} />
            </div>
            <button type="submit" className="btn btn-primary" disabled={loading} style={{ backgroundColor: "var(--color-primary)", width: "100%", marginTop: "1rem" }}>
              {loading ? "Ingresando..." : "Ingresar"}
            </button>
          </form>

          <div className="auth-footer" style={{ marginTop: "1.5rem" }}>
            ¿No tienes cuenta? <Link to="/registro" style={{ color: "var(--color-primary-dark)", fontWeight: "bold" }}>Crear una cuenta</Link>
          </div>
        </div>
      </div>
  );
}