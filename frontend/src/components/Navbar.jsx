import { Link, useLocation } from "react-router-dom";
import logoIEEE from "../assets/logo-ieee.png";

export default function Navbar() {
  const location = useLocation();
  const isActive = (path) => location.pathname === path ? "active" : "";

  return (
    <nav className="navbar">
      <div className="navbar-inner">
        <Link to="/" className="navbar-brand">
          <img src={logoIEEE} alt="IEEE Javeriana" />
        </Link>
        <ul className="navbar-links">
          <li><Link to="/" className={isActive("/")}>Inicio</Link></li>
          <li><Link to="/capitulos" className={isActive("/capitulos")}>Capítulos</Link></li>
          <li><Link to="/proyectos" className={isActive("/proyectos")}>Proyectos</Link></li>
          <li><Link to="/eventos" className={isActive("/eventos")}>Eventos</Link></li>
          <li><Link to="/logros" className={isActive("/logros")}>Logros</Link></li>
          <li><Link to="/equipo" className={isActive("/equipo")}>Equipo</Link></li>
          <li><Link to="/login" className="btn btn-primary btn-sm">Ingresar</Link></li>
        </ul>
      </div>
    </nav>
  );
}
