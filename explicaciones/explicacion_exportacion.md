# Documentación de Exportación - Gestión de Cine

Este documento detalla cómo funciona la nueva funcionalidad de exportación de datos en la aplicación **Gestión de Cine**. Esta característica permite extraer la información del sistema en formatos estándar para su uso externo.

## 1. Formatos de Exportación

La aplicación permite exportar los datos (Directores, Actores y Películas) en dos formatos diferentes:

### A. JSON (.json)
El formato JSON (JavaScript Object Notation) es un estándar ligero para el intercambio de datos.
- **Estructura**: Objetos anidados. Las películas incluyen la información completa de su director y actores dentro del mismo objeto.
- **Archivos generados**: `directores.json`, `actores.json`, `peliculas.json`.
- **Uso**: Ideal para integración con aplicaciones web, APIs o análisis de datos.

**Ejemplo de salida (Pelicula):**
```json
{
  "titulo": "Inception",
  "anio": 2010,
  "director": { "nombre": "Christopher Nolan" },
  "actores": [
    { "nombre": "Leonardo DiCaprio" },
    { "nombre": "Joseph Gordon-Levitt" }
  ]
}
```

### B. Texto Delimitado (.txt)
Formato de texto plano donde los campos están separados por punto y coma (`;`).
- **Estructura**: Idéntica al formato CSV utilizado para la persistencia interna.
- **Archivos generados**: `directores.txt`, `actores.txt`, `peliculas.txt`.
- **Uso**: Útil para importación en hojas de cálculo (Excel) o procesamiento simple de texto.

**Ejemplo de salida (Pelicula):**
```text
Inception;2010;Christopher Nolan;Leonardo DiCaprio,Joseph Gordon-Levitt
```

## 2. Cómo Utilizar la Exportación

1.  Inicie la aplicación ejecutando `./launcher.sh`.
2.  En el menú principal, seleccione la opción **9. Exportar datos**.
3.  Se le presentará un submenú:
    ```
    Seleccione el formato de exportación:
    1. JSON
    2. TXT (delimitado por ;)
    ```
4.  Ingrese el número correspondiente al formato deseado (1 o 2) y presione Enter.
5.  El sistema confirmará la operación con un mensaje: `>> Datos exportados a [FORMATO] correctamente.`
6.  Los archivos se generarán en la carpeta raíz del proyecto.

## 3. Implementación Técnica y Código

A continuación se detalla el código concreto que hace posible esta funcionalidad.

### A. Controlador (`CineController.java`)

La clase `ejemplomvp.CineController` gestiona el flujo de la exportación.

**1. Menú de Selección**
El método `exportarDatos()` solicita al usuario el formato deseado.
```java
private void exportarDatos() {
    System.out.println("Seleccione el formato de exportación:");
    System.out.println("1. JSON");
    System.out.println("2. TXT (delimitado por ;)");
    // ... switch para llamar a exportarJSON() o exportarTXT()
}
```

**2. Exportación a JSON**
El método `exportarJSON()` recorre las listas, llama al método `toJson()` de cada objeto y escribe el resultado en archivos.
```java
private void exportarJSON() {
    try {
        // Ejemplo con Directores
        StringBuilder jsonDirectores = new StringBuilder("[\n");
        for (int i = 0; i < directores.size(); i++) {
            jsonDirectores.append(directores.get(i).toJson());
            if (i < directores.size() - 1) {
                jsonDirectores.append(",\n");
            }
        }
        jsonDirectores.append("\n]");
        Files.write(Paths.get("directores.json"), jsonDirectores.toString().getBytes(StandardCharsets.UTF_8));
        // ... se repite para Actores y Películas
    } catch (IOException e) { ... }
}
```

**3. Exportación a TXT**
El método `exportarTXT()` utiliza el método `toCSV()` (reutilizado) para generar las líneas de texto.
```java
private void exportarTXT() {
    try {
        List<String> lineasPeliculas = new ArrayList<>();
        for (Pelicula p : peliculas) {
            lineasPeliculas.add(p.toCSV());
        }
        Files.write(Paths.get("peliculas.txt"), lineasPeliculas, StandardCharsets.UTF_8);
        // ... se repite para Directores y Actores
    } catch (IOException e) { ... }
}
```

### B. Modelos (Lógica de Serialización)

Cada modelo sabe cómo convertirse a sí mismo en String.

**1. Clase `Actor` y `Director`**
Ubicación: `src/ejemplomvp/models/`
- `toJson()`: Retorna un JSON simple.
```java
public String toJson() {
    return "{\"nombre\": \"" + nombre + "\"}";
}
```

**2. Clase `Pelicula`**
Ubicación: `src/ejemplomvp/models/Pelicula.java`
- `toJson()`: Construye un JSON complejo anidando el director y la lista de actores.
```java
public String toJson() {
    StringBuilder json = new StringBuilder();
    json.append("{");
    json.append("\"titulo\": \"").append(titulo).append("\", ");
    json.append("\"anio\": ").append(anio).append(", ");
    
    // Serialización del Director
    json.append("\"director\": ");
    if (director != null) {
        json.append(director.toJson());
    } else {
        json.append("null");
    }
    json.append(", ");

    // Serialización de Actores (Array JSON)
    json.append("\"actores\": [");
    for (int i = 0; i < actores.size(); i++) {
        json.append(actores.get(i).toJson());
        if (i < actores.size() - 1) {
            json.append(", ");
        }
    }
    json.append("]");
    
    json.append("}");
    return json.toString();
}
```

- `toCSV()`: Genera la cadena delimitada por punto y coma.
```java
public String toCSV() {
    // ... lógica para concatenar campos con ";"
    return titulo + ";" + anio + ";" + directorNombre + ";" + actoresNombres.toString();
}
```
