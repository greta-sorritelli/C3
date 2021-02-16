package it.unicam.cs.ids.C3.TeamMGC.javaFX;

import it.unicam.cs.ids.C3.TeamMGC.manager.GestoreAlert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Controller della pagina che gestisce gli alert in arrivo.
 *
 * @author Matteo Rondini, Greta Sorritelli, Clarissa Albanese
 */
public class JavaFXControllareAlert implements JavaFXController {

    private final String tipologiaUtente;
    private final int ID;
    private final GestoreAlert gestoreAlert = GestoreAlert.getInstance();

    @FXML
    TableView<ArrayList<String>> alertTable;
    @FXML
    TableColumn<ArrayList<String>, String> IDAlert;
    @FXML
    TableColumn<ArrayList<String>, String> messaggio;

    public JavaFXControllareAlert(int ID, String tipologiaUtente) {
        this.tipologiaUtente = tipologiaUtente;
        this.ID = ID;
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
                ArrayList<ArrayList<String>> sel = new ArrayList<>(alertTable.getSelectionModel().getSelectedItems());
                for (ArrayList<String> alert : sel) {
                    int ID = Integer.parseInt(alert.get(0));
                    gestoreAlert.deleteAlert(ID, tipologiaUtente);
                }
                successWindow("Success!", "Notifiche eliminate con successo.", 2);
                sel.clear();
                visualizzaAlert();
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
            if (gestoreAlert.getDettagliAlert(ID, tipologiaUtente).isEmpty()) {
                alertTable.getItems().clear();
                throw new NullPointerException("No notifiche");
            }
            setAlertTableCellValueFactory();
            alertTable.getItems().clear();
            alertTable.getItems().addAll(gestoreAlert.getDettagliAlert(ID, tipologiaUtente));
        } catch (SQLException exception) {
            errorWindow("Error!", "Errore nel DB." + exception.getMessage());
        } catch (NullPointerException e) {
            alertWindow("Non ci sono notifiche.", "Aggiornare piu' tardi.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                errorWindow("Error!", "Errore.");
            }
            closeWindow((Stage) alertTable.getScene().getWindow());
        }
    }

}
