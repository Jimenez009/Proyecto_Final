# 🕒 Sistema de Control de Entrada y Salida (Java EE / JSF)

Un sistema web para la gestión y control de asistencia de empleados, administración de puestos de trabajo y control de accesos de usuarios.

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** Java 17+
- **Framework Web:** Jakarta Faces (JSF 3.0 / JSF 4.0)
- **Componentes UI:** PrimeFaces
- **Arquitectura:** Patrón Modelo-Vista-Controlador (MVC) y DAO con JDBC
- **Base de Datos:** MySQL
- **Gestor de Dependencias:** Apache Maven
- **Estilos UI:** CSS3 personalizado
- **Servidor de Aplicaciones:** GlassFish / Payara / Apache TomEE
- **IDE:** IntelliJ IDEA

---

## 📁 Estructura del Proyecto

```text
src/main/java/com/uth/sistema_entrada_salida/
├── config/
│   └── Database.java             # Conexión JDBC a MySQL
├── controlador/
│   ├── EmpleadoBean.java         # Controller para la gestión de Empleados
│   ├── PuestoBean.java           # Controller para la gestión de Puestos
│   ├── UsuarioBean.java          # Controller para la gestión de Usuarios
│   └── LoginBean.java            # Control de inicio y cierre de sesión
├── dao/
│   ├── EmpleadoDAO.java          # Consultas SQL y CRUD de Empleados
│   ├── PuestoDAO.java            # Consultas SQL y CRUD de Puestos
│   └── UsuarioDAO.java           # Consultas SQL y CRUD de Usuarios
└── modelo/
    ├── Empleado.java             # Entidad Empleado
    ├── Puesto.java               # Entidad Puesto
    ├── Usuario.java              # Entidad Usuario
    └── Marcacion.java            # Entidad Marcación

src/main/webapp/
├── admin/                        # Vistas del Panel de Administración (XHTML)
│   ├── dashboard.xhtml
│   ├── empleados.xhtml
│   ├── puestos.xhtml
│   ├── usuarios.xhtml
│   └── marcaciones.xhtml
├── resources/
│   └── css/
│       └── estilos.css           # Estilos generales del sistema
└── login.xhtml                   # Pantalla de acceso
```

---

## 🚀 Requisitos Previos

- Java Development Kit (JDK) 17 o superior
- Apache Maven 3.8.0 o superior
- MySQL Server 5.7 o superior
- Servidor de aplicaciones (GlassFish, Payara o Apache TomEE)

---

## 🔐 Características Principales

- ✅ **Autenticación de Usuarios:** Sistema de login seguro
- ✅ **Gestión de Empleados:** Crear, editar, eliminar y listar empleados
- ✅ **Gestión de Puestos:** Administrar puestos de trabajo disponibles
- ✅ **Control de Asistencia:** Registro de entrada y salida de empleados
- ✅ **Control de Accesos:** Permisos y roles de usuario
- ✅ **Dashboard:** Visualización de datos y estadísticas
- ✅ **Interfaz Responsiva:** UI amigable con PrimeFaces

---

## 📝 Flujo de Uso

1. **Acceder al Sistema:** Navegar a `http://localhost:8080/sistema_entrada_salida`
2. **Iniciar Sesión:** Usar credenciales de usuario
3. **Panel de Control:** Visualizar datos y acceder a funciones según rol
4. **Gestión de Empleados:** CRUD completo desde la interfaz
5. **Registro de Asistencia:** Marcar entrada y salida

---



