package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore.JavaFXGestioneNegozi;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.amministratore.JavaFXGestionePuntiPrelievo;
import javafx.fxml.FXML;

/**
 * Controller della Home dell' Amministratore.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class IAmministratore implements JavaFXController {

    /**
     * Apre la finestra per la gestione dei punti vendita.
     */
    @FXML
    public void gestioneNegozi() {
        openWindow("/amministratore/GestioneNegozi.fxml", "Gestione negozi", new JavaFXGestioneNegozi());
    }

    /**
     * Apre la finestra per la gestione dei punti di prelievo.
     */
    @FXML
    public void gestionePuntiPrelievo() {
        openWindow("/amministratore/GestionePuntiPrelievo.fxml", "Gestione punti prelievo", new JavaFXGestionePuntiPrelievo());
    }
}
