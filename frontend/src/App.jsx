import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import { useEffect } from "react";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import Capitulos from "./pages/Capitulos";
import CapituloDetalle from "./pages/CapituloDetalle";
import Proyectos from "./pages/Proyectos";
import ProyectoDetalle from "./pages/ProyectoDetalle";
import Eventos from "./pages/Eventos";
import EventoDetalle from "./pages/EventoDetalle";
import Logros from "./pages/Logros";
import LogroDetalle from "./pages/LogroDetalle";
import Equipo from "./pages/Equipo";
import PerfilPublico from "./pages/PerfilPublico";
import Login from "./pages/Login";
import Registro from "./pages/Registro";
import MiPerfil from "./pages/MiPerfil";

// Componente utilitario para reiniciar el scroll al cambiar de ruta
// Vital para que los IntersectionObservers (reveal-on-scroll) se disparen correctamente
function ScrollToTop() {
  const { pathname } = useLocation();
  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);
  return null;
}

export default function App() {
  return (
      <Router>
        <ScrollToTop />
        <div style={{ display: "flex", flexDirection: "column", minHeight: "100vh", background: "var(--color-bg-main)" }}>
          <Navbar />
          <main style={{ flex: 1 }}>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/capitulos" element={<Capitulos />} />
              <Route path="/capitulos/:id" element={<CapituloDetalle />} />
              <Route path="/proyectos" element={<Proyectos />} />
              <Route path="/proyectos/:id" element={<ProyectoDetalle />} />
              <Route path="/eventos" element={<Eventos />} />
              <Route path="/eventos/:id" element={<EventoDetalle />} />
              <Route path="/logros" element={<Logros />} />
              <Route path="/logros/:id" element={<LogroDetalle />} />
              <Route path="/equipo" element={<Equipo />} />
              <Route path="/perfil/:id" element={<PerfilPublico />} />
              <Route path="/login" element={<Login />} />
              <Route path="/registro" element={<Registro />} />
              <Route path="/mi-perfil" element={<MiPerfil />} />
            </Routes>
          </main>
          <Footer />
        </div>
      </Router>
  );
}