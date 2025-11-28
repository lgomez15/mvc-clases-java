# Gestión de Cine (MVP)

Bienvenido a la aplicación de **Gestión de Cine**. Este es un sistema de consola desarrollado en Java que permite administrar una base de datos simple de películas, directores y actores, siguiendo el patrón de arquitectura **MVC (Modelo-Vista-Controlador)**.

## 🚀 Funcionalidades

El sistema ofrece las siguientes capacidades:

### Gestión de Entidades
- **Registrar Director**: Añade nuevos directores al sistema.
- **Registrar Actor**: Añade nuevos actores.
- **Registrar Película**: Crea películas con título y año de lanzamiento.

### Relaciones
- **Asignar Director a Película**: Vincula un director existente a una película.
- **Asignar Actor a Película**: Añade actores al reparto de una película.

### Consultas
- **Listar Películas**: Muestra todas las películas con su director y reparto.
- **Listar Directores**: Muestra la lista de directores registrados.
- **Listar Actores**: Muestra la lista de actores registrados.

### Persistencia y Exportación
- **Persistencia Automática**: Todos los datos se guardan automáticamente en la carpeta `repo/` en formato CSV al cerrar la aplicación.
- **Exportación de Datos**: Opción dedicada para exportar la base de datos completa a:
    - **JSON**: Formato estructurado ideal para integraciones (`.json`).
    - **TXT**: Formato legible delimitado por punto y coma (`.txt`).

---

## 📂 Estructura del Proyecto

El proyecto está organizado siguiendo las convenciones de Java y el patrón MVC:

```
mvc-clases-java/
├── src/
│   └── ejemplomvp/
│       ├── Main.java           # Punto de entrada de la aplicación
│       ├── CineController.java # Controlador: Gestiona la lógica y flujo de datos
│       ├── models/             # Modelos: Clases POJO (Pelicula, Actor, Director)
│       └── views/              # Vistas: Interfaz de consola (ConsoleView)
├── repo/                       # Almacenamiento de datos (CSV, JSON, TXT)
├── launcher.sh                 # Script de compilación y ejecución automática
└── README.md                   # Documentación del proyecto
```

---

## 🛠️ Requisitos y Ejecución

### Requisitos Previos
- **Java Development Kit (JDK)**: Versión 17 o superior recomendada.
- **Terminal**: Bash o similar (Linux/macOS).

### Cómo Ejecutar

La forma más sencilla de ejecutar la aplicación es utilizando el script `launcher.sh` incluido, que se encarga de compilar el código y lanzar el programa.

1.  **Abrir una terminal** en la carpeta raíz del proyecto.
2.  **Dar permisos de ejecución** al script (solo la primera vez):
    ```bash
    chmod +x launcher.sh
    ```
3.  **Iniciar la aplicación**:
    ```bash
    ./launcher.sh
    ```

---

## 📖 Guía de Uso

Al iniciar la aplicación, verás el siguiente menú principal:

```text
--- GESTIÓN DE CINE ---
1. Registrar Director
2. Registrar Actor
3. Registrar Película
4. Asignar Director a Película
5. Asignar Actor a Película
6. Listar Películas
7. Listar Directores
8. Listar Actores
9. Exportar datos
0. Salir
Seleccione una opción:
```

1.  **Navegación**: Ingrese el número de la opción deseada y presione `Enter`.
2.  **Entrada de Datos**: Siga las instrucciones en pantalla para ingresar nombres, títulos o seleccionar IDs.
3.  **Guardar y Salir**: Seleccione la opción `0` para guardar todos los cambios en la carpeta `repo/` y cerrar el programa.

### Nota sobre la Exportación
Para detalles técnicos sobre cómo funciona la exportación a JSON y TXT, consulte el archivo [`explicacion_exportacion.md`](./explicaciones/explicacion_exportacion.md).
