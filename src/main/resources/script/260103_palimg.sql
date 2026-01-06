
-- Tabla de Pal añadimos columna para imagen local
ALTER TABLE `pal`
    ADD COLUMN `palimg` VARCHAR(255) NOT NULL DEFAULT ''
        COMMENT 'Ruta imagen local (e.g., /images/palas/id_marca_modelo.jpg)'
        AFTER `palurl`;
