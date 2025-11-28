package ejemplomvp.models;

import java.util.ArrayList;

public class Actor {
    private String nombre;
    private ArrayList<Pelicula> filmografia;

    public Actor(String nombre) {
        this.nombre = nombre;
        this.filmografia = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarPelicula(Pelicula pelicula) {
        this.filmografia.add(pelicula);
    }

    public ArrayList<Pelicula> getFilmografia() {
        return filmografia;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String toCSV() {
        return nombre;
    }

    public static Actor fromCSV(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return null;
        }
        return new Actor(csv.trim());
    }

    public String toJson() {
        return "{\"nombre\": \"" + nombre + "\"}";
    }
}
