package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXGestioneInventario;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IAddettoMagazzino implements JavaFXController {

    private GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final Negozio negozio;
    private int ID;

    public IAddettoMagazzino(int ID, int IDNegozio) throws SQLException {
        this.ID = ID;
        this.negozio = gestoreNegozi.getItem(IDNegozio);
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
