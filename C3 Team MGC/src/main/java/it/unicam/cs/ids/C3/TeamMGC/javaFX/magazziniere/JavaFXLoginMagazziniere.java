package it.unicam.cs.ids.C3.TeamMGC.javaFX.magazziniere;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.puntoPrelievo.GestoreMagazzini;
import it.unicam.cs.ids.C3.TeamMGC.view.IMagazziniere;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXLoginMagazziniere implements JavaFXController {
    private final GestoreMagazzini gestoreMagazzini = GestoreMagazzini.getInstance();
    private int ID;

    @FXML
    TextField IDMagazziniere;

    @FXML
    public void loginMagazziniere() throws SQLException {
        try {
            int id = Integer.parseInt(IDMagazziniere.getText());
            this.ID = id;
            gestoreMagazzini.getItem(id);
            Stage stage = (Stage) IDMagazziniere.getScene().getWindow();
//            openWindow("/HomeMagazziniere.fxml", "Home Magazziniere", new IMagazziniere());
            closeWindow(stage);
        } catch (IllegalArgumentException e) {
            errorWindow("ID errato", "Inserire un ID valido.");
            IDMagazziniere.clear();
        }
    }

    @FXML
    public void login() {
        IDMagazziniere.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try {
                    loginMagazziniere();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
