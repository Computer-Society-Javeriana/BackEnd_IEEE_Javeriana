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
    <div className="page-container">
      <div className="auth-container">
        <h1>Iniciar Sesión</h1>
        <p className="subtitle">Ingresa a tu cuenta IEEE Javeriana</p>

        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="correo">Correo electrónico</label>
            <input
              id="correo"
              type="email"
              value={correo}
              onChange={(e) => setCorreo(e.target.value)}
              placeholder="tu@correo.edu.co"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="contrasenia">Contraseña</label>
            <input
              id="contrasenia"
              type="password"
              value={contrasenia}
              onChange={(e) => setContrasenia(e.target.value)}
              placeholder="••••••••"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? "Ingresando..." : "Ingresar"}
          </button>
        </form>

        <div className="auth-footer">
          ¿No tienes cuenta? <Link to="/registro">Crear una cuenta</Link>
        </div>
      </div>
    </div>
  );
}
