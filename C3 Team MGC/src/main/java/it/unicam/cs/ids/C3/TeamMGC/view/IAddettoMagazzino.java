package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.addettoMagazzino.JavaFXAssegnaMerceCorriere;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXGestioneInventario;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.ordine.GestoreOrdini;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class IAddettoMagazzino implements JavaFXController {

    //todo
    private final Negozio negozio = new Negozio(1);

    private final GestoreCorrieri gestoreCorrieri = new GestoreCorrieri();
    private final GestoreMagazzini gestoreMagazzini = new GestoreMagazzini();
    private final GestoreOrdini gestoreOrdini = new GestoreOrdini(negozio);

    public IAddettoMagazzino() throws SQLException {
    }

    /**
     * Apre la finestra per assegnare la merce al corriere.
     */
    @FXML
    private void assegnaMerceCorriere() {
        openWindow("/AssegnaMerceCorriere.fxml", "AssegnaMerce", new JavaFXAssegnaMerceCorriere(gestoreCorrieri,
                gestoreMagazzini, gestoreOrdini));

    }

    /**
     * Apre la finestra per la gestione dell' inventario.
     */
    @FXML
    private void modificaInventario() {
        openWindow("/GestioneInventario.fxml", "GestioneInventario", new JavaFXGestioneInventario(negozio));
    }

}
