
-- Tabla de Pal añadimos columna para imagen local
ALTER TABLE `pal`
    ADD COLUMN `palact` TINYINT(1) NOT NULL DEFAULT 1
        COMMENT 'Indicador de si la pala está activa o no(1=activo, 0=inactivo)'
        AFTER `palimg`;
