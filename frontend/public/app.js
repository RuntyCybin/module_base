const API_BASE_URL =
  window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
    ? 'http://localhost:8080'
    : '/api';

const state = {
  articles: [],
  lastUpdated: null,
};

const elements = {
  cardsGrid: document.getElementById('articlesGrid'),
  cardTemplate: document.getElementById('articleCard'),
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
  elements.statusMessage.classList.remove('alert-danger', 'alert-success', 'alert-info');
  const bootstrapVariant = variant === 'error' ? 'danger' : variant;
  elements.statusMessage.classList.add(`alert-${bootstrapVariant}`);
}

function updateStats() {
  const total = state.articles.length;
  elements.statTotal.textContent = total;
  elements.statUpdated.textContent = state.lastUpdated
    ? state.lastUpdated.toLocaleTimeString('es-ES')
    : '—';
}

const gradients = [
  'linear-gradient(135deg, #111827 0%, #1f2937 100%)',
  'linear-gradient(135deg, #0ea5e9 0%, #2563eb 100%)',
  'linear-gradient(135deg, #7c3aed 0%, #6b21a8 100%)',
  'linear-gradient(135deg, #f59e0b 0%, #ef4444 100%)',
  'linear-gradient(135deg, #10b981 0%, #059669 100%)',
  'linear-gradient(135deg, #ec4899 0%, #8b5cf6 100%)',
];

function pickGradient(index) {
  return gradients[index % gradients.length];
}

function renderCards(rows) {
  if (!elements.cardsGrid || !elements.cardTemplate) return;
  elements.cardsGrid.innerHTML = '';

  if (!rows.length) {
    elements.cardsGrid.innerHTML = `
      <div class="col">
        <div class="card h-100 border-0 bg-light d-flex align-items-center justify-content-center">
          <div class="text-muted py-5">Sin resultados</div>
        </div>
      </div>`;
    return;
  }

  const fragment = document.createDocumentFragment();

  rows.forEach((item, idx) => {
    const clone = elements.cardTemplate.content.cloneNode(true);
    const card = clone.querySelector('[data-bg]');
    card.style.backgroundImage = pickGradient(idx);
    clone.querySelector('[data-field="nombre"]').textContent = item.nombre;
    clone.querySelector('[data-field="descripcion"]').textContent = item.descripcion || '—';
    clone.querySelector('[data-field="precio"]').textContent = formatCurrency(item.precio);
    clone.querySelector('[data-field="stock"]').textContent = `Stock: ${item.stock}`;
    clone.querySelector('[data-field="createdAt"]').textContent = formatDate(item.createdAt);
    fragment.appendChild(clone);
  });

  elements.cardsGrid.appendChild(fragment);
}

function applyFilter() {
  const query = normalize(elements.searchInput.value);
  const filtered = state.articles.filter((item) => {
    const target = `${item.nombre} ${item.descripcion}`;
    return normalize(target).includes(query);
  });
  renderCards(filtered);
}

async function fetchArticles({ silent = false } = {}) {
  if (!silent) {
    setStatus('Consultando API…', 'info');
    elements.cardsGrid.innerHTML = `
      <div class="col">
        <div class="card h-100 border-0 bg-light d-flex align-items-center justify-content-center">
          <div class="text-muted py-5">Cargando artículos…</div>
        </div>
      </div>`;
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
    elements.cardsGrid.innerHTML = `
      <div class="col">
        <div class="card h-100 border-0 bg-light d-flex align-items-center justify-content-center">
          <div class="text-muted py-5">Error al consultar la API</div>
        </div>
      </div>`;
  }
}

function setupLiveUpdates() {
  const url = `${API_BASE_URL}/articles/sse`;
  const source = new EventSource(url);

  source.onopen = () => {
    console.log('[SSE] Conectado a', url);
    setStatus('Conectado al stream de artículos.', 'info');
  };

  source.onmessage = (event) => {
    try {
      const articulo = JSON.parse(event.data);

      // Evitar duplicados por id
      const existingIndex = state.articles.findIndex((a) => a.id === articulo.id);
      if (existingIndex !== -1) {
        state.articles[existingIndex] = articulo;
      } else {
        state.articles.push(articulo);
      }

      state.lastUpdated = new Date();
      updateStats();
      applyFilter(); // respeta el filtro actual

      console.log('[SSE] Artículo recibido:', articulo);
    } catch (e) {
      console.error('[SSE] Error parseando mensaje:', e, event.data);
    }
  };

  source.onerror = (err) => {
    console.error('[SSE] Error en el stream:', err);
    setStatus('Conexión en tiempo real interrumpida. Reintentando…', 'error');
    // El propio EventSource reintenta por defecto, así que no cerramos
  };
}

function init() {
  elements.reloadBtn?.addEventListener('click', () => fetchArticles());
  elements.searchInput?.addEventListener('input', applyFilter);

  fetchArticles();
  setupLiveUpdates();
}

window.addEventListener('DOMContentLoaded', init);
