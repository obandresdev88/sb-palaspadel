# Recomendador de Palas de Pádel

Este proyecto es una aplicación web desarrollada con **Spring Boot** para el backend y **Bootstrap** para la interfaz de usuario. Su objetivo principal es recomendar palas de pádel a los usuarios según su nivel y estilo de juego.

## Tecnologías utilizadas

- **Backend:** Java + Spring Boot
- **Base de datos:** MariaDB
- **Frontend:** HTML, CSS y Bootstrap
- **ORM:** Hibernate (JPA)

## Instalación y configuración

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DEL_PROYECTO>
```

### 2. Configurar la base de datos

Asegúrate de tener **MariaDB** instalado y corriendo en el puerto **3306**. Luego, crea una base de datos:

```sql
CREATE DATABASE recomendador_palas;
```

Edita el archivo `application.properties` o `application.yml` para configurar la conexión:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/recomendador_palas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecutar la aplicación

Compila y ejecuta el proyecto con:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Características principales

- **Registro y autenticación de usuarios**
- **Roles de usuario:** ADMIN, USER e INVITADO
- **Recomendación de palas** según nivel y estilo de juego
- **Gestor de reseñas** para valorar y comentar sobre las palas

## Estructura del proyecto

```
/src
  /main
    /java
      /com.example.recomendador
        /controllers  # Controladores REST
        /services     # Lógica de negocio
        /repositories # Repositorios JPA
        /models       # Entidades
    /resources
      application.properties
      schema.sql  # Script opcional de creación de BD
```

## Futuras mejoras

- Implementación de más criterios en la recomendación
- Mejoras en la interfaz con React o Angular
- Integración con una API externa de tiendas de pádel

##

---

Desarrollado por Andrés Ochoa

