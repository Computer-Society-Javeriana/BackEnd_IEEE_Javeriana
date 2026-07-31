export function getChapterLogoUrl(logo, idCapitulo, nombreCapitulo) {
  if (logo && logo.trim() !== "") {
    return logo;
  }
  const text = idCapitulo || nombreCapitulo || "IEEE";
  return `data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='400' height='220' viewBox='0 0 400 220'%3E%3Cdefs%3E%3ClinearGradient id='capGrad' x1='0%25' y1='0%25' x2='100%25' y2='100%25'%3E%3Cstop offset='0%25' stop-color='%23002855'/%3E%3Cstop offset='100%25' stop-color='%23005082'/%3E%3C/linearGradient%3E%3C/defs%3E%3Crect width='400' height='220' fill='url(%23capGrad)'/%3E%3Ctext x='50%25' y='50%25' dominant-baseline='middle' text-anchor='middle' fill='%23ffffff' font-family='Plus Jakarta Sans, sans-serif' font-weight='800' font-size='32'%3E${encodeURIComponent(text)}%3C/text%3E%3C/svg%3E`;
}
