# MVP Completo - Gestión de Cine

Programa de consola en Java para registrar directores, actores y películas, y asignarlos entre sí.

Archivos principales:
- [`src/main/Main.java`](src/main/Main.java) — Punto de entrada (`ejemplomvp.Main`).
- [`src/main/CineController.java`](src/main/CineController.java) — Controlador (nuevo).
- [`src/views/ConsoleView.java`](src/views/ConsoleView.java) — Vista de consola (`ejemplomvp.ConsoleView`).
- [`src/models/Pelicula.java`](src/models/Pelicula.java) — Modelo `ejemplomvp.Pelicula`.
- [`src/models/Director.java`](src/models/Director.java) — Modelo `ejemplomvp.Director`.
- [`src/models/Actor.java`](src/models/Actor.java) — Modelo `ejemplomvp.Actor`.
- [launcher.sh](launcher.sh) — Script para compilar y ejecutar.

Cómo compilar y ejecutar:
1. Desde la raíz del proyecto:
   - Dar permiso de ejecución al launcher: `chmod +x launcher.sh`
   - Ejecutar: `./launcher.sh`

Entrada/salida:
- Interfaz de texto en la consola que muestra un menú y pide opciones/entradas.

Nota:
- El paquete usado es `ejemplomvp` en todos los archivos fuente bajo `src/`.