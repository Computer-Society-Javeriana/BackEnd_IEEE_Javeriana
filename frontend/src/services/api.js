const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080";

async function request(endpoint, options = {}) {
  const response = await fetch(`${API_BASE}${endpoint}`, {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || `Error ${response.status}`);
  }

  const contentType = response.headers.get("content-type");
  if (contentType && contentType.includes("application/json")) {
    return response.json();
  }
  return response.text();
}

// ==================== USUARIOS ====================
export const getUsuarios = (categoriaId) =>
  request(categoriaId ? `/usuarios?categoria=${categoriaId}` : "/usuarios");

export const getUsuario = (id) => request(`/usuarios/${id}`);

export const getProyectosDeUsuario = (id) => request(`/usuarios/${id}/proyectos`);

export const actualizarUsuario = (id, data) =>
  request(`/usuarios/${id}`, { method: "PUT", body: JSON.stringify(data) });

// ==================== PROYECTOS ====================
export const getProyectos = () => request("/proyectos");

export const getProyecto = (id) => request(`/proyectos/${id}`);

export const crearProyecto = (data) =>
  request("/proyectos", { method: "POST", body: JSON.stringify(data) });

export const actualizarProyecto = (id, data) =>
  request(`/proyectos/${id}`, { method: "PUT", body: JSON.stringify(data) });

export const eliminarProyecto = (id) =>
  request(`/proyectos/${id}`, { method: "DELETE" });

// ==================== CUENTAS ====================
export const crearCuenta = (data) =>
  request("/cuentas", { method: "POST", body: JSON.stringify(data) });

export const login = (correo, contrasenia) =>
  request(`/cuentas/login?correo=${encodeURIComponent(correo)}&contrasenia=${encodeURIComponent(contrasenia)}`, { method: "POST" });

export const logout = () =>
  request("/cuentas/logout", { method: "POST" });

export const getMiPerfil = () => request("/cuentas/me");

export const actualizarMiPerfil = (data) =>
  request("/cuentas/me", { method: "PUT", body: JSON.stringify(data) });

export const eliminarMiCuenta = () =>
  request("/cuentas/me", { method: "DELETE" });

export const getPerfilPublico = (id) => request(`/cuentas/${id}`);

// ==================== CONTENIDO: EVENTOS ====================
export const getEventos = () => request("/contenido/eventos");
export const getEvento = (id) => request(`/contenido/eventos/${id}`);
export const crearEvento = (data) =>
  request("/contenido/eventos", { method: "POST", body: JSON.stringify(data) });
export const actualizarEvento = (id, data) =>
  request(`/contenido/eventos/${id}`, { method: "PUT", body: JSON.stringify(data) });
export const eliminarEvento = (id) =>
  request(`/contenido/eventos/${id}`, { method: "DELETE" });

// ==================== CONTENIDO: CATEGORÍAS ====================
export const getCategorias = () => request("/contenido/categorias");
export const getCategoria = (id) => request(`/contenido/categorias/${id}`);
export const crearCategoria = (data) =>
  request("/contenido/categorias", { method: "POST", body: JSON.stringify(data) });
export const actualizarCategoria = (id, data) =>
  request(`/contenido/categorias/${id}`, { method: "PUT", body: JSON.stringify(data) });
export const eliminarCategoria = (id) =>
  request(`/contenido/categorias/${id}`, { method: "DELETE" });

// ==================== CONTENIDO: LOGROS ====================
export const getLogros = () => request("/contenido/logros");
export const getLogro = (id) => request(`/contenido/logros/${id}`);
export const crearLogro = (data) =>
  request("/contenido/logros", { method: "POST", body: JSON.stringify(data) });
export const actualizarLogro = (id, data) =>
  request(`/contenido/logros/${id}`, { method: "PUT", body: JSON.stringify(data) });
export const eliminarLogro = (id) =>
  request(`/contenido/logros/${id}`, { method: "DELETE" });

// ==================== CONTENIDO: CAPÍTULOS ====================
export const getCapitulos = () => request("/contenido/capitulos");
export const getCapitulo = (id) => request(`/contenido/capitulos/${id}`);
export const crearCapitulo = (data) =>
  request("/contenido/capitulos", { method: "POST", body: JSON.stringify(data) });
export const actualizarCapitulo = (id, data) =>
  request(`/contenido/capitulos/${id}`, { method: "PUT", body: JSON.stringify(data) });
export const eliminarCapitulo = (id) =>
  request(`/contenido/capitulos/${id}`, { method: "DELETE" });

// ==================== CONTENIDO: HORARIOS ====================
export const getHorarios = (capituloId) =>
  request(capituloId ? `/contenido/horarios?capitulo=${capituloId}` : "/contenido/horarios");
export const getHorario = (id) => request(`/contenido/horarios/${id}`);
export const crearHorario = (data) =>
  request("/contenido/horarios", { method: "POST", body: JSON.stringify(data) });

// ==================== CONTENIDO: LEARNING PATH ====================
export const getLearningPaths = (capituloId) =>
  request(capituloId ? `/contenido/learning-path?capitulo=${capituloId}` : "/contenido/learning-path");
export const getLearningPath = (id) => request(`/contenido/learning-path/${id}`);
export const crearLearningPath = (data) =>
  request("/contenido/learning-path", { method: "POST", body: JSON.stringify(data) });

// ==================== CONTENIDO: GALERÍA ====================
export const getGaleria = (capituloId) =>
  request(capituloId ? `/contenido/galeria?capitulo=${capituloId}` : "/contenido/galeria");
export const getFoto = (id) => request(`/contenido/galeria/${id}`);
export const crearFoto = (data) =>
  request("/contenido/galeria", { method: "POST", body: JSON.stringify(data) });

// ==================== TODOS ====================
export const getTodos = (usuarioId) =>
  request(usuarioId ? `/todos?usuarios=${usuarioId}` : "/todos");
export const getTodo = (id) => request(`/todos/${id}`);
export const crearTodo = (data) =>
  request("/todos", { method: "POST", body: JSON.stringify(data) });
export const actualizarTodo = (id, data) =>
  request(`/todos/${id}`, { method: "PUT", body: JSON.stringify(data) });
export const eliminarTodo = (id) =>
  request(`/todos/${id}`, { method: "DELETE" });
