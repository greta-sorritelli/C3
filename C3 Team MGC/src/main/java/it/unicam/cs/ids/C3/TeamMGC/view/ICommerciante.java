package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commerciante.JavaFXGestioneVenditePromozionali;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commerciante.JavaFXVisualizzaStatistiche;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import it.unicam.cs.ids.C3.TeamMGC.personale.Commerciante;
import javafx.fxml.FXML;

import java.sql.SQLException;

/**
 * Controller della Home del {@link Commerciante}.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class ICommerciante implements JavaFXController {

    private final Negozio negozio;
    private final int ID;

    public ICommerciante(int ID, int IDNegozio) throws SQLException {
        this.ID = ID;
        this.negozio = GestoreNegozi.getInstance().getItem(IDNegozio);
    }

    /**
     * Apre la finestra per visualizzare le statistiche del negozio.
     */
    @FXML
    public void visualizzaStatistiche() {
        openWindow("/commerciante/VisualizzaStatistiche.fxml", "Visualizza le statistiche", new JavaFXVisualizzaStatistiche(negozio));
    }

    /**
     * Apre la finestra per la gestione delle vendite promozionali.
     */
    @FXML
    public void gestioneVenditePromozionali() {
        openWindow("/commerciante/GestioneVenditePromozionali.fxml", "Gestione vendite promozionali", new JavaFXGestioneVenditePromozionali(negozio));
    }
}
