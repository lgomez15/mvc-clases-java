package ejemplomvp;

import ejemplomvp.views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView vista = new ConsoleView();
        CineController controlador = new CineController(vista);
        controlador.iniciar();
    }
}
