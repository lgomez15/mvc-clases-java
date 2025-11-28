# Documentación de Persistencia - Gestión de Cine

Este documento detalla cómo se ha implementado la persistencia de datos en la aplicación **Gestión de Cine**. El objetivo es guardar la información (Directores, Actores y Películas) en ficheros de texto para que no se pierda al cerrar el programa.

## 1. Estrategia de Persistencia

Se ha optado por una persistencia basada en **ficheros de texto delimitados (CSV)**.
- **Formato**: Texto plano.
- **Delimitador**: Punto y coma (`;`) para separar campos y coma (`,`) para listas internas (como actores en una película).
- **Ubicación**: Raíz del proyecto ([directores.csv](file:///Users/usuario/Desktop/mvpcompleto/directores.csv), [actores.csv](file:///Users/usuario/Desktop/mvpcompleto/actores.csv), [peliculas.csv](file:///Users/usuario/Desktop/mvpcompleto/peliculas.csv)).

## 2. Métodos Implementados

La lógica se divide en dos partes: la **serialización** (en los modelos) y la **gestión de archivos** (en el controlador).

### A. En los Modelos ([Director](file:///Users/usuario/Desktop/mvpcompleto/src/models/Director.java#5-42), [Actor](file:///Users/usuario/Desktop/mvpcompleto/src/models/Actor.java#5-42), [Pelicula](file:///Users/usuario/Desktop/mvpcompleto/src/models/Pelicula.java#5-76))

Cada clase del modelo implementa dos métodos clave para transformar objetos a texto y viceversa.

1.  **[toCSV()](file:///Users/usuario/Desktop/mvpcompleto/src/models/Pelicula.java#46-57) (Serialización)**
    -   **Función**: Convierte el estado actual del objeto en una cadena de texto (String) con formato CSV.
    -   **Ejemplo ([Pelicula](file:///Users/usuario/Desktop/mvpcompleto/src/models/Pelicula.java#5-76))**:
        ```java
        public String toCSV() {
            // Concatena título, año, nombre del director y nombres de actores
            return titulo + ";" + anio + ";" + directorNombre + ";" + actoresNombres;
        }
        ```

2.  **[fromCSV(String csv)](file:///Users/usuario/Desktop/mvpcompleto/src/models/Director.java#35-41) (Deserialización)**
    -   **Función**: Método estático (Factory Method) que recibe una línea de texto CSV y devuelve una nueva instancia del objeto.
    -   **Nota**: En el caso de [Pelicula](file:///Users/usuario/Desktop/mvpcompleto/src/models/Pelicula.java#5-76), este método solo recupera los datos básicos (título, año). Las relaciones (Director y Actores) se reconstruyen después en el controlador.

### B. En el Controlador ([CineController](file:///Users/usuario/Desktop/mvpcompleto/src/main/CineController.java#11-253))

El controlador orquesta cuándo se guardan y cargan los datos.

1.  **[guardarDatos()](file:///Users/usuario/Desktop/mvpcompleto/src/main/CineController.java#158-187)**
    -   Se ejecuta al seleccionar la opción "Salir" (0).
    -   Recorre las listas en memoria (`ArrayList`).
    -   Llama a [toCSV()](file:///Users/usuario/Desktop/mvpcompleto/src/models/Pelicula.java#46-57) de cada objeto.
    -   Escribe las líneas resultantes en los ficheros físicos usando `Files.write()`.

2.  **[cargarDatos()](file:///Users/usuario/Desktop/mvpcompleto/src/main/CineController.java#188-252)**
    -   Se ejecuta en el constructor del controlador (al iniciar la app).
    -   Lee todas las líneas de los ficheros usando `Files.readAllLines()`.
    -   Convierte cada línea en un objeto usando [fromCSV()](file:///Users/usuario/Desktop/mvpcompleto/src/models/Director.java#35-41).
    -   **Reconstrucción de Relaciones**: Una vez cargados todos los objetos básicos, conecta las películas con sus directores y actores buscando por nombre.

---

## 3. Flujo de Datos

### Flujo de Guardado (Memoria -> Disco)

Cuando el usuario cierra la aplicación, los datos fluyen desde la memoria RAM hacia el disco duro.

```mermaid
sequenceDiagram
    participant User as Usuario
    participant Ctrl as CineController
    participant Model as Modelos (Pelicula/Actor...)
    participant File as Ficheros (.csv)

    User->>Ctrl: Selecciona "Salir" (0)
    Ctrl->>Ctrl: Llama a guardarDatos()
    
    loop Para cada Director, Actor, Pelicula
        Ctrl->>Model: toCSV()
        Model-->>Ctrl: Retorna String "dato;dato;..."
    end
    
    Ctrl->>File: Files.write(...)
    File-->>Ctrl: Confirmación de escritura
    Ctrl-->>User: "Datos guardados correctamente"
```

### Flujo de Carga (Disco -> Memoria)

Al iniciar, el proceso es inverso, pero con un paso adicional para "tejer" las relaciones entre objetos.

```mermaid
flowchart TD
    A[Inicio de App] --> B[CineController Constructor]
    B --> C{¿Existen ficheros?}
    C -- No --> D[Iniciar listas vacías]
    C -- Sí --> E[cargarDatos()]
    
    subgraph Carga
    E --> F[Leer directores.csv]
    F --> G[Crear objetos Director]
    G --> H[Leer actores.csv]
    H --> I[Crear objetos Actor]
    I --> J[Leer peliculas.csv]
    J --> K[Crear objetos Pelicula (sin relaciones)]
    end
    
    subgraph Reconstruccion[Reconstrucción de Relaciones]
    K --> L[Leer nombre de Director en CSV]
    L --> M[Buscar Director en lista cargada]
    M --> N[Pelicula.setDirector(...)]
    
    N --> O[Leer nombres de Actores en CSV]
    O --> P[Buscar Actores en lista cargada]
    P --> Q[Pelicula.agregarActor(...)]
    end
    
    Q --> R[App lista para usar]
```

## 4. Detalles Técnicos Importantes

-   **Clases de Utilidad**: Se ha utilizado `java.nio.file.Files` y `java.nio.file.Paths` para una gestión moderna y eficiente de ficheros.
-   **Codificación**: Se fuerza el uso de `StandardCharsets.UTF_8` para evitar problemas con tildes o caracteres especiales (ñ).
-   **Manejo de Errores**: Todo el proceso de E/S (Entrada/Salida) está envuelto en bloques `try-catch` para capturar `IOException` y evitar que el programa se cierre inesperadamente si falla un fichero.
