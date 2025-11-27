package ejemplomvp;

public class Main {
    public static void main(String[] args) {
        ConsoleView vista = new ConsoleView();
        CineController controlador = new CineController(vista);
        controlador.iniciar();
    }
}
