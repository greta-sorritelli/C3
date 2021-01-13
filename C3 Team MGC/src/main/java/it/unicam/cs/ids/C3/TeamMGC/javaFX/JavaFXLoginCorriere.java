package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.view.ICorriere;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXLoginCorriere implements JavaFXController{

    private GestoreCorrieri gestoreCorrieri;
    private int ID;

    public JavaFXLoginCorriere() {
        this.gestoreCorrieri = new GestoreCorrieri();
    }

    @FXML
    TextField IDCorriere;

    public void loginCorriere() throws SQLException {
        try {
            int id = Integer.parseInt(IDCorriere.getText());
            gestoreCorrieri.getItem(id);
            Stage stage = (Stage) IDCorriere.getScene().getWindow();
            closeWindow(stage);
            openWindow("/HomeCorriere.fxml", "Home Corriere", new ICorriere(id));
        } catch (IllegalArgumentException e) {
            errorWindow("ID errato", "Inserire un ID valido.");
        }
    }



}
