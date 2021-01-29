
package it.unicam.cs.ids.C3.TeamMGC;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXC3;
import javafx.application.Application;

/**
 * Classe principale per il launch di un' interfaccia grafica.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class C3 {

    private String nome;
    private String indirizzo;
    private String orarioApertura;
    private String orarioChiusura;
    private String telefono;

    public static void main(String[] args) {
        if (args.length == 0)
            launchGui();
        throw new UnsupportedOperationException();
    }

    private static void launchGui() {
        Application.launch(JavaFXC3.class);
    }

}