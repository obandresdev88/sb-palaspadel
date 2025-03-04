-- Desactivar restricciones de clave foránea para evitar errores al eliminar tablas
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS recomendador_palas;

-- Crear la base de datos
CREATE DATABASE recomendador_palas;
USE recomendador_palas;

-- Tabla de Usuarios
CREATE TABLE usu (
                     usuid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador único del usuario',
                     usunom VARCHAR(75) NOT NULL DEFAULT '' COMMENT 'Nombre del usuario',
                     usuema VARCHAR(255) NOT NULL UNIQUE COMMENT 'Correo electrónico único del usuario',
                     usupas VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada del usuario',
                     usufec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de registro del usuario',
                     usuniv INT COMMENT 'Nivel del usuario (relación con niv.nivid)'
) COMMENT='Usuarios registrados en el sistema';

-- Tabla de Niveles de jugador
CREATE TABLE niv (
                     nivid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador único del nivel',
                     nivnom ENUM('Principiante', 'Intermedio', 'Avanzado', 'Pro') NOT NULL DEFAULT 'Principiante' COMMENT 'Nombre del nivel',
                     nivdes TEXT COMMENT 'Descripción del nivel de juego',
                     nivfec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación/modificación del nivel'
) COMMENT='Niveles de habilidad de los jugadores';

-- Tabla de Permisos/Roles de usuario
CREATE TABLE usg (
                     usgid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador único del permiso',
                     usgnom VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'Nombre del rol (ADMIN, USER, etc.)',
                     usgdes TEXT COMMENT 'Descripción del permiso/rol',
                     usgfec TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del permiso',
                     usgact BOOLEAN DEFAULT 1 COMMENT 'Estado del permiso (1=Activo, 0=Inactivo)'
) COMMENT='Roles o permisos que pueden tener los usuarios';

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
                     palid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador único de la pala',
                     palmar VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'Marca de la pala',
                     palmod VARCHAR(75) NOT NULL DEFAULT '' COMMENT 'Modelo específico de la pala',
                     palpes INT NOT NULL DEFAULT 0 COMMENT 'Peso de la pala en gramos',
                     palfor ENUM('redonda', 'lágrima', 'diamante') NOT NULL DEFAULT 'redonda' COMMENT 'Forma de la pala',
                     paldur ENUM('blanda', 'media', 'media-dura', 'dura') NOT NULL DEFAULT 'media' COMMENT 'Dureza de la pala',
                     palbal ENUM('alto', 'medio', 'bajo') NOT NULL DEFAULT 'medio' COMMENT 'Balance de la pala',
                     palpre DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT 'Precio de la pala en euros',
                     palurl VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'Enlace de compra de la pala'
) COMMENT='Palas de pádel disponibles en la plataforma';

-- Tabla de Reseñas de palas
CREATE TABLE rev (
                     revid INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador único de la reseña',
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
