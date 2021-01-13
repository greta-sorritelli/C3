package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class JavaFXModificareDisponibilita {

    private final GestoreCorrieri gestoreCorrieri;
    private int IDCorriere;

    @FXML
    Label disponibilita = new Label();

    @FXML
    Button disponibile = new Button();

    @FXML
    Button nonDisponibile = new Button();

    public JavaFXModificareDisponibilita(GestoreCorrieri gestoreCorrieri, int id) throws SQLException {
        this.gestoreCorrieri = gestoreCorrieri;
        this.IDCorriere = id;
        getDisponibilita();
    }


    public void getDisponibilita() throws SQLException {
        //todo
        if (gestoreCorrieri.getDisponibilita(IDCorriere)) {
            disponibilita.setText("disponibile");
        } else {
            disponibilita.setText("non disponibile");
        }
        aggiornaButton();
    }

    private void aggiornaButton() throws SQLException {
        if (gestoreCorrieri.getDisponibilita(IDCorriere)) {
            nonDisponibile.setDisable(true);
            disponibile.setDisable(false);
        } else {
            disponibile.setDisable(true);
            nonDisponibile.setDisable(false);
        }
    }


    private void selezionaDisponibilita(boolean statoAttuale) throws SQLException {
        //todo
        gestoreCorrieri.setDisponibilita(IDCorriere, statoAttuale);
    }

    @FXML
    public void setDisponibilita() throws SQLException {
        //todo impostare a true se il corriere non è disponibile e vuole iniziare il turno
        //todo impostare a false se il corriere è disponibile e vuole terminare il turno
        if (disponibile.isPressed())
            selezionaDisponibilita(true);
        else
            selezionaDisponibilita(false);

        aggiornaButton();
    }
}
