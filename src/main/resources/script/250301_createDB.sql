-- Desactivar restricciones de clave foránea para evitar errores al eliminar tablas

SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS recomendador_palas;

-- Crear la base de datos
CREATE DATABASE recomendador_palas;
USE recomendador_palas;

-- Tabla de Usuarios
CREATE TABLE usu (
                     usuid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Id del usuario',
                     usunom VARCHAR(75) NOT NULL DEFAULT '' COMMENT 'Nombre del usuario',
                     usuema VARCHAR(255) NOT NULL UNIQUE COMMENT 'Mail único del usuario',
                     usupas VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada del usuario',
                     usufec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro del usuario',
                     usuniv ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO', 'PRO') NOT NULL DEFAULT 'PRINCIPIANTE' COMMENT 'Nivel del usuario'
) COMMENT='Usuarios registrados en el sistema';

-- Tabla de Permisos/Roles de usuario
CREATE TABLE usg (
                     usgid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Id del permiso',
                     usgnom VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'Nombre del rol (ADMIN, USER, etc.)',
                     usgdes TEXT COMMENT 'Descripción del permiso/rol',
                     usgfec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del permiso',
                     usgact BOOLEAN DEFAULT 1 COMMENT 'Estado del permiso (1=Activo, 0=Inactivo)'
) COMMENT='Roles o permisos que pueden tener los usuarios';

-- Insertar roles por defecto
INSERT INTO usg (usgnom, usgdes) VALUES
                                     ('ADMIN', 'Administrador con acceso total al sistema'),
                                     ('USER', 'Usuario registrado con permisos básicos');

-- Tabla intermedia Usuarios - Permisos (Relación muchos a muchos)
CREATE TABLE usu_usg (
                         usuusuid INT COMMENT 'ID del usuario',
                         usuusgid INT COMMENT 'ID del permiso',
                         usuusfec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de asignación del permiso',
                         PRIMARY KEY (usuusuid, usuusgid),
                         FOREIGN KEY (usuusuid) REFERENCES usu(usuid) ON DELETE CASCADE,
                         FOREIGN KEY (usuusgid) REFERENCES usg(usgid) ON DELETE CASCADE
) COMMENT='Relación entre usuarios y permisos';

-- Tabla de Palas de pádel
CREATE TABLE pal (
                     palid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Id de la pala',
                     palmar VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'Marca de la pala',
                     palmod VARCHAR(75) NOT NULL DEFAULT '' COMMENT 'Modelo de la pala',
                     palpes INT NOT NULL DEFAULT 0 COMMENT 'Peso de la pala en gramos',
                     palfor ENUM('REDONDA', 'LAGRIMA', 'DIAMANTE') NOT NULL DEFAULT 'REDONDA' COMMENT 'Forma de la pala',
                     paldur ENUM('BLANDA', 'MEDIA', 'MEDIA_DURA', 'DURA') NOT NULL DEFAULT 'MEDIA' COMMENT 'Dureza de la pala',
                     palbal ENUM('ALTO', 'MEDIO', 'BAJO') NOT NULL DEFAULT 'MEDIO' COMMENT 'Balance de la pala',
                     palpre DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT 'Precio de la pala en euros',
                     palurl VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'Enlace de compra de la pala'
) COMMENT='Palas de pádel disponibles en la plataforma';

-- Tabla de Reseñas de palas
CREATE TABLE rev (
                     revid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Id de la reseña',
                     revusu INT COMMENT 'Usuario que hizo la reseña',
                     revpal INT COMMENT 'Pala reseñada',
                     revval TINYINT UNSIGNED NOT NULL DEFAULT 3 COMMENT 'Valoración del usuario (0-5)',
                     revopi TEXT COMMENT 'Opinión del usuario sobre la pala',
                     revfec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de la reseña',
                     UNIQUE (revusu, revpal), -- Un usuario solo puede reseñar una pala una vez
                     FOREIGN KEY (revusu) REFERENCES usu(usuid) ON DELETE CASCADE,
                     FOREIGN KEY (revpal) REFERENCES pal(palid) ON DELETE CASCADE
) COMMENT='Reseñas y valoraciones de los usuarios sobre las palas';

-- Reactivar restricciones de clave foránea
SET FOREIGN_KEY_CHECKS = 1;
