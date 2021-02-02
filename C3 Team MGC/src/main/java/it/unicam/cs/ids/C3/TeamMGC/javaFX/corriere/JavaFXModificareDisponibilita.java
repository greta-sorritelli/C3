package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

/**
 * Controller della pagina per modificare la disponibilità del Corriere.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXModificareDisponibilita {

    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private final int IDCorriere;

    @FXML
    Label disponibilita = new Label();

    @FXML
    Button disponibile = new Button();

    @FXML
    Button nonDisponibile = new Button();

    public JavaFXModificareDisponibilita(int id) {
        this.IDCorriere = id;
    }

    public void getDisponibilita() throws SQLException {
        if (gestoreCorrieri.getDisponibilita(IDCorriere)) {
            disponibilita.setText("disponibile");
        } else {
            disponibilita.setText("non disponibile");
        }
        aggiornaButton();
    }

    private void aggiornaButton() throws SQLException {
        if (gestoreCorrieri.getDisponibilita(IDCorriere)) {
            nonDisponibile.setDisable(false);
            disponibile.setDisable(true);
        } else {
            disponibile.setDisable(false);
            nonDisponibile.setDisable(true);
        }
    }

    private void selezionaDisponibilita(boolean statoAttuale) throws SQLException {
        gestoreCorrieri.setDisponibilita(IDCorriere, statoAttuale);
        getDisponibilita();
        aggiornaButton();
    }

    @FXML
    public void setDisponibile() throws SQLException {
        selezionaDisponibilita(true);
    }

    @FXML
    public void setNonDisponibile() throws SQLException {
        selezionaDisponibilita(false);
    }

}
