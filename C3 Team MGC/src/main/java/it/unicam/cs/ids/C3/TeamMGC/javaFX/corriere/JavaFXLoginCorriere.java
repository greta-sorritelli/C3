package it.unicam.cs.ids.C3.TeamMGC.javaFX.corriere;

import it.unicam.cs.ids.C3.TeamMGC.corriere.GestoreCorrieri;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.view.ICorriere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXLoginCorriere implements JavaFXController {

    private final GestoreCorrieri gestoreCorrieri = GestoreCorrieri.getInstance();
    private int ID;

    @FXML
    TextField IDCorriere;

    @FXML
    public void loginCorriere() throws SQLException {
        try {
            int id = Integer.parseInt(IDCorriere.getText());
            this.ID = id;
            gestoreCorrieri.getItem(id);
            Stage stage = (Stage) IDCorriere.getScene().getWindow();
            openWindow("/HomeCorriere.fxml", "Home Corriere", new ICorriere(ID));
            closeWindow(stage);
        } catch (IllegalArgumentException e) {
            errorWindow("ID errato", "Inserire un ID valido.");
            IDCorriere.clear();
        }
    }


    @FXML
    public void login() {
        IDCorriere.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try {
                    loginCorriere();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }


}
