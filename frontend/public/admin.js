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
    createArticleBtn: document.getElementById('createArticleBtn'),
    createArticleModal: document.getElementById('createArticleModal'),
    closeModalBtn: document.getElementById('closeModalBtn'),
    cancelBtn: document.getElementById('cancelBtn'),
    createArticleForm: document.getElementById('createArticleForm'),
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
        elements.tableBody.innerHTML = '<tr><td colspan="6" class="empty">Cargando artículos!</td></tr>';
    }

    console.log('fetchArticles');

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

function openModal() {
    elements.createArticleModal?.classList.add('active');
    document.body.style.overflow = 'hidden';
}

function closeModal() {
    elements.createArticleModal?.classList.remove('active');
    document.body.style.overflow = '';
    elements.createArticleForm?.reset();
}

async function createArticle(formData) {
    try {
        setStatus('Creando artículo…', 'info');

        const response = await fetch(`${API_BASE_URL}/articles`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
            body: JSON.stringify({
                titulo: formData.titulo,
                desc: formData.desc,
                precio: parseFloat(formData.precio),
                stock: parseInt(formData.stock, 10),
            }),
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `Error ${response.status}`);
        }

        const newArticle = await response.json();
        setStatus('Artículo creado correctamente.', 'success');
        closeModal();

        // Recargar la lista de artículos
        await fetchArticles({ silent: true });
    } catch (error) {
        console.error(error);
        setStatus(`Error al crear el artículo: ${error.message}`, 'error');
    }
}

function init() {
    elements.reloadBtn?.addEventListener('click', () => fetchArticles());
    elements.searchInput?.addEventListener('input', applyFilter);

    // Modal handlers
    elements.createArticleBtn?.addEventListener('click', openModal);
    elements.closeModalBtn?.addEventListener('click', closeModal);
    elements.cancelBtn?.addEventListener('click', closeModal);
    elements.createArticleModal?.addEventListener('click', (e) => {
        if (e.target === elements.createArticleModal || e.target.classList.contains('modal__overlay')) {
            closeModal();
        }
    });

    // Form submit handler
    elements.createArticleForm?.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());
        await createArticle(data);
    });

    // Close modal on Escape key
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && elements.createArticleModal?.classList.contains('active')) {
            closeModal();
        }
    });

    fetchArticles();
}

window.addEventListener('DOMContentLoaded', init);