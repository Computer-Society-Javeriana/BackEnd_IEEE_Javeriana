export function getAvatarUrl(usuario) {
  if (usuario?.foto && usuario.foto.trim() !== "") {
    return usuario.foto;
  }
  const nombre = usuario?.nombre || "Usuario";
  const inicial = nombre.trim().charAt(0).toUpperCase() || "U";

  // Retorna un SVG vectorial limpio con colores IEEE y la inicial del usuario
  return `data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='160' height='160' viewBox='0 0 160 160'%3E%3Cdefs%3E%3ClinearGradient id='avatarGrad' x1='0%25' y1='0%25' x2='100%25' y2='100%25'%3E%3Cstop offset='0%25' stop-color='%23003366'/%3E%3Cstop offset='100%25' stop-color='%23006699'/%3E%3C/linearGradient%3E%3C/defs%3E%3Ccircle cx='80' cy='80' r='80' fill='url(%23avatarGrad)'/%3E%3Ctext x='50%25' y='54%25' dominant-baseline='middle' text-anchor='middle' fill='%23ffffff' font-family='Plus Jakarta Sans, sans-serif' font-weight='800' font-size='64'%3E${encodeURIComponent(inicial)}%3C/text%3E%3C/svg%3E`;
}
