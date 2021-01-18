package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXGestioneInventario;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.negozio.SimpleNegozio;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IAddettoMagazzino implements JavaFXController {

    //todo
    private final Negozio negozio = new SimpleNegozio(1);

    public IAddettoMagazzino() throws SQLException {
    }

    /**
     * Apre la finestra per assegnare la merce al corriere.
     */
    @FXML
    private void assegnaMerceCorriere() {
        openWindow("/AssegnaMerceCorriere.fxml", "AssegnaMerce", new JavaFXAssegnaMerceCorriere());

    }

    /**
     * Apre la finestra per la gestione dell' inventario.
     */
    @FXML
    private void modificaInventario() {
        openWindow("/GestioneInventario.fxml", "GestioneInventario", new JavaFXGestioneInventario(negozio));
    }

}
