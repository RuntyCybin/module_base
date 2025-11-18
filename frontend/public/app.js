const API_BASE_URL =
  window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
    ? 'http://localhost:8080'
    : '/api';

const state = {
  articles: [],
  lastUpdated: null,
};

const elements = {
  tableBody: document.getElementById('articlesBody'),
  template: document.getElementById('articleRow'),
  searchInput: document.getElementById('searchInput'),
  reloadBtn: document.getElementById('reloadBtn'),
  statTotal: document.getElementById('statTotal'),
  statUpdated: document.getElementById('statUpdated'),
  statusMessage: document.getElementById('statusMessage'),
};

const formatCurrency = (value) =>
  new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(value);

const formatDate = (value) =>
  value ? new Date(value).toLocaleString('es-ES') : '—';

const normalize = (text) => (text || '').toLowerCase().normalize('NFD').replace(/\p{Diacritic}/gu, '');

function setStatus(message, variant = 'info') {
  if (!elements.statusMessage) return;
  elements.statusMessage.textContent = message;
  elements.statusMessage.classList.remove('status--error', 'status--success', 'status--info');
  elements.statusMessage.classList.add(`status--${variant}`);
}

function updateStats() {
  const total = state.articles.length;
  elements.statTotal.textContent = total;
  elements.statUpdated.textContent = state.lastUpdated
    ? state.lastUpdated.toLocaleTimeString('es-ES')
    : '—';
}

function renderRows(rows) {
  elements.tableBody.innerHTML = '';

  if (!rows.length) {
    elements.tableBody.innerHTML = '<tr><td colspan="6" class="empty">Sin resultados</td></tr>';
    return;
  }

  const fragment = document.createDocumentFragment();

  rows.forEach((item) => {
    const clone = elements.template.content.cloneNode(true);
    clone.querySelector('[data-field="id"]').textContent = item.id;
    clone.querySelector('[data-field="nombre"]').textContent = item.nombre;
    clone.querySelector('[data-field="descripcion"]').textContent = item.descripcion;
    clone.querySelector('[data-field="precio"]').textContent = formatCurrency(item.precio);
    clone.querySelector('[data-field="stock"]').textContent = item.stock;
    clone.querySelector('[data-field="createdAt"]').textContent = formatDate(item.createdAt);
    fragment.appendChild(clone);
  });

  elements.tableBody.appendChild(fragment);
}

function applyFilter() {
  const query = normalize(elements.searchInput.value);
  const filtered = state.articles.filter((item) => {
    const target = `${item.nombre} ${item.descripcion}`;
    return normalize(target).includes(query);
  });
  renderRows(filtered);
}

async function fetchArticles({ silent = false } = {}) {
  if (!silent) {
    setStatus('Consultando API…', 'info');
    elements.tableBody.innerHTML = '<tr><td colspan="6" class="empty">Cargando artículos…</td></tr>';
  }

  try {
    const response = await fetch(`${API_BASE_URL}/articles`, {
      headers: { Accept: 'application/json' },
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}`);
    }

    const data = await response.json();
    state.articles = Array.isArray(data) ? data : [];
    state.lastUpdated = new Date();

    updateStats();
    applyFilter();
    setStatus('Datos actualizados correctamente.', 'success');
  } catch (error) {
    console.error(error);
    setStatus('No se pudo obtener la información. Inténtalo de nuevo.', 'error');
    elements.tableBody.innerHTML = '<tr><td colspan="6" class="empty">Error al consultar la API</td></tr>';
  }
}

function init() {
  elements.reloadBtn?.addEventListener('click', () => fetchArticles());
  elements.searchInput?.addEventListener('input', applyFilter);
  fetchArticles();
}

window.addEventListener('DOMContentLoaded', init);
