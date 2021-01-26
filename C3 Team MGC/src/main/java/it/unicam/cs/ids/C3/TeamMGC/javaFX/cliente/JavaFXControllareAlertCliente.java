package it.unicam.cs.ids.C3.TeamMGC.javaFX.cliente;

import it.unicam.cs.ids.C3.TeamMGC.javaFX.JavaFXController;
import it.unicam.cs.ids.C3.TeamMGC.manager.GestoreAlert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class JavaFXControllareAlertCliente implements JavaFXController {
    private final String tipologiaUtente = "CLIENTE";
    private final int IDCliente;
    private GestoreAlert gestoreAlert = GestoreAlert.getInstance();

    @FXML
    TableView<ArrayList<String>> alertTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDAlert;
    @FXML
    TableColumn<ArrayList<String>, String> messaggio;

    public JavaFXControllareAlertCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    /**
     * Collega i campi del Messaggio dell' alert alla tabella.
     */
    private void setAlertTableCellValueFactory() {
        alertTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        IDAlert.setCellValueFactory(id -> new SimpleObjectProperty<>(id.getValue().get(0)));
        messaggio.setCellValueFactory(m -> new SimpleObjectProperty<>(m.getValue().get(1)));
    }

    @FXML
    public void deleteAlert() {
        try {
            if (!alertTable.getSelectionModel().isEmpty()) {
                int id = Integer.parseInt(alertTable.getSelectionModel().getSelectedItem().get(0));
                gestoreAlert.deleteAlert(id, tipologiaUtente);
            } else {
                alertWindow("Impossibile proseguire", "Selezionare una notifica.");
            }
        } catch (SQLException e) {
            errorWindow("Error!", "Errore nel DB.");
        }
    }

    @FXML
    public void visualizzaAlert() {
        try {
            if (gestoreAlert.getDettagliAlert(IDCliente, tipologiaUtente).isEmpty())
                throw new NullPointerException("No notifiche");
            setAlertTableCellValueFactory();
            alertTable.getItems().clear();
            alertTable.getItems().addAll(gestoreAlert.getDettagliAlert(IDCliente, tipologiaUtente));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB." + exception.getMessage());
        } catch (NullPointerException e) {
            alertWindow("Notifiche non disponibili.", "Aggiorna piu' tardi.");
            closeWindow((Stage) alertTable.getScene().getWindow());
        }
    }

}
