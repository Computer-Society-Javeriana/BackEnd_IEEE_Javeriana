import logoIEEE from "../assets/logo-ieee.png";
import logoRAS from "../assets/logo-ras.png";
import logoCS from "../assets/logo-cs.png";
import logoWIE from "../assets/logo-wie.png";
import logoPES from "../assets/logo-pes.png";
import logoEMBS from "../assets/logo-embs.png";
import logoAESS from "../assets/logo-aess.png";

const chapterLogos = {
  IEEE: logoIEEE,
  RAS: logoRAS,
  CS: logoCS,
  WIE: logoWIE,
  PES: logoPES,
  EMBS: logoEMBS,
  AESS: logoAESS,
};

export function getChapterLogoUrl(logo, idCapitulo, nombreCapitulo) {
  // Si la BD devuelve una URL http/https o un data URI válido, usarlo
  if (logo && logo.trim() !== "" && (logo.startsWith("http") || logo.startsWith("data:"))) {
    return logo;
  }

  // Buscar el logo local del capítulo
  const key = String(idCapitulo || nombreCapitulo || "").toUpperCase();
  for (const [capKey, logoUrl] of Object.entries(chapterLogos)) {
    if (key.includes(capKey)) {
      return logoUrl;
    }
  }

  // Fallback genérico IEEE si no se encuentra coincidencia
  return logoIEEE;
}
