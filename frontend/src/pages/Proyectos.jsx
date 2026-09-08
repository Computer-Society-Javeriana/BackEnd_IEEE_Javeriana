import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function Proyectos() {
  const [proyectos, setProyectos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Estados para el buscador
  const [filtroTipo, setFiltroTipo] = useState('todos');
  const [terminoBusqueda, setTerminoBusqueda] = useState('');

  const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

  const cargarProyectos = async (url) => {
    try {
      setLoading(true);
      setError(null);
      const response = await fetch(url);
      if (!response.ok) throw new Error('Error al obtener los proyectos');
      const data = await response.json();
      setProyectos(data);
    } catch (err) {
      setError(err.message);
      setProyectos([]);
    } finally {
      setLoading(false);
    }
  };

  // Carga inicial
  useEffect(() => {
    cargarProyectos(`${API_BASE_URL}/proyectos`);
  }, []);

  const handleBuscar = (e) => {
    e.preventDefault();

    if (filtroTipo === 'todos' || terminoBusqueda.trim() === '') {
      cargarProyectos(`${API_BASE_URL}/proyectos`);
      return;
    }

    const valorDecodificado = encodeURIComponent(terminoBusqueda.trim());
    let endpoint = '';

    switch (filtroTipo) {
      case 'nombre':
        endpoint = `/proyectos/nombre/${valorDecodificado}`;
        break;
      case 'autor':
        endpoint = `/proyectos/autor/${valorDecodificado}`;
        break;
      case 'capitulo':
        endpoint = `/proyectos/capitulo/${valorDecodificado}`;
        break;
      case 'palabras':
        // Usa @RequestParam(name = "palabras") según el controlador
        endpoint = `/proyectos/palabras?palabras=${valorDecodificado}`;
        break;
      default:
        endpoint = '/proyectos';
    }

    cargarProyectos(`${API_BASE_URL}${endpoint}`);
  };

  return (
      <div className="page-container reveal-on-scroll reveal-active">
        <div className="page-header">
          <h1>Proyectos IEEE</h1>
          <p>Explora las innovaciones y desarrollos técnicos de nuestra comunidad estudiantil.</p>
        </div>

        {/* Panel de Búsqueda */}
        <div style={{ background: 'var(--color-bg-card)', padding: '1.5rem', borderRadius: 'var(--radius-md)', marginBottom: '2.5rem', boxShadow: 'var(--shadow-sm)' }}>
          <form onSubmit={handleBuscar} style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', alignItems: 'flex-end' }}>

            <div className="form-group" style={{ marginBottom: 0, flex: '1 1 200px' }}>
              <label htmlFor="filtroTipo">Buscar por</label>
              <select
                  id="filtroTipo"
                  value={filtroTipo}
                  onChange={(e) => setFiltroTipo(e.target.value)}
              >
                <option value="todos">Todos los proyectos</option>
                <option value="nombre">Nombre del Proyecto</option>
                <option value="autor">Autor</option>
                <option value="capitulo">Capítulo (ID)</option>
                <option value="palabras">Palabras Clave</option>
              </select>
            </div>

            <div className="form-group" style={{ marginBottom: 0, flex: '2 1 300px' }}>
              <label htmlFor="terminoBusqueda">Término de búsqueda</label>
              <input
                  type="text"
                  id="terminoBusqueda"
                  placeholder="Ej. Inteligencia Artificial, Juan Pérez..."
                  value={terminoBusqueda}
                  onChange={(e) => setTerminoBusqueda(e.target.value)}
                  disabled={filtroTipo === 'todos'}
              />
            </div>

            <button type="submit" className="btn btn-primary" style={{ height: '48px' }}>
              Buscar
            </button>
          </form>
        </div>

        {/* Resultados */}
        {loading && <div className="loading">Cargando proyectos...</div>}

        {error && <div className="error-message">{error}</div>}

        {!loading && !error && proyectos.length === 0 && (
            <div style={{ textAlign: 'center', color: 'var(--color-text-muted)', padding: '3rem' }}>
              No se encontraron proyectos con esos criterios.
            </div>
        )}

        {!loading && !error && proyectos.length > 0 && (
            <div className="grid-3">
              {proyectos.map((proyecto) => (
                  <Link to={`/proyectos/${proyecto.id}`} key={proyecto.id} className="card">
                    <div className="card-body">
                <span className="badge badge-primary">
                  Capítulo {proyecto.capituloId}
                </span>
                      <h3>{proyecto.nombre}</h3>
                      <p style={{ fontSize: '0.85rem', color: 'var(--color-text-muted)', marginBottom: '0.5rem' }}>
                        Por: {proyecto.autor}
                      </p>
                      <p>
                        {proyecto.descripcion ?
                            `${proyecto.descripcion.substring(0, 100)}...` :
                            'Sin descripción disponible.'}
                      </p>
                    </div>
                  </Link>
              ))}
            </div>
        )}
      </div>
  );
}
