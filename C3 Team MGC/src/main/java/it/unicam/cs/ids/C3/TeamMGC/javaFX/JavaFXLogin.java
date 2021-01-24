package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Objects;

public class JavaFXLogin {

    @FXML
    TextField ID;
    @FXML
    TextField nome;
    @FXML
    TextField cognome;
    @FXML
    TextField password;

    @FXML
    ChoiceBox<String> utentiChoiceBox = new ChoiceBox<>();

    @FXML
    public void showUtenti() {
        utentiChoiceBox.getItems().clear();
        utentiChoiceBox.getItems().add("CLIENTE");
        utentiChoiceBox.getItems().add("CORRIERE");
        utentiChoiceBox.getItems().add("COMMESSO");
        utentiChoiceBox.getItems().add("ADDETTO");
        utentiChoiceBox.getItems().add("COMMERCIANTE");
        utentiChoiceBox.getItems().add("MAGAZZINIERE");
    }

    @FXML
    public void updateUtentiChoiceBox() {
        if (Objects.isNull(utentiChoiceBox.getValue())) {
            showUtenti();
        }
    }

}
