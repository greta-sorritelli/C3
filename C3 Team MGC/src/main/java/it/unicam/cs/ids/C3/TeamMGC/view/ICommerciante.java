package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXControllareAlert;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commerciante.JavaFXVisualizzaStatistiche;
import it.unicam.cs.ids.C3.TeamMGC.negozio.GestoreNegozi;
import it.unicam.cs.ids.C3.TeamMGC.negozio.Negozio;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class ICommerciante implements JavaFXController {

    private GestoreNegozi gestoreNegozi = GestoreNegozi.getInstance();
    private final Negozio negozio;
    private int ID;

    public ICommerciante(int ID, int IDNegozio) throws SQLException {
        this.ID = ID;
        this.negozio = gestoreNegozi.getItem(IDNegozio);
    }

    /**
     * Apre la finestra per visualizzare le statistiche del negozio.
     */
    @FXML
    public void visualizzaStatistiche() {
        openWindow("/VisualizzaStatistiche.fxml", "Visualizza le statistiche", new JavaFXVisualizzaStatistiche(negozio.getID()));
    }
}
