-- Script de inicialización de la base de datos
-- Este script se ejecuta automáticamente cuando PostgreSQL se inicia por primera vez
-- (solo cuando la base de datos está vacía)

-- ===================================================================
-- Crear tabla de artículos
CREATE TABLE IF NOT EXISTS articulos (
    id SERIAL PRIMARY KEY,
    titulo_art VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio INTEGER NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_articulos_titulo ON articulos(titulo_art);
CREATE INDEX IF NOT EXISTS idx_articulos_precio ON articulos(precio);

-- Insertar datos de ejemplo (opcional)
INSERT INTO articulos (titulo_art, descripcion, precio, stock) VALUES
    ('Laptop Dell XPS 15', 'Laptop de alto rendimiento con pantalla 4K', 1299, 10),
    ('iPhone 15 Pro', 'Smartphone con cámara profesional', 999, 25),
    ('Samsung Galaxy Watch', 'Reloj inteligente con monitor de salud', 299, 15),
    ('AirPods Pro', 'Auriculares inalámbricos con cancelación de ruido', 249, 30),
    ('MacBook Pro M3', 'Laptop profesional con chip M3', 1999, 8)
ON CONFLICT DO NOTHING;


-- ===================================================================
-- Crear tabla de categoria artículos
CREATE TABLE IF NOT EXISTS categoriArt (
    id SERIAL PRIMARY KEY,
    titulo_cat VARCHAR(255) NOT NULL,
    descripcion TEXT
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_categ_articulos_titulo ON categoriArt(titulo_cat);

-- Insertar datos de ejemplo (opcional)
INSERT INTO categoriArt (titulo_cat, descripcion) VALUES
    ('telefonos', 'categoria telefono'),
    ('auriculares', 'de oreja y gaming'),
    ('relojes', 'relojes inteligentes y no'),
    ('ordenadores', 'portatiles y sobre mesa')
ON CONFLICT DO NOTHING;

-- Mostrar mensaje de confirmación
DO $$
BEGIN
    RAISE NOTICE 'Base de datos inicializada correctamente';
END $$;

