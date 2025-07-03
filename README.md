# 📋 Aplicación de Gestión de Bonos para Clínica Estética

Proyecto web completo desarrollado con **Java + Spring Boot** para la gestión de bonos (vales/regalos) en una clínica estética. Incluye autenticación, control de acceso por roles, validaciones, CRUD, búsqueda, paginación y vencimientos automáticos.

## 🚀 Características principales

- 🔐 Inicio de sesión con roles (ADMIN / USER)
- ✅ Validaciones mediante DTOs y `jakarta.validation`
- 🧾 CRUD completo de bonos
- 🔍 Búsqueda por ID
- 📅 Paginación y ordenamiento por fecha de compra
- ⏳ Estatus automático (ACTIVO, USADO, VENCIDO)
- 🛡️ Seguridad con Spring Security
- 🌐 Interfaz con Thymeleaf
- 📄 Página de error personalizada (/error)
- 📁 Gestión de usuarios y roles desde la app (solo ADMIN)

## 🧠 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- Hibernate
- MySQL / H2 (según configuración)
- Maven
