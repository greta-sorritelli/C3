package it.unicam.cs.ids.C3.TeamMGC.view;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente.JavaFXControllareAlertCliente;
import it.unicam.cs.ids.C3.TeamMGC.javaFX.commesso.JavaFXRicezionePagamento;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class ICliente implements JavaFXController {
    private final int IDCliente;

    public ICliente(int ID) throws SQLException {
        this.IDCliente = ID;
    }

    @FXML
    public void controllaAlert() {
        openWindow("/cliente/AlertCliente.fxml", "Visualizza le notifiche", new JavaFXControllareAlertCliente(IDCliente));
    }
}
