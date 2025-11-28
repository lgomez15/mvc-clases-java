package ejemplomvp.views;

import ejemplomvp.models.Pelicula;
import ejemplomvp.models.Director;
import ejemplomvp.models.Actor;

import java.util.Scanner;
import java.util.ArrayList;

public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n--- GESTIÓN DE CINE ---");
        System.out.println("1. Registrar Director");
        System.out.println("2. Registrar Actor");
        System.out.println("3. Registrar Película");
        System.out.println("4. Asignar Director a Película");
        System.out.println("5. Asignar Actor a Película");
        System.out.println("6. Listar Películas");
        System.out.println("7. Listar Directores");
        System.out.println("8. Listar Actores");
        System.out.println("9. Exportar datos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int leerEntero() {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(">> " + mensaje);
    }

    public void mostrarPeliculas(ArrayList<Pelicula> peliculas) {
        System.out.println("\n--- LISTA DE PELÍCULAS ---");
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
        } else {
            for (Pelicula p : peliculas) {
                System.out.println("Título: " + p.getTitulo() + " (" + p.getAnio() + ")");
                if (p.getDirector() != null) {
                    System.out.println("  Director: " + p.getDirector().getNombre());
                } else {
                    System.out.println("  Director: No asignado");
                }
                System.out.print("  Reparto: ");
                if (p.getActores().isEmpty()) {
                    System.out.println("Sin actores asignados");
                } else {
                    for (int i = 0; i < p.getActores().size(); i++) {
                        System.out.print(p.getActores().get(i).getNombre());
                        if (i < p.getActores().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("-------------------------");
            }
        }
    }

    public void mostrarDirectores(ArrayList<Director> directores) {
        System.out.println("\n--- LISTA DE DIRECTORES ---");
        if (directores.isEmpty()) {
            System.out.println("No hay directores registrados.");
        } else {
            for (int i = 0; i < directores.size(); i++) {
                System.out.println((i + 1) + ". " + directores.get(i).getNombre());
            }
        }
    }

    public void mostrarActores(ArrayList<Actor> actores) {
        System.out.println("\n--- LISTA DE ACTORES ---");
        if (actores.isEmpty()) {
            System.out.println("No hay actores registrados.");
        } else {
            for (int i = 0; i < actores.size(); i++) {
                System.out.println((i + 1) + ". " + actores.get(i).getNombre());
            }
        }
    }
}
