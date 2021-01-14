package it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente;

import it.unicam.cs.ids.C3.TeamMGC.cliente.GestoreClienti;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.view.ICliente;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JavaFXLoginCliente implements JavaFXController {
    private final GestoreClienti gestoreClienti = GestoreClienti.getInstance();
    private int ID;

    @FXML
    TextField IDCliente;

    @FXML
    public void loginCliente() throws SQLException {
        try {
            int id = Integer.parseInt(IDCliente.getText());
            this.ID = id;
            gestoreClienti.getItem(id);
            Stage stage = (Stage) IDCliente.getScene().getWindow();
            openWindow("/HomeCliente.fxml", "Home Cliente", new ICliente(ID));
            closeWindow(stage);
        } catch (IllegalArgumentException e) {
            errorWindow("ID errato", "Inserire un ID valido.");
            IDCliente.clear();
        }
    }

    @FXML
    public void login() {
        IDCliente.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                try {
                    loginCliente();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
