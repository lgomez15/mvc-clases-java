package ejemplomvp.models;

import java.util.ArrayList;

public class Pelicula {
    private String titulo;
    private int anio;
    private Director director;
    private ArrayList<Actor> actores;

    public Pelicula(String titulo, int anio) {
        this.titulo = titulo;
        this.anio = anio;
        this.actores = new ArrayList<>();
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public Director getDirector() {
        return director;
    }

    public ArrayList<Actor> getActores() {
        return actores;
    }

    @Override
    public String toString() {
        return titulo + " (" + anio + ")";
    }

    public String toCSV() {
        String directorNombre = (director != null) ? director.getNombre() : "null";
        StringBuilder actoresNombres = new StringBuilder();
        for (int i = 0; i < actores.size(); i++) {
            actoresNombres.append(actores.get(i).getNombre());
            if (i < actores.size() - 1) {
                actoresNombres.append(",");
            }
        }
        return titulo + ";" + anio + ";" + directorNombre + ";" + actoresNombres.toString();
    }

    public static Pelicula fromCSV(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return null;
        }
        String[] parts = csv.split(";");
        if (parts.length < 2) {
            return null;
        }
        String titulo = parts[0];
        int anio = 0;
        try {
            anio = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Pelicula(titulo, anio);
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"titulo\": \"").append(titulo).append("\", ");
        json.append("\"anio\": ").append(anio).append(", ");

        json.append("\"director\": ");
        if (director != null) {
            json.append(director.toJson());
        } else {
            json.append("null");
        }
        json.append(", ");

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
}
