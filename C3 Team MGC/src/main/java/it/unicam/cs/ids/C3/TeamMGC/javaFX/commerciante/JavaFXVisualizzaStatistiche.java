package it.unicam.cs.ids.C3.TeamMGC.javaFX.commerciante;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Controller della pagina per la visualizzazione delle Statistiche.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXVisualizzaStatistiche implements JavaFXController {

    private final Negozio negozio;

    public JavaFXVisualizzaStatistiche(Negozio negozio) {
        this.negozio = negozio;
    }

    @FXML
    TextField prezzoMedio;

    @FXML
    TextField percentuale;

    public void calcolaPrezzoMedio() {
        try {
            if (negozio.getInventario().isEmpty())
                throw new IllegalArgumentException("Merci non presenti.");
            prezzoMedio.setText(String.valueOf(negozio.getPrezzoMedio()));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        } catch (IllegalArgumentException exception) {
            alertWindow("Impossibile calcolare la statistica.", "Inventario vuoto.");
        }
    }

    public void calcolaPercentuale() {
        try {
            percentuale.setText(negozio.getMerceVenduta() + "%");
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    public void reset() {
        prezzoMedio.clear();
        percentuale.clear();
    }

}
