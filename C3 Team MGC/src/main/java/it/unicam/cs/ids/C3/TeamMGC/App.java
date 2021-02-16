
package it.unicam.cs.ids.C3.TeamMGC;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXC3;
import javafx.application.Application;

/**
 * Classe principale per il launch di un' interfaccia grafica.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class App {

    public static void main(String[] args) {
        launchGui();
    }

    private static void launchGui() {
        Application.launch(JavaFXC3.class);
    }
}