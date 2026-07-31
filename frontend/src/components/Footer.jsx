import { Link } from "react-router-dom";

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer-inner">
        <div className="footer-col">
          <h4>IEEE Javeriana</h4>
          <p>Rama estudiantil de IEEE en la Pontificia Universidad Javeriana.</p>
        </div>
        <div className="footer-col">
          <h4>Enlaces</h4>
          <Link to="/capitulos">Capítulos</Link>
          <Link to="/proyectos">Proyectos</Link>
          <Link to="/eventos">Eventos</Link>
          <Link to="/logros">Logros</Link>
        </div>
        <div className="footer-col">
          <h4>Contacto</h4>
          <p>ieee@javeriana.edu.co</p>
          <p>Bogotá, Colombia</p>
        </div>
        <div className="footer-col">
          <h4>Redes Sociales</h4>
          <a href="https://github.com/Computer-Society-Javeriana" target="_blank" rel="noreferrer">GitHub</a>
          <a href="https://linkedin.com" target="_blank" rel="noreferrer">LinkedIn</a>
        </div>
      </div>
      <div className="footer-bottom">
        &copy; {new Date().getFullYear()} IEEE Javeriana. Todos los derechos reservados.
      </div>
    </footer>
  );
}
