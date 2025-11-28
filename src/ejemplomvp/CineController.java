package ejemplomvp;

import ejemplomvp.models.Actor;
import ejemplomvp.models.Director;
import ejemplomvp.models.Pelicula;
import ejemplomvp.views.ConsoleView;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CineController {
    private ConsoleView vista;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Director> directores;
    private ArrayList<Actor> actores;

    public CineController(ConsoleView vista) {
        this.vista = vista;
        this.peliculas = new ArrayList<>();
        this.directores = new ArrayList<>();
        this.actores = new ArrayList<>();
        try {
            Files.createDirectories(Paths.get("repo"));
        } catch (IOException e) {
            System.err.println("Error al crear directorio repo: " + e.getMessage());
        }
        cargarDatos();
    }

    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerEntero();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarDirector();
                break;
            case 2:
                registrarActor();
                break;
            case 3:
                registrarPelicula();
                break;
            case 4:
                asignarDirectorAPelicula();
                break;
            case 5:
                asignarActorAPelicula();
                break;
            case 6:
                vista.mostrarPeliculas(peliculas);
                break;
            case 7:
                vista.mostrarDirectores(directores);
                break;
            case 8:
                vista.mostrarActores(actores);
                break;
            case 9:
                exportarDatos();
                break;
            case 0:
                guardarDatos();
                vista.mostrarMensaje("Saliendo del sistema...");
                break;
            default:
                vista.mostrarMensaje("Opción no válida.");
        }
    }

    private void registrarDirector() {
        String nombre = vista.leerTexto("Nombre del Director: ");
        Director director = new Director(nombre);
        directores.add(director);
        vista.mostrarMensaje("Director registrado con éxito.");
    }

    private void registrarActor() {
        String nombre = vista.leerTexto("Nombre del Actor: ");
        Actor actor = new Actor(nombre);
        actores.add(actor);
        vista.mostrarMensaje("Actor registrado con éxito.");
    }

    private void registrarPelicula() {
        String titulo = vista.leerTexto("Título de la Película: ");
        System.out.print("Año de lanzamiento: ");
        int anio = vista.leerEntero();
        Pelicula pelicula = new Pelicula(titulo, anio);
        peliculas.add(pelicula);
        vista.mostrarMensaje("Película registrada con éxito.");
    }

    private void asignarDirectorAPelicula() {
        if (peliculas.isEmpty() || directores.isEmpty()) {
            vista.mostrarMensaje("Debe haber películas y directores registrados.");
            return;
        }

        vista.mostrarPeliculas(peliculas);
        System.out.print("Seleccione el número de la película (1-" + peliculas.size() + "): ");
        int indicePelicula = vista.leerEntero() - 1;

        if (indicePelicula < 0 || indicePelicula >= peliculas.size()) {
            vista.mostrarMensaje("Selección de película inválida.");
            return;
        }

        vista.mostrarDirectores(directores);
        System.out.print("Seleccione el número del director (1-" + directores.size() + "): ");
        int indiceDirector = vista.leerEntero() - 1;

        if (indiceDirector < 0 || indiceDirector >= directores.size()) {
            vista.mostrarMensaje("Selección de director inválida.");
            return;
        }

        Pelicula pelicula = peliculas.get(indicePelicula);
        Director director = directores.get(indiceDirector);

        pelicula.setDirector(director);
        director.agregarPelicula(pelicula);

        vista.mostrarMensaje("Director asignado correctamente.");
    }

    private void asignarActorAPelicula() {
        if (peliculas.isEmpty() || actores.isEmpty()) {
            vista.mostrarMensaje("Debe haber películas y actores registrados.");
            return;
        }

        vista.mostrarPeliculas(peliculas);
        System.out.print("Seleccione el número de la película (1-" + peliculas.size() + "): ");
        int indicePelicula = vista.leerEntero() - 1;

        if (indicePelicula < 0 || indicePelicula >= peliculas.size()) {
            vista.mostrarMensaje("Selección de película inválida.");
            return;
        }

        vista.mostrarActores(actores);
        System.out.print("Seleccione el número del actor (1-" + actores.size() + "): ");
        int indiceActor = vista.leerEntero() - 1;

        if (indiceActor < 0 || indiceActor >= actores.size()) {
            vista.mostrarMensaje("Selección de actor inválida.");
            return;
        }

        Pelicula pelicula = peliculas.get(indicePelicula);
        Actor actor = actores.get(indiceActor);

        pelicula.agregarActor(actor);
        actor.agregarPelicula(pelicula);

        vista.mostrarMensaje("Actor asignado correctamente.");
    }

    private void guardarDatos() {
        try {
            // Guardar Directores
            List<String> lineasDirectores = new ArrayList<>();
            for (Director d : directores) {
                lineasDirectores.add(d.toCSV());
            }
            Files.write(Paths.get("repo/directores.csv"), lineasDirectores, StandardCharsets.UTF_8);

            // Guardar Actores
            List<String> lineasActores = new ArrayList<>();
            for (Actor a : actores) {
                lineasActores.add(a.toCSV());
            }
            Files.write(Paths.get("repo/actores.csv"), lineasActores, StandardCharsets.UTF_8);

            // Guardar Peliculas
            List<String> lineasPeliculas = new ArrayList<>();
            for (Pelicula p : peliculas) {
                lineasPeliculas.add(p.toCSV());
            }
            Files.write(Paths.get("repo/peliculas.csv"), lineasPeliculas, StandardCharsets.UTF_8);

            System.out.println("Datos guardados correctamente.");

        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        try {
            Path pathDirectores = Paths.get("repo/directores.csv");
            if (Files.exists(pathDirectores)) {
                List<String> lineas = Files.readAllLines(pathDirectores, StandardCharsets.UTF_8);
                for (String linea : lineas) {
                    Director d = Director.fromCSV(linea);
                    if (d != null)
                        directores.add(d);
                }
            }

            Path pathActores = Paths.get("repo/actores.csv");
            if (Files.exists(pathActores)) {
                List<String> lineas = Files.readAllLines(pathActores, StandardCharsets.UTF_8);
                for (String linea : lineas) {
                    Actor a = Actor.fromCSV(linea);
                    if (a != null)
                        actores.add(a);
                }
            }

            Path pathPeliculas = Paths.get("repo/peliculas.csv");
            if (Files.exists(pathPeliculas)) {
                List<String> lineas = Files.readAllLines(pathPeliculas, StandardCharsets.UTF_8);
                for (String linea : lineas) {
                    Pelicula p = Pelicula.fromCSV(linea);
                    if (p != null) {
                        // Reconstruir relaciones
                        String[] parts = linea.split(";");

                        // Director
                        if (parts.length > 2 && !parts[2].equals("null")) {
                            String nombreDirector = parts[2];
                            for (Director d : directores) {
                                if (d.getNombre().equals(nombreDirector)) {
                                    p.setDirector(d);
                                    d.agregarPelicula(p);
                                    break;
                                }
                            }
                        }

                        // Actores
                        if (parts.length > 3 && !parts[3].isEmpty()) {
                            String[] nombresActores = parts[3].split(",");
                            for (String nombreActor : nombresActores) {
                                for (Actor a : actores) {
                                    if (a.getNombre().equals(nombreActor)) {
                                        p.agregarActor(a);
                                        a.agregarPelicula(p);
                                        break;
                                    }
                                }
                            }
                        }
                        peliculas.add(p);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void exportarDatos() {
        System.out.println("Seleccione el formato de exportación:");
        System.out.println("1. JSON");
        System.out.println("2. TXT (delimitado por ;)");
        int opcion = vista.leerEntero();

        switch (opcion) {
            case 1:
                exportarJSON();
                break;
            case 2:
                exportarTXT();
                break;
            default:
                vista.mostrarMensaje("Opción no válida.");
        }
    }

    private void exportarJSON() {
        try {
            // Exportar Directores
            StringBuilder jsonDirectores = new StringBuilder("[\n");
            for (int i = 0; i < directores.size(); i++) {
                jsonDirectores.append(directores.get(i).toJson());
                if (i < directores.size() - 1) {
                    jsonDirectores.append(",\n");
                }
            }
            jsonDirectores.append("\n]");
            Files.write(Paths.get("repo/directores.json"), jsonDirectores.toString().getBytes(StandardCharsets.UTF_8));

            // Exportar Actores
            StringBuilder jsonActores = new StringBuilder("[\n");
            for (int i = 0; i < actores.size(); i++) {
                jsonActores.append(actores.get(i).toJson());
                if (i < actores.size() - 1) {
                    jsonActores.append(",\n");
                }
            }
            jsonActores.append("\n]");
            Files.write(Paths.get("repo/actores.json"), jsonActores.toString().getBytes(StandardCharsets.UTF_8));

            // Exportar Peliculas
            StringBuilder jsonPeliculas = new StringBuilder("[\n");
            for (int i = 0; i < peliculas.size(); i++) {
                jsonPeliculas.append(peliculas.get(i).toJson());
                if (i < peliculas.size() - 1) {
                    jsonPeliculas.append(",\n");
                }
            }
            jsonPeliculas.append("\n]");
            Files.write(Paths.get("repo/peliculas.json"), jsonPeliculas.toString().getBytes(StandardCharsets.UTF_8));

            vista.mostrarMensaje("Datos exportados a JSON correctamente.");
        } catch (IOException e) {
            vista.mostrarMensaje("Error al exportar a JSON: " + e.getMessage());
        }
    }

    private void exportarTXT() {
        try {
            // Exportar Directores
            List<String> lineasDirectores = new ArrayList<>();
            for (Director d : directores) {
                lineasDirectores.add(d.toCSV());
            }
            Files.write(Paths.get("repo/directores.txt"), lineasDirectores, StandardCharsets.UTF_8);

            // Exportar Actores
            List<String> lineasActores = new ArrayList<>();
            for (Actor a : actores) {
                lineasActores.add(a.toCSV());
            }
            Files.write(Paths.get("repo/actores.txt"), lineasActores, StandardCharsets.UTF_8);

            // Exportar Peliculas
            List<String> lineasPeliculas = new ArrayList<>();
            for (Pelicula p : peliculas) {
                lineasPeliculas.add(p.toCSV());
            }
            Files.write(Paths.get("repo/peliculas.txt"), lineasPeliculas, StandardCharsets.UTF_8);

            vista.mostrarMensaje("Datos exportados a TXT correctamente.");
        } catch (IOException e) {
            vista.mostrarMensaje("Error al exportar a TXT: " + e.getMessage());
        }
    }
}