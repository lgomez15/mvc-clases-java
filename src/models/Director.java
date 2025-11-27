package ejemplomvp;

import java.util.ArrayList;

public class Director {
    private String nombre;
    private ArrayList<Pelicula> peliculasDirigidas;

    public Director(String nombre) {
        this.nombre = nombre;
        this.peliculasDirigidas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarPelicula(Pelicula pelicula) {
        this.peliculasDirigidas.add(pelicula);
    }

    public ArrayList<Pelicula> getPeliculasDirigidas() {
        return peliculasDirigidas;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String toCSV() {
        return nombre;
    }

    public static Director fromCSV(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return null;
        }
        return new Director(csv.trim());
    }
}
